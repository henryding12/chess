package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPosition newPos = new ChessPosition(4, 4);
        ChessMove newMove = new ChessMove(myPosition, newPos, null);
        moves.add(newMove);
        return moves;
    }
}
