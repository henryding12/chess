package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    ChessPiece[][] squares = new ChessPiece[8][8];
    // set board
    ChessPiece P = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    ChessPiece R = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    ChessPiece N = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
    ChessPiece B = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
    ChessPiece Q = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
    ChessPiece K = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
    ChessPiece p = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    ChessPiece r = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    ChessPiece n = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    ChessPiece b = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    ChessPiece k = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
    ChessPiece q = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);

    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[8][8];
        squares[7][0] = r;
        squares[7][1] = n;
        squares[7][2] = b;
        squares[7][3] = q;
        squares[7][4] = k;
        squares[7][5] = b;
        squares[7][6] = n;
        squares[7][7] = r;
        squares[6][0] = p;
        squares[6][1] = p;
        squares[6][2] = p;
        squares[6][3] = p;
        squares[6][4] = p;
        squares[6][5] = p;
        squares[6][6] = p;
        squares[6][7] = p;
        squares[0][0] = R;
        squares[0][1] = N;
        squares[0][2] = B;
        squares[0][3] = Q;
        squares[0][4] = K;
        squares[0][5] = B;
        squares[0][6] = N;
        squares[0][7] = R;
        squares[1][0] = P;
        squares[1][1] = P;
        squares[1][2] = P;
        squares[1][3] = P;
        squares[1][4] = P;
        squares[1][5] = P;
        squares[1][6] = P;
        squares[1][7] = P;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
