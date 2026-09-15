package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int r = myPosition.getRow();
        int c = myPosition.getColumn();
        // need to allow pawns to attack pieces for promotion
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            r = r + 1;
        } else {
            r = r - 1;
        }
        if (r >= 1 && r <= 8 && c >= 1 && c <= 8) {
            ChessPosition newPos = new ChessPosition(r, c);
            if (board.getPiece(newPos) == null || board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(newPos).getPieceType() != ChessPiece.PieceType.KNIGHT) {
                ChessMove newMove = new ChessMove(myPosition, newPos, null);
                moves.add(newMove);
            }
        }
        return moves;
    }
}
