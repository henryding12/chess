package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator implements PieceMovesCalculator{
    private static int[][] king = {
            {1,0}, {-1, 0}, {0, 1}, {0, -1}
            , {1,1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        return MoveUtils.slide(board, myPosition, king, 1);
    }

}
