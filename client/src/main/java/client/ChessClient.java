package client;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import ui.BoardRenderer;

import java.util.Arrays;
import java.util.List;

public class ChessClient {

    private final String serverUrl;
    private State state;
    private ServerFacade serverFacade;
    private AuthData authData;
    private List<GameData> gameList = List.of();

    public ChessClient(String serverUrl) {
        this.serverUrl = serverUrl;
        serverFacade = new ServerFacade(serverUrl);
        state = State.SIGNEDOUT;
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - register <username> <password> <email>
                    - login <username> <password>
                    - help
                    - quit
                    """;
        }
        return """
                - list
                - create <gameName>
                - join <game#, color(WHITE|BLACK)>
                - observe <game#>
                - logout
                - help
                - quit
                """;
    }

    public String eval(String input) {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            if (state == State.SIGNEDOUT) {
                return preLoginHandler(tokens, cmd, params);
            } else {
                return postLoginHandler(tokens, cmd, params);
            }
        } catch (ResponseException e) {
            return switch (e.getStatus()) {
                case 400 -> "Bad request: missing inputs";
                case 401 -> "Unauthorized: check username/password";
                case 403 -> "Forbidden: something is already taken, use something else";
                default -> "Server Error";
            };
        } catch (Exception e) {
            return "Server Issue";
        }
    }

    private String preLoginHandler(String[] tokens,String cmd, String[] params) throws Exception {
        return switch(cmd) {
            case "register" -> register(params);
            case "login" -> login(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String postLoginHandler(String[] tokens, String cmd, String[] params) throws Exception {
        return switch(cmd) {
            case "list" -> listGames();
            case "create" -> create(params);
            case "join" -> joinGame(params);
            case "observe" -> observe(params);
            case "logout" -> logout(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String register(String[] params) throws Exception {
        if (params.length != 3) {
            return "Missing a parameter, please make sure you filled everything in correctly.";
        }
        authData = serverFacade.register(new UserData(params[0], params[1], params[2]));
        state = State.SIGNEDIN;
        return "Signed in!";
    }

    private String login(String[] params) throws Exception {
        if (params.length != 2) {
            return "Missing a parameter, please make sure you filled everything in correctly.";
        }
        authData = serverFacade.login(new UserData(params[0], params[1], null));
        state = State.SIGNEDIN;
        return "Signing In!";
    }

    private String logout(String [] params) throws Exception {
        serverFacade.logout(authData);
        state = State.SIGNEDOUT;
        return "Logging Out!";
    }

    private String create(String[] params) throws Exception {
        if (params.length != 1) {
            return "Missing input, please make sure you have filled everything.";
        }
        serverFacade.createGame(params[0], authData);
        return "Created Game " + params[0];
    }

    private String listGames() throws Exception {
        state = State.SIGNEDIN;
        ListGamesResult gamesResult = serverFacade.listGames(authData);
        gameList = gamesResult.games();
        String result = "";
        if (gameList.isEmpty()) {
            return "No Games Yet.";
        } else {
            for (int i = 0; i < gameList.size(); i++) {
                String white;
                String black;
                var game = gameList.get(i);
                if (game.whiteUsername() == null) {
                    white = "None";
                } else {
                    white = game.whiteUsername();
                }
                if (game.blackUsername() == null) {
                    black = "None";
                } else {
                    black = game.blackUsername();
                }

                result = result + (i+1) + ". " + game.gameName() + " - " + "white: " + white + "  black:" + black;
                result = result + "\n";
            }
        }
        return result;
    }

    private String joinGame(String[] params) throws Exception {
        state = State.SIGNEDIN;
        if (params.length != 2 ) {
            return "Invalid: Need both <game#> <color>";
        }
        int listId;
        try {
            listId = Integer.parseInt(params[0]);
        } catch(NumberFormatException e) {
            return "Game# needs to be a number.";
        }
        if (listId < 1 || listId > gameList.size()) {
            return "Invalid Game#: Run the command 'list' to see available game#s.";
        }
        String color = params[1].toUpperCase();
        if (!color.equals("BLACK") && !color.equals("WHITE")) {
            return "Invalid color: color needs to be either black or white.";
        }
        var game = gameList.get(listId - 1);
        int gameID = game.gameID();
        serverFacade.joinGame(gameID, color, authData);
        new BoardRenderer(game.game().getBoard(), ChessGame.TeamColor.valueOf(color));
        return "Joined " + game.gameName() + " as player " + color;
    }

    private String observe(String[] params) throws Exception {
        if (params.length != 1) {
            return "Need an input of which game you want to observe. Use 'list' to see games.";
        }
        int listId;
        try {
            listId = Integer.parseInt(params[0]);
        } catch(NumberFormatException e) {
            return "Game# needs to be a number.";
        }
        if (listId < 1 || listId > gameList.size()) {
            return "Invalid Game#: Run the command 'list' to see available game#s.";
        }
        var game = gameList.get(listId - 1);
        new BoardRenderer(game.game().getBoard(), ChessGame.TeamColor.WHITE);
        return "Observing game " + game.gameName();
    }

}
