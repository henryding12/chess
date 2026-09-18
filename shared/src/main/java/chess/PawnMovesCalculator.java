package chess;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Math.abs;

public class PawnMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int r = myPosition.getRow();
            int c = myPosition.getColumn();
            if (i == 0) {
                if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    r = r + 1;
                } else {
                    r = r - 1;
                }
            } else if (i == 1){
                //attack right
                c = c + 1;
                if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    r = r + 1;
                } else {
                    r = r - 1;
                }
            } else if (i == 2) {
                //attack left
                c = c - 1;
                if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    r = r + 1;
                } else {
                    r = r - 1;
                }
            } else {
                if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    r = r + 2;
                } else {
                    r = r - 2;
                }
            }

            if (r >= 1 && r <= 8 && c >= 1 && c <= 8) {
                ChessPosition newPos = new ChessPosition(r, c);
                ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();
                int slotsMoved = abs(myPosition.getRow() - r);
                if ((board.getPiece(newPos) == null && (i == 0 || i == 3))
                    || (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != myColor)
                    && board.getPiece(newPos).getPieceType() != ChessPiece.PieceType.KNIGHT) {

                    // only can move two slots if in starting position
                    if (slotsMoved == 1 || myPosition.getRow() == 2 || myPosition.getRow() == 7) {
                        ChessMove newMove = new ChessMove(myPosition, newPos, null);
                        moves.add(newMove);
                    }

                }
            }
        }

        return moves;
    }
}
