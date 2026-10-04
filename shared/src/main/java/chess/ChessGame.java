package chess;

import java.util.Collection;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard theBoard = new ChessBoard();
    private ChessGame.TeamColor turn;
    public ChessGame() {
        turn = TeamColor.WHITE;
        theBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // variables
        ChessPosition kingPosition = null;
        ChessPosition position; // placeholder for chess positions
        ChessPiece piece; // placeholder for the chess piece
        TeamColor positionColor;
        String symbol; // placeholder for chess symbols
        Collection<ChessMove> enemyMoves; // placeholder for enemy moves

        TeamColor enemyColor; // placeholder for the enemy color
        if (teamColor == TeamColor.WHITE) {
            enemyColor = TeamColor.BLACK;
        } else {
            enemyColor = TeamColor.WHITE;
        }

        // iterate the board
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                position = new ChessPosition(r, c);
                piece = theBoard.getPiece(position);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    positionColor = piece.getTeamColor();
                    // locate the position of the king of the interested color
                    if (teamColor == TeamColor.WHITE && positionColor == TeamColor.WHITE) {
                        kingPosition = position;
                    }
                    if (teamColor == TeamColor.BLACK && positionColor == TeamColor.BLACK) {
                        kingPosition = position;
                    }
                }

            }
        }


        // iterate the board
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                position = new ChessPosition(r, c);
                piece = theBoard.getPiece(position);
                if (piece != null) {
                    positionColor = piece.getTeamColor();

                    // identify if enemy
                    if (positionColor == enemyColor) {
                        // does the enemy attack the king square?
                        enemyMoves = piece.pieceMoves(theBoard, position);
                        for (ChessMove move : enemyMoves) {
                            if (move.getEndPosition().equals(kingPosition)) {
                                return true;
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        theBoard = board;

    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return theBoard;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
