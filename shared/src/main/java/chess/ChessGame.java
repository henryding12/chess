package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
        // variables for this function
        Collection<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = theBoard.getPiece(startPosition);
        ChessBoard temporaryBoard; // placeholder board

        // if no piece at start then return null
        if (piece == null) {
            return null;
        }
        // get all possible moves from PieceMove()
        Collection<ChessMove> potentialMoves = piece.pieceMoves(theBoard, startPosition);
            // iterate through each move
            for (ChessMove move : potentialMoves) {
                // deep copy theBoard
                temporaryBoard = new ChessBoard(theBoard);
                // make the move (private helper)
                makeMove(move, temporaryBoard);
                // check if the king is in check
                if (!isInCheck(piece.getTeamColor(), temporaryBoard)) {
                    // append to moves if the king isn't in check
                    moves.add(move);
                }
            }



        return moves;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = theBoard.getPiece(move.getStartPosition());

        // check if a valid piece exists to move
        if(piece == null) {
            throw new InvalidMoveException("No piece there!");
        }
        // check if out of turn
        if (piece.getTeamColor() != turn) {
            throw new InvalidMoveException("Not your turn!");
        }

        Collection<ChessMove> validCollectionOfMoves = validMoves(move.getStartPosition());
        boolean apartOf = false;
        for (ChessMove  moveInstance : validCollectionOfMoves) {
            if (moveInstance.getEndPosition().equals(move.getEndPosition())) {
                apartOf = true;
                break;
            }
        }
        if (!apartOf) {
            throw new InvalidMoveException("Not a valid move!");
        } else {
            // successfully move piece
            makeMove(move, theBoard);
            // set the turn for next
            if (getTeamTurn() == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else {
                setTeamTurn(TeamColor.WHITE);
            }
        }
    }

    private void makeMove(ChessMove move, ChessBoard board) {
        // if no promotion
        if (move.getPromotionPiece() == null) {
            // set endPosition to that piece
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        } else {
            // set endPosition to the promotion piece
            board.addPiece(move.getEndPosition(), new ChessPiece(board.getPiece(move.getStartPosition()).getTeamColor(), move.getPromotionPiece()));
        }
        // remove the old Position (startPosition)
        board.addPiece(move.getStartPosition(), null);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */

    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(teamColor, theBoard);
    }

    private boolean isInCheck(TeamColor teamColor, ChessBoard board) {
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
                piece = board.getPiece(position);
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
                piece = board.getPiece(position);
                if (piece != null) {
                    positionColor = piece.getTeamColor();

                    // identify if enemy
                    if (positionColor == enemyColor) {
                        // does the enemy attack the king square?
                        enemyMoves = piece.pieceMoves(board, position);
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
        // is team in Check?
        if (!isInCheck(teamColor)) {
            // return false if not
            return false;
        }
        // find all the team pieces on the board
        ChessPosition temporaryPosition; // placeholder
        ChessPiece temporaryPiece; // placeholder
        Collection<ChessPosition> positions = new ArrayList<>(); // placeholder for chess positions of team
        // iterate the board
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                temporaryPosition = new ChessPosition(r, c);
                temporaryPiece = theBoard.getPiece(temporaryPosition);
                // check if a piece even exists there
                if (temporaryPiece != null) {
                    if (temporaryPiece.getTeamColor() == teamColor) {
                        positions.add(temporaryPosition);
                    }
                }
            }
        }

        // for each temporaryPiece identify if any valid moves can be made
        for (ChessPosition position : positions) {
            // if found valid move, return false
            if (!validMoves(position).isEmpty()) {
                return false;
            }
        }

        return true;
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(theBoard, chessGame.theBoard) && turn == chessGame.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(theBoard, turn);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "theBoard=" + theBoard +
                ", turn=" + turn +
                '}';
    }
}
