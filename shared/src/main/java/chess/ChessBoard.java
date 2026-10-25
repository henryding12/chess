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

    private ChessPiece[][] squares = new ChessPiece[8][8];
    // define pieces
    private final ChessPiece whitePawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    private final ChessPiece whiteRook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    private final ChessPiece whiteKnight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
    private final ChessPiece whiteBishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
    private final ChessPiece whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
    private final ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
    private final ChessPiece blackPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    private final ChessPiece blackRook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    private final ChessPiece blackKnight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
    private final ChessPiece blackBishop = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
    private final ChessPiece blackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
    private final ChessPiece blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);

    public ChessBoard() {
    }
    public ChessBoard(ChessBoard other) {
        this.squares = new ChessPiece[other.squares.length][];
        for (int r = 0; r < squares.length; r++) {
            this.squares[r] = other.squares[r].clone();
        }
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
        squares[7][0] = blackRook;
        squares[7][1] = blackKnight;
        squares[7][2] = blackBishop;
        squares[7][3] = blackQueen;
        squares[7][4] = blackKing;
        squares[7][5] = blackBishop;
        squares[7][6] = blackKnight;
        squares[7][7] = blackRook;
        squares[6][0] = blackPawn;
        squares[6][1] = blackPawn;
        squares[6][2] = blackPawn;
        squares[6][3] = blackPawn;
        squares[6][4] = blackPawn;
        squares[6][5] = blackPawn;
        squares[6][6] = blackPawn;
        squares[6][7] = blackPawn;
        squares[0][0] = whiteRook;
        squares[0][1] = whiteKnight;
        squares[0][2] = whiteBishop;
        squares[0][3] = whiteQueen;
        squares[0][4] = whiteKing;
        squares[0][5] = whiteBishop;
        squares[0][6] = whiteKnight;
        squares[0][7] = whiteRook;
        squares[1][0] = whitePawn;
        squares[1][1] = whitePawn;
        squares[1][2] = whitePawn;
        squares[1][3] = whitePawn;
        squares[1][4] = whitePawn;
        squares[1][5] = whitePawn;
        squares[1][6] = whitePawn;
        squares[1][7] = whitePawn;

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

    @Override
    public String toString() {
        String output;
        output = "|";
        ChessPiece piece;
        for (int r = 7; r >= 0; r--) {
            for (int c = 0; c <= 7; c++) {
                piece = squares[r][c];
                if (piece == null) {
                    output = output + " " + "|";
                } else {
                    output = output + piece.getSymbol() + "|";
                }

            }
            // adding a newline between rows
            if (r > 0) {
                output = output + "\n" + "|";
            }
        }
        return output;

    }
}
