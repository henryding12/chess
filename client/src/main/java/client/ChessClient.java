package client;

import model.AuthData;
import model.UserData;
import ui.Repl;

import java.util.Arrays;
import java.util.Map;

public class ChessClient {

    private final String serverUrl;
    private State state;
    private ServerFacade serverFacade;
    private AuthData authData;

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
                    - quit
                    """;
        }
        return """
                - list
                - create <gameName>
                - join <gameID, color>
                - logout
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
            } else if (state == State.SIGNEDIN) {
                return postLoginHandler(tokens, cmd, params);
            }
        } catch (Exception e) {
            return e.toString();
        }
        return "Bad";
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
            case "logout" -> logout(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String register(String[] params) throws Exception {
        state = State.SIGNEDIN;
        authData = serverFacade.register(new UserData(params[0], params[1], params[2]));
        return "Signed in!";
    }

    private String login(String[] params) throws Exception {
        state = State.SIGNEDIN;
        authData = serverFacade.login(new UserData(params[0], params[1], null));
        return "Signing In!";
    }

    private String logout(String [] params) {
        state = State.SIGNEDOUT;
        return "Logging Out!";
    }

    private String create(String[] params) throws Exception{
        state = State.SIGNEDIN;
        serverFacade.createGame(params[0], authData);
        return "Created Game " + params[0];
    }

    private String listGames() throws Exception {
        state = State.SIGNEDIN;
        Map listOfGames = serverFacade.listGames(authData);
        return listOfGames.toString();
    }

    private String joinGame(String[] params) throws Exception{
        state = State.SIGNEDIN;
        return serverFacade.joinGame(params[0], params[1], authData);
    }

}
