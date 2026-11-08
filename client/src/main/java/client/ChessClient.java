package client;

import ui.Repl;

import java.util.Arrays;

public class ChessClient {

    private final String serverUrl;
    private State state;

    public ChessClient(String serverUrl) {
        this.serverUrl = serverUrl;
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

    private String preLoginHandler(String[] tokens,String cmd, String[] params){
        return switch(cmd) {
            case "register" -> register(params);
            case "login" -> login(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String postLoginHandler(String[] tokens, String cmd, String[] params){
        return switch(cmd) {
            case "logout" -> logout(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    private String register(String[] params) {
        state = State.SIGNEDIN;
        return "Signed in!";
    }

    private String login(String[] params) {
        state = State.SIGNEDIN;
        return "Signing In!";
    }

    private String logout(String [] params) {
        state = State.SIGNEDOUT;
        return "Logging Out!";
    }

}
