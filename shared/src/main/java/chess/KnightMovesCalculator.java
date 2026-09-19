package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            int r = myPosition.getRow();
            int c = myPosition.getColumn();
            switch(i){
                case 0:
                    // top right
                    r = r + 2;
                    c = c + 1;
                    break;
                case 1:
                    r = r + 1;
                    c = c + 2;
                    break;
                case 2:
                    // top left
                    r = r + 2;
                    c = c - 1;
                    break;
                case 3:
                    r = r + 1;
                    c = c - 2;
                    break;
                case 4:
                    // bottom right
                    r = r - 2;
                    c = c + 1;
                    break;
                case 5:
                    r = r - 1;
                    c = c + 2;
                    break;
                case 6:
                    // bottom left
                    r = r - 2;
                    c = c - 1;
                    break;
                case 7:
                    r = r - 1;
                    c = c - 2;
                    break;
            }
            if (r >= 1 && r <= 8 && c >= 1 && c <= 8){
                ChessPosition newPos = new ChessPosition(r, c);
                if (board.getPiece(newPos) == null || board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove newMove = new ChessMove(myPosition, newPos, null);
                    moves.add(newMove);
                }

            }
        }


        return moves;
    }
}
