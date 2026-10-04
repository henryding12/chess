package chess;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Math.abs;

public class MoveUtils {
    public static Collection<ChessMove> slide (ChessBoard board, ChessPosition myPosition, int[][] movement, int steps){
        Collection<ChessMove> moves = new ArrayList<>();


        for (int[] direction: movement) {
            int count = 0;

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (count < steps) {
                // movement
                row = row + direction[0];
                col = col + direction[1];

                // check if in bounds
                if (row < 1 || row > 8 || col < 1 || col > 8) {
                    // not in bounds so exit loop
                    break;
                }
                // check if a piece already exists
                ChessPosition newPosition = new ChessPosition(row, col);
                ChessMove newMove = new ChessMove(myPosition, newPosition, null);
                if (board.getPiece(newPosition) == null){
                    moves.add(newMove);
                } else {
                    ChessGame.TeamColor color = board.getPiece(newPosition).getTeamColor();
                    ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();
                    // check if there is an Enemy Piece to capture
                    if (color != myColor) {
                        moves.add(newMove);
                        break;
                    } else {
                        // friendly piece so don't do anything since you are blocked
                        break;
                    }
                }
                count++;
            }

        }

        return moves;
    }

    public static Collection<ChessMove> pawn (ChessBoard board, ChessPosition myPosition, int[][] movement, int steps){
        Collection<ChessMove> moves = new ArrayList<>();


        for (int[] direction: movement) {
            int count = 0;

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (count < steps) {
                // color of the piece being moved
                ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();


                // movement
                if (myColor == ChessGame.TeamColor.WHITE && direction[0] < 0) {
                    // white can only move forward
                    break;
                }
                if (myColor == ChessGame.TeamColor.BLACK && direction[0] > 0) {
                    // black can only move negative
                    break;
                }
                row = row + direction[0];
                col = col + direction[1];

                // check if in bounds
                if (row < 1 || row > 8 || col < 1 || col > 8) {
                    // not in bounds so exit loop
                    break;
                }


                // if move two, on starting row?
                int moveDistance = abs(row - myPosition.getRow());
                if (moveDistance == 2 && (myPosition.getRow() != 2 && myPosition.getRow() != 7)) {
                    // must be on a starting row if moving two
                    break;
                }
                if (moveDistance == 2 ) {
                    ChessPiece WhitePieceBefore = board.getPiece(new ChessPosition(row - 1, col));
                    ChessPiece BlackPieceBefore = board.getPiece(new ChessPosition(row + 1, col));
                    // only can move two from starting positions and can't move past a piece
                    if (myColor == ChessGame.TeamColor.WHITE && (myPosition.getRow() != 2 || WhitePieceBefore != null)) {
                        break;
                    }
                    if (myColor == ChessGame.TeamColor.BLACK && (myPosition.getRow() != 7 || BlackPieceBefore != null)) {
                        break;
                    }

                }


                // check if a piece already exists
                ChessPosition newPosition = new ChessPosition(row, col);
                ChessMove newMove = new ChessMove(myPosition, newPosition, null);

                // only can move laterally if a piece exists to attack
                if (direction[1] != 0 && board.getPiece(newPosition) == null) {
                    break;
                }

                if (board.getPiece(newPosition) == null) {
                    if ((myColor == ChessGame.TeamColor.WHITE && row == 8) || (myColor == ChessGame.TeamColor.BLACK && row == 1)) {
                        // if promotion then reflect promotion
                        moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
                    } else {
                        moves.add(newMove);
                    }
                } else {
                    ChessGame.TeamColor color = board.getPiece(newPosition).getTeamColor();

                    // check if there is an Enemy Piece to capture, pawn can only capture diagonal
                    if (color != myColor && direction[1] != 0) {
                        if ((myColor == ChessGame.TeamColor.WHITE && row == 8) || (myColor == ChessGame.TeamColor.BLACK && row == 1)) {
                            // if promotion then reflect promotion
                            moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
                        } else {
                            moves.add(newMove);
                            break;
                        }

                    } else {
                        // friendly piece so don't do anything since you are blocked
                        break;
                    }
                }
                count++;
            }

        }

        return moves;
    }


}
