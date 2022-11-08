package chess.Pieces;

import chess.Board;
import chess.Player;

import java.awt.*;

public class Pawn extends Piece {

    public boolean hasMoved;

    public Pawn(Player player, int row, int col) {
        super(player, row, col);
        this.hasMoved = false;
    }

    public void updatePieceThreat(Board board) {
        int rowDir;
        if (this.owner.isWhite()) {
            rowDir = -1;
        } else {
            rowDir = 1;
        }

        if (coords.y - 1 >= 0) {
            this.owner.threatMap[coords.x + rowDir][coords.y - 1] = true;
        }
        if (coords.y + 1 < 8) {
            this.owner.threatMap[coords.x + rowDir][coords.y + 1] = true;
        }
    }

    public boolean isLegalPieceMove(Board board, Player player, Point start, Point end) {

        int rowDiff = end.x - start.x;

        //Pieces are going the correct direction
        if (this.owner.isWhite()) {
            if (start.x - end.x < 1) {
                //White going down
                return false;
            }
        } else {
            if (start.x - end.x > - 1) {
                //Black going up
                return false;
            }
        }

        if (this.hasMoved && Math.abs(rowDiff) == 2) {
            //Pawn that has moved attempting to move 2 squares
            return false;
        }

        if (Math.abs(rowDiff) == 2 && (board.getPiece(end.x, end.y) != null || board.getPiece(start.x + rowDiff, start.y) != null)) {
            //Pawn moving up 2 squares on top of a piece
            return false;
        }

        Piece endPiece = board.getPiece(end.x, end.y);
        if (start.y == end.y && endPiece != null) {
            return false;
        }

        if (start.y != end.y) {
            //Moving sideways
            int colDiff = end.y - start.y;

            if(Math.abs(colDiff) != 1) {
                //Moving multiple squares sideways
                return false;
            }

            if (board.getPiece(end.x, end.y) == null && board.getPiece(end.x+rowDiff, end.y) != board.lastTurnPawnMovedTwo()) {
                //Check if doing en passant, if not then can't move sideways
                return false;
            }

        }

        return true;

    }

    public void promotePawn(Board board, Point end, char pieceName) {

        Piece piece = null;

        if (pieceName == 'R') {
            piece = new Rook(this.owner, end.x, end.y);
        } else if (pieceName == 'N') {
            piece = new Knight(this.owner, end.x, end.y);
        } else if (pieceName == 'B') {
            piece = new Bishop(this.owner, end.x, end.y);
        } else if (pieceName == 'Q') {
            piece = new Queen(this.owner, end.x, end.y);
        }
        board.setPiece(null, coords.x, coords.y);
        this.removePieceFromOwner();
        board.setPiece(piece, end.x, end.y);
    }

    public void movePiece(Board board, Point end) {

        int rowDiff = coords.x - end.x;

        if (board.getPiece(end.x, end.y) != null) {
            //Removes enemy piece if there
            board.getPiece(end.x, end.y).removePieceFromOwner();
        }

        if (coords.y != end.y && board.getPiece(end.x+rowDiff, end.y) != null && board.getPiece(end.x+rowDiff, end.y) == board.lastTurnPawnMovedTwo()) {
            //Check if doing en passant
            board.getPiece(end.x+rowDiff, end.y).removePieceFromOwner();
            board.setPiece(null, end.x+rowDiff, end.y);
        }

        //Moves piece to end
        board.setPiece(board.getPiece(coords.x, coords.y), end.x, end.y);
        board.setPiece(null, coords.x, coords.y);
        board.getPiece(end.x, end.y).updateCoords(end.x, end.y);

        if (Math.abs(rowDiff) == 2) {
            //Let board know that this pawn has moved 2 squares
            board.updatePawnMovedTwo(this);
        }

        this.hasMoved = true;
    }

    public void printPiece() {
        System.out.print("p ");
    }
    public String pieceType() { return "Pawn"; }
}
