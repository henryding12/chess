package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMoveCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();

        int upDist = 8 - r;
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                // move up
                for
            }
        }
        for (int i = 0; i < upDist + 1; i++) {
            r = r + i;
        }


        ChessPosition newPos = new ChessPosition(r, c);
        ChessMove newMove = new ChessMove(myPosition, newPos, null);
        moves.add(newMove);
        return moves;
    }
}
