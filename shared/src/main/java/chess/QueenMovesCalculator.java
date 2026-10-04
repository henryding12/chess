package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator{

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        moves.addAll((new BishopMovesCalculator()).pieceMoves(board, myPosition));
        moves.addAll((new RookMovesCalculator()).pieceMoves(board, myPosition));

        return moves;
    }

}
