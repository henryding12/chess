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
                // check if moved 2 that a piece isn't in front of it
                if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    ChessPiece inFront = board.getPiece(new ChessPosition(r + 1, c));
                    if (inFront != null) {
                        break; // exit if a piece is blocking a double advancement
                    }
                    r = r + 2;
                } else {
                    ChessPiece inFront = board.getPiece(new ChessPosition(r - 1, c));
                    if (inFront != null) {
                        break; // exit if a piece is blocking a double advancement
                    }
                    r = r - 2;
                }
            }

            if (r >= 1 && r <= 8 && c >= 1 && c <= 8) {
                ChessPosition newPos = new ChessPosition(r, c);
                ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();
                int slotsMoved = abs(myPosition.getRow() - r);
                if ( ((i == 0 || i == 3) && board.getPiece(newPos) == null)
                    || ((i == 1 || i == 2) && board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != myColor) ) {

                    // only can move two slots if in starting position
                    if (slotsMoved == 1 || myPosition.getRow() == 2 || myPosition.getRow() == 7) {

                        // check if promotion
                        if (r == 8 || r == 1) {
                            moves.add(new ChessMove(myPosition, newPos, ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, newPos, ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, newPos, ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, newPos, ChessPiece.PieceType.BISHOP));
                        } else {
                            // else no promotion move
                            moves.add(new ChessMove(myPosition, newPos, null));
                        }

                    }

                }
            }
        }

        return moves;
    }
}
