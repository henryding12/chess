package client;

import java.util.Scanner;

public class Repl {
    private final ChessClient client;

    public enum State {
        SIGNEDOUT,
        SIGNEDIN,
        QUIT
    }

    private State state;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl, this);
        state = State.SIGNEDOUT;
    }


    public void run() {
        System.out.println("Welcome to Chess. Type Help to get started.");
        Scanner scanner = new Scanner(System.in);
        while (!state.equals(State.QUIT)) {
            String input = scanner.nextLine();
            if (state == State.SIGNEDOUT) {
                handlePreLogin(input);
            } else {
                handlePostLogin(input);
            }
        }
    }

    private void handlePreLogin(String input){

    }

    private void handlePostLogin(String input ){

    }
//
//    public void run() {
//        System.out.println("\uD83D\uDC36 Welcome to Chess. Sign in to start.");
//        System.out.print(client.help());
//
//        Scanner scanner = new Scanner(System.in);
//        var result = "";
//        while (!result.equals("quit")) {
//            printPrompt();
//            String line = scanner.
//
//            try {
//                result = client.eval(line);
//                System.out.print(BLUE + result);
//            } catch (Throwable e) {
//                var msg = e.toString();
//                System.out.print(msg);
//            }
//        }
//        System.out.println();
//    }


    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }
}
