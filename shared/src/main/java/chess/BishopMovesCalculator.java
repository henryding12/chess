package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int r = myPosition.getRow();
            int c = myPosition.getColumn();

            // check for movement criteria
            boolean hitPiece = false;
            while(!hitPiece) {
                if (i == 0) {
                    // towards top right
                    r = r + 1;
                    c = c + 1;
                } else if (i == 1) {
                    // towards bottom left
                    r = r - 1;
                    c = c - 1;
                } else if (i == 2) {
                    // towards top left
                    r = r + 1;
                    c = c - 1;
                } else {
                    // towards bottom left
                    r = r - 1;
                    c = c + 1;
                }
                // is the position in the board?
                if (r < 1 || r > 8 || c < 1 || c > 8) {
                    break;
                }
                ChessPosition newPos = new ChessPosition(r, c);
                // do we hit a piece?
                if (board.getPiece(newPos) != null) {
                    hitPiece = true;
                }
                // can you add the piece? only add when no piece or an enemy piece
                if (board.getPiece(newPos) == null || board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove newMove = new ChessMove(myPosition, newPos, null);
                    moves.add(newMove);
                }
            }
        }

        return moves;
    }
}
