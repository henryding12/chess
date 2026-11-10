package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import javax.swing.text.Position;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class BoardRenderer {
    private ChessBoard chessBoard;
    private ChessGame.TeamColor teamColor;

    // Padded characters.
    private static final String EMPTY = "   ";


    public BoardRenderer(ChessBoard chessBoard, ChessGame.TeamColor teamColor){
        this.chessBoard = chessBoard;
        this.teamColor = teamColor;
        chessBoard.resetBoard();

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);
    }

    private void drawHeaders(PrintStream out) {


//        String[] headers = { "TIC", "TAC", "TOE" };
//        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//            drawHeader(out, headers[boardCol]);
//
//            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//                out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
//            }
//        }
        drawHeader(out);
        newLine(out);
        drawBoard(out); // ends on the newline
        drawHeader(out);
//        out.println();
    }

    private void drawHeader(PrintStream out) {
//        int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
//        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

        char startingText = 'a';

        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(EMPTY);
        for (int i = 0; i < 8; i++){
            out.print(' ');
            out.print((char)(startingText+i));
            out.print(' ');
        }
        out.print(EMPTY);
//        out.print("a");
//        out.print(EMPTY);
//        printHeaderText(out, headerText);
//        out.print(EMPTY.repeat(suffixLength));
    }

    private void drawBoard (PrintStream out) {
        int color = 0;
        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 10; c++){
                if (c == 0 || c == 9) {
                    out.print(SET_BG_COLOR_LIGHT_GREY);
                    out.print(' ');
                    out.print(8-r);
                    out.print(' ');
                    if (c == 9) {
                        newLine(out);
                        color++;
                    }
                } else {
                    if (color % 2 == 0) {
                        out.print(SET_BG_COLOR_WHITE);
                    } else {
                        out.print(SET_BG_COLOR_BLACK);
                    }
                    out.print(' ');
                    printPiece(8 - r,c,out);
                    out.print(' ');
                    color++;
                }
            }

        }
    }

    private void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private void newLine(PrintStream out) {
        out.print(RESET_BG_COLOR);
        out.print("\n");
    }

    private void printPiece(int r, int c, PrintStream out) {
        char symbol = ' ';

        ChessPiece piece = chessBoard.getPiece(new ChessPosition(r,c));
        if (piece != null) {
            chess.ChessGame.TeamColor color = piece.getTeamColor();
            ChessPiece.PieceType type = piece.getPieceType();


            switch(type) {
                case KING -> symbol = 'K';
                case QUEEN -> symbol = 'Q';
                case BISHOP -> symbol = 'B';
                case KNIGHT -> symbol = 'N';
                case ROOK -> symbol = 'R';
                case PAWN -> symbol = 'P';
            }

            if (color == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_RED);
            } else {
                out.print(SET_TEXT_COLOR_BLUE);
            }
        }

        out.print(symbol);
        out.print(RESET_TEXT_COLOR);

    }
}


