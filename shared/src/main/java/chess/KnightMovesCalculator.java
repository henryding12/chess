package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator implements PieceMovesCalculator{
    private static int[][] knight = {
            {2,1}, {2, -1}, {-2, -1}, {-2, 1}
            , {1,2}, {1, -2}, {-1, 2}, {-1, -2}
    };

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        return MoveUtils.slide(board, myPosition, knight, 1);
    }

}
