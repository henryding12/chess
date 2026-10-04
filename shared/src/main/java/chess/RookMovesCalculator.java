package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator{
    private static int[][] straight = {{1,0}, {-1, 0}, {0, 1}, {0, -1}};

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        return MoveUtils.slide(board, myPosition, straight, 1000);
    }

}
