package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveUtils {
    public static Collection<ChessMove> slide(ChessBoard board, ChessPosition myPosition, int[][] movement) {
        Collection<ChessMove> moves = new ArrayList<>();

        int myPositionRow = myPosition.getRow();
        int myPositionColumn = myPosition.getColumn();


        for (int[] move: movement) {
            int row = myPositionRow;
            int col = myPositionColumn;

            while(true){

                // movement
                row = row + move[0];
                col = col + move[1];

                // keep moving until you reach the edge
                if (row < 1 || row > 8 || col < 1 || col > 8) {
                    break;
                }

                // new Position
                ChessPosition newPosition = new ChessPosition(row, col);
                ChessMove newMove = new ChessMove(myPosition, newPosition, null);

                // if nothing is there to contest then continue else
                if (board.getPiece(newPosition) == null) {
                    moves.add(newMove);
                } else {
                    ChessGame.TeamColor positionColor = board.getPiece(newPosition).getTeamColor();
                    ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();

                    if (positionColor != myColor) {
                        // if enemy piece, then kill and move one then stop
                        moves.add(newMove);
                        break;
                    } else {
                        // if friendly piece can't move anymore and stop
                        break;
                    }
                }
            }
        }

        return moves;
    }

}
