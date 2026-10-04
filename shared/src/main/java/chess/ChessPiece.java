package chess;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
        PieceMovesCalculator calc;
        switch(this.getPieceType()) {
            case BISHOP:
                calc = new BishopMovesCalculator();
                break;
            case KING:
                calc = new KingMovesCalculator();
                break;
            case KNIGHT:
                calc = new KnightMovesCalculator();
                break;
            case PAWN:
                calc = new PawnMovesCalculator();
                break;
            case ROOK:
                calc = new RookMovesCalculator();
                break;
            case QUEEN:
                calc = new QueenMovesCalculator();
                break;
            default:
                throw new IllegalMonitorStateException("Unknown piece type" + this.getPieceType());
        }
        return calc.pieceMoves(board, myPosition);
    }

    public String getSymbol() {
        if (getPieceType() == PieceType.PAWN) {
            if (getTeamColor() == ChessGame.TeamColor.WHITE) {
                return "P";
            } else {
                return "p";
            }
         } else if (getPieceType() == PieceType.ROOK) {
            if (getTeamColor() == ChessGame.TeamColor.WHITE) {
                return "R";
            } else {
                return "r";
            }
        } else if (getPieceType() == PieceType.KNIGHT) {
            if (getTeamColor() == ChessGame.TeamColor.WHITE) {
                return "N";
            } else {
                return "n";
            }
        } else if (getPieceType() == PieceType.BISHOP) {
            if (getTeamColor() == ChessGame.TeamColor.WHITE) {
                return "B";
            } else {
                return "b";
            }
        } else if (getPieceType() == PieceType.QUEEN) {
            if (getTeamColor() == ChessGame.TeamColor.WHITE) {
                return "Q";
            } else {
                return "q";
            }
        } else if (getPieceType() == PieceType.KING) {
            if (getTeamColor() == ChessGame.TeamColor.WHITE) {
                return "K";
            } else {
                return "k";
            }
        }  else {
            return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
