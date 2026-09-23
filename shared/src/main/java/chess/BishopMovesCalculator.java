package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator{
    private static final int[][] movement = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};


    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {


        return MoveUtils.slide(board, myPosition, movement);
    }


}
