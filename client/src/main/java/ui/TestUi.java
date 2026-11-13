package ui;

import chess.ChessBoard;
import chess.ChessGame;

public class TestUi {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        ChessGame.TeamColor color = ChessGame.TeamColor.BLACK;
        BoardRenderer render = new BoardRenderer(board, color);

    }
}
