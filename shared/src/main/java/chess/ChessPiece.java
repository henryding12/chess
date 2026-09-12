package chess;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPosition[] pos = {
//                new ChessPosition(1,8)
//                , new ChessPosition(2,1)
//                , new ChessPosition(2,7)
//                , new ChessPosition(3,2)
//                , new ChessPosition(3,6)
//                , new ChessPosition(4,3)
//                , new ChessPosition(4,5)
//                , new ChessPosition(6, 3)
//                , new ChessPosition(6, 5)
//                , new ChessPosition(7, 2)
//                , new ChessPosition(7, 6)
//                , new ChessPosition(8, 1)
//                , new ChessPosition(8, 7)
        };
//        for (var p:pos) {
//            ChessMove m = new ChessMove(myPosition, p, null);
//            moves.add(m);
//        }
        if (this.getPieceType() == PieceType.BISHOP) {

            for (int i = 0; i < 4; i++) {
                int r = myPosition.getRow();
                int c = myPosition.getColumn();

                // check for movement criteria
                boolean hitPiece = false;
                while(!hitPiece && r > 1 && r < 8 && c > 1 && c < 8) {
                    if (i == 0) {
                        // towards top right
                        r = r + 1;
                        c = c + 1;
                    } else if (i == 1) {
                        // towards bottom left
                        r = r - 1;
                        c = c - 1;
                    } else if (i == 2) {
                        // towards top left
                        r = r + 1;
                        c = c - 1;
                    } else {
                        // towards bottom left
                        r = r - 1;
                        c = c + 1;
                    }

                    ChessPosition newPos = new ChessPosition(r, c);
                    // do we hit an enemy piece?
                    if (board.getPiece(newPos) != null) {
                        hitPiece = true;
                    }
                    // can you add the piece? only add when no piece or an enemy piece
                    if (board.getPiece(newPos) == null || board.getPiece(newPos).getTeamColor() != this.getTeamColor()) {
                        ChessMove newMove = new ChessMove(myPosition, newPos, null);
                        moves.add(newMove);
                    }
                }
            }


        }



//        if (getPieceType() == PieceType.BISHOP) {
//            // calculate potential moves
//            // take starting position
//            // add diagonal until you reach the end
//        }
        return moves;
    }
}
