package chess.Pieces;

import chess.Board;
import chess.Player;

import java.awt.*;

public class Rook extends Piece {

    public boolean hasMoved;

    public Rook(Player player, int row, int col) {
        super(player, row, col);
        this.hasMoved = false;
    }

    public void updatePieceThreat(Board board) {

        //East
        for (int y = coords.y + 1; y < Board.BOARD_SIZE; y++) {
            this.owner.threatMap[coords.x][y] = true;
            if (board.getPiece(coords.x, y) != null) {
                if (board.getPiece(coords.x, y).pieceType().equals("King")) {
                    if (this.owner != board.getPiece(coords.x, y).owner && y + 1 < 8) {
                        this.owner.threatMap[coords.x][y + 1] = true;
                    }
                }
                break;
            }
        }

        //South
        for (int x = coords.x + 1; x < Board.BOARD_SIZE; x++) {
            this.owner.threatMap[x][coords.y] = true;
            if (board.getPiece(x, coords.y) != null) {
                if (board.getPiece(x, coords.y).pieceType().equals("King")) {
                    if (this.owner != board.getPiece(x, coords.y).owner && x + 1 < 8) {
                        this.owner.threatMap[x + 1][coords.y] = true;
                    }
                }
                break;
            }
        }

        //West
        for (int y = coords.y - 1; y >= 0; y--) {
            this.owner.threatMap[coords.x][y] = true;
            if (board.getPiece(coords.x, y) != null) {
                if (board.getPiece(coords.x, y).pieceType().equals("King")) {
                    if (this.owner != board.getPiece(coords.x, y).owner && y - 1 >= 0) {
                        this.owner.threatMap[coords.x][y - 1] = true;
                    }
                }
                break;
            }
        }
//
        //North
        for (int x = coords.x - 1; x >= 0; x--) {
            this.owner.threatMap[x][coords.y] = true;
            if (board.getPiece(x, coords.y) != null) {
                if (board.getPiece(x, coords.y).pieceType().equals("King")) {
                    if (this.owner != board.getPiece(x, coords.y).owner && x - 1 >= 0) {
                        this.owner.threatMap[x - 1][coords.y] = true;
                    }
                }
                break;
            }
        }
    }

    @Override
    public boolean isLegalPieceMove(Board board, Player player, Point start, Point end) {

        if(start.x != end.x && start.y != end.y){
            //A rook does not move along a row/column only
            return false;
        }

        int offset;

        if(start.x != end.x){
            //rows
            if(start.x < end.x){
                offset = 1;
            }else{
                offset = -1;
            }
            for(int x = start.x + offset; x != end.x; x += offset){
                if(board.getPiece(x, start.y) != null ) {
                    return false;
                }
            }
        }

        if(start.y != end.y){
            //columns
            if(start.y < end.y){
                offset = 1;
            }else{
                offset = -1;
            }
            for(int y = start.y + offset; y != end.y; y += offset){
                if(board.getPiece(start.x, y) != null){
                    return false;
                }
            }
        }

        //Return true when no piece in between
        return true;
    }

    public void movePiece(Board board, Point end) {
        super.movePiece(board, end);
        hasMoved = true;
    }

    public void printPiece() {
        System.out.print("R ");
    }
    public String pieceType() { return "Rook"; }
}
