package chess.Pieces;

import chess.Board;
import chess.Player;

import java.awt.*;

public abstract class Piece {

    public Point coords;
    public Player owner;

    public Piece(Player player, int row, int col) {
        this.coords = new Point(row, col);
        this.owner = player;
        this.owner.addPiece(this);
    }

    public boolean isLegalMove(Board board, Player player, Point start, Point end) {

        if (!isLegalBasics(board, player, start, end)) {
            return false;
        }

        return isLegalPieceMove(board, player, start, end);
    }

    //Checks for basic legal requirement of a move done by all pieces
    public boolean isLegalBasics(Board board, Player player, Point start, Point end) {

        //Start is your piece
        if (board.getPiece(start.x, start.y).isWhite() != player.isWhite()) {
            return false;
        }

        //No need to check further if end is blank square
        if (board.getPiece(end.x, end.y) == null) {
            return true;
        }

        //Pieces are different colors
        boolean isWhiteStart = board.getPiece(start.x, start.y).isWhite();
        boolean isWhiteEnd = board.getPiece(end.x, end.y).isWhite();
        return isWhiteStart != isWhiteEnd;
    }

    public void movePiece(Board board, Point end) {
        if (board.getPiece(end.x, end.y) != null) {
            //Removes enemy piece if there
            board.getPiece(end.x, end.y).removePieceFromOwner();
        }
        //Moves piece to end
        board.setPiece(board.getPiece(coords.x, coords.y), end.x, end.y);
        board.setPiece(null, coords.x, coords.y);
        board.getPiece(end.x, end.y).updateCoords(end.x, end.y);
    }

    public void updateCoords(int x, int y) {
        this.coords = new Point(x, y);
    }

    public void removePieceFromOwner() {
        this.owner.removePiece(this);
    }

    public boolean isWhite() {
        return owner.isWhite();
    }

    abstract public void printPiece();
    abstract public boolean isLegalPieceMove(Board board, Player player, Point start, Point end);
    abstract public void updatePieceThreat(Board board);
    abstract public String pieceType();
}
