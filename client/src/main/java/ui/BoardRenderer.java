package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

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

        draw(out, teamColor);
    }

    private void draw(PrintStream out, ChessGame.TeamColor teamColor) {


        drawHeader(out, teamColor);
        newLine(out);
        drawBoard(out, teamColor); // ends on the newline
        drawHeader(out, teamColor);
        newLine(out);

    }

    private void drawHeader(PrintStream out, ChessGame.TeamColor teamColor) {

        char startingText;
        int direction;
        if (teamColor == ChessGame.TeamColor.WHITE) {
            startingText = 'a';
            direction = 1;
        } else {
            startingText = 'h';
            direction = -1;
        }


        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(EMPTY);
        for (int i = 0; i < 8; i++){
            out.print(' ');
            out.print((char)(startingText+i*direction));
            out.print(' ');
        }
        out.print(EMPTY);
    }

    private void drawBoard (PrintStream out, ChessGame.TeamColor teamColor) {
        int color = 0;
        int startingRowNumber;
        int startingColNumber;
        int directionRow;
        int directionCol;
        if (teamColor == ChessGame.TeamColor.WHITE) {
            startingRowNumber = 8;
            startingColNumber = 0;
            directionRow = -1;
            directionCol = 1;
        } else {
            startingRowNumber = 1;
            startingColNumber = 9;
            directionRow = 1;
            directionCol = -1;
        }

        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 10; c++) {
                if (c == 0 || c == 9) {
                    out.print(SET_BG_COLOR_LIGHT_GREY);
                    out.print(' ');
                    out.print(startingRowNumber + r*directionRow); // header
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
                    printPiece(startingRowNumber + r*directionRow, startingColNumber + c*directionCol, out);
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

        out.print(SET_TEXT_BOLD);
        out.print(symbol);
        out.print(RESET_TEXT_COLOR);
        out.print(RESET_TEXT_BOLD_FAINT);

    }
}


