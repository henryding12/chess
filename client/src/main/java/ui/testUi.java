package ui;

import chess.ChessBoard;
import chess.ChessGame;

public class testUi {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        ChessGame.TeamColor color = ChessGame.TeamColor.WHITE;
        BoardRenderer render = new BoardRenderer(board, color);

    }
}
