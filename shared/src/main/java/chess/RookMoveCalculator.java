package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMoveCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        Collection<ChessPosition> potentialPositions = new ArrayList<>();
        // start position variables
        int myPositionRow = myPosition.getRow();
        int  myPositionColumn = myPosition.getColumn();
        ChessGame.TeamColor myPositionColor = board.getPiece(myPosition).getTeamColor();

        int upDistance = 8 - myPositionRow;
        int downDistance = myPositionRow - 1;
        int leftDistance = myPositionColumn - 1;
        int rightDistance = 8 - myPositionColumn;
        // distance, row=0;column=1 , movement direction
        int[][] rookArgs = {{upDistance, 0, 1}, {downDistance, 0, -1}, {leftDistance, 1, -1}, {rightDistance, 1, 1}};
        for (var arg : rookArgs) {
            for (int i = 1; i <= arg[0]; i++) {
                int r = myPositionRow;
                int c = myPositionColumn;
                if (arg[1] == 0) {
                    r = r + i*arg[2];
                } else {
                    c = c + i*arg[2];
                }
                ChessPosition newPosition = new ChessPosition(r, c);

                if (board.getPiece(newPosition) == null) {
                    potentialPositions.add(newPosition);
                } else {
                    ChessGame.TeamColor newPositionColor = board.getPiece(newPosition).getTeamColor();
                    if (newPositionColor != myPositionColor) {
                        potentialPositions.add(newPosition);
                        break; // exits since can't move further after taking the enemy piece
                    } else {
                        // friendly piece in the spot
                        break; // exits because no friendly fire allowed
                    }
                }

            }
        }

        for (ChessPosition potentialPosition: potentialPositions){
            ChessMove newMove = new ChessMove(myPosition, potentialPosition, null);
            possibleMoves.add(newMove);
        }
        return possibleMoves;
    }
}
