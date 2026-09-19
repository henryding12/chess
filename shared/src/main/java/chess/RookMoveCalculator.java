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
        int[][] rookArgs = {{upDistance, 1}, {downDistance, -1}};
        for (var arg : rookArgs) {
            for (int i = 1; i < arg[0]; i=i+arg[1]) {
                ChessPosition newPosition = new ChessPosition(myPositionRow + i*arg[1], myPositionColumn);
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
