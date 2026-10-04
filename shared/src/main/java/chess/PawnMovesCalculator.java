package chess;

import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator{
    private static int[][] pawn = {
            {1,0}, {2, 0}, {1, 1}, {1, -1} // white
            , {-1,0}, {-2, 0}, {-1, 1}, {-1, -1} // black
    };

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        return MoveUtils.pawn(board, myPosition, pawn, 1);
    }

}
