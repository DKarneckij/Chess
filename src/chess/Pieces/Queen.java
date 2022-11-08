package chess.Pieces;

import chess.Board;
import chess.Player;

import java.awt.*;

public class Queen extends Piece {
    public Queen(Player player, int row, int col) {
        super(player, row, col);
    }

    public void updatePieceThreat(Board board) {
        updatePieceThreatBishop(board);
        updatePieceThreatRook(board);
    }

    public void updatePieceThreatBishop(Board board) {
        //North West
        for ( int i = 1; coords.x - i >=0 & coords.y + i < Board.BOARD_SIZE; i++) {
            this.owner.threatMap[coords.x - i][coords.y + i] = true;
            if (board.getPiece(coords.x - i, coords.y + i) != null) {
                break;
            }
        }

        //North East
        for ( int i = 1; coords.x + i < Board.BOARD_SIZE & coords.y + i < Board.BOARD_SIZE; i++) {
            this.owner.threatMap[coords.x + i][coords.y + i] = true;
            if (board.getPiece(coords.x + i, coords.y + i) != null) {
                break;
            }
        }

        //South West
        for ( int i = 1; coords.x - i >=0 & coords.y - i >= 0; i++) {
            this.owner.threatMap[coords.x - i][coords.y - i] = true;
            if (board.getPiece(coords.x - i, coords.y - i) != null) {
                break;
            }
        }

        //South East
        for ( int i = 1; coords.x+i <Board.BOARD_SIZE & coords.y-i >=0; i++) {
            this.owner.threatMap[coords.x+i][coords.y-i] = true;
            if (board.getPiece(coords.x+i, coords.y-i) != null) {
                break;
            }
        }
    }

    public void updatePieceThreatRook(Board board) {

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

    public boolean isLegalPieceMove(Board board, Player player, Point start, Point end) {
        if (start.x == end.x || start.y == end.y) {
            return isLegalPieceMoveRook(board, player, start,end);
        } else {
            return isLegalPieceMoveBishop(board, player, start, end);
        }
    }

    public boolean isLegalPieceMoveBishop(Board board, Player player, Point start, Point end) {

        if(start.x == end.x || start.y == end.y){
            //A Bishop does not move diagonally
            return false;
        }

        if (Math.abs(start.x - end.x) != Math.abs(start.y - end.y)) {
            return false;
        }

        //South West
        if (start.x < end.x && start.y > end.y) {
            for (int i = 1; coords.x + i != end.x; i++) {
                if (board.getPiece(coords.x + i, coords.y - i) != null) {
                    return false;
                }
            }
        }

        //North East
        if (start.x > end.x && start.y < end.y) {
            for (int i = 1; coords.x - i != end.x; i++) {
                if (board.getPiece(coords.x - i, coords.y + i) != null) {
                    return false;
                }
            }
        }
//
        //South East
        if (start.x < end.x && start.y < end.y) {
            for (int i = 1; coords.x + i != end.x; i++) {
                if (board.getPiece(coords.x + i, coords.y + i) != null) {
                    return false;
                }
            }
        }
//
//        //North West
        if (start.x > end.x && start.y > end.y) {
            for (int i = 1; coords.x - i != end.x; i++) {
                if (board.getPiece(coords.x - i, coords.y - i) != null) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isLegalPieceMoveRook(Board board, Player player, Point start, Point end) {

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

    public void printPiece() {
        System.out.print("Q ");
    }
    public String pieceType() { return "Queen"; }
}
