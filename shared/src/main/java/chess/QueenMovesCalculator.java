package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        var RookCalc = new RookMovesCalculator();
        var BishopCalc = new BishopMovesCalculator();

        moves.addAll(RookCalc.pieceMoves(board, myPosition));
        moves.addAll(BishopCalc.pieceMoves(board, myPosition));
        return moves;
    }
}
