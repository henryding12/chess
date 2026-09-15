package chess;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        // kingMiddleOfBoard()
        for (int i = 0; i < 8; i++) {
            int r = myPosition.getRow();
            int c = myPosition.getColumn();
            switch(i) {
                // cross
                case 0:
                    c = c + 1;
                    break;
                case 1:
                    c = c - 1;
                    break;
                case 2:
                    r = r + 1;
                    break;
                case 3:
                    r = r - 1;
                    break;
                // diagonal
                case 4:
                    r = r + 1;
                    c = c + 1;
                    break;
                case 5:
                    r = r - 1;
                    c = c - 1;
                    break;
                case 6:
                    r = r + 1;
                    c = c - 1;
                    break;
                case 7:
                    r = r - 1;
                    c = c + 1;
                    break;
            }

            ChessPosition newPos = new ChessPosition(r, c);
            ChessMove newMove = new ChessMove(myPosition, newPos, null);
            // new move is within board
            if (r >= 1 && r <= 8 && c >= 1 && c <= 8) {
                // kingBlocked()
                if (board.getPiece(newPos) == null || board.getPiece(myPosition).getTeamColor() != board.getPiece(newPos).getTeamColor()){
                    moves.add(newMove);
                }
            }

        }

        return moves;
    }
}
