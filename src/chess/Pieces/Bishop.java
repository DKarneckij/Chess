package chess.Pieces;

import chess.Board;
import chess.Player;

import java.awt.*;

public class Bishop extends Piece {
    public Bishop(Player player, int row, int col) {
        super(player, row, col);
    }

    public void updatePieceThreat(Board board) {

        //North West
        for ( int i = 1; coords.x - i >=0 & coords.y - i >=0 ; i++) {
            this.owner.threatMap[coords.x - i][coords.y - i] = true;
            if (board.getPiece(coords.x - i, coords.y - i) != null) {
                if (board.getPiece(coords.x - i, coords.y - i).pieceType().equals("King")) {
                    if (coords.x - i - 1 >= 0 &&coords.y - i -1 >= 0 && this.owner == board.getPiece(coords.x - i, coords.y - i).owner) {
                        this.owner.threatMap[coords.x - i -1][coords.y - i -1] = true;
                    }
                }
                break;
            }
        }

        //North East
        for ( int i = 1; coords.x - i >= 0 && coords.y + i < Board.BOARD_SIZE; i++) {
            this.owner.threatMap[coords.x - i][coords.y + i] = true;
            if (board.getPiece(coords.x - i, coords.y + i) != null) {
                if (board.getPiece(coords.x - i, coords.y + i).pieceType().equals("King")) {
                    if ( coords.x - i - 1 >=0 && coords.y + i +1 < Board.BOARD_SIZE && this.owner == board.getPiece(coords.x - i, coords.y + i).owner) {
                        this.owner.threatMap[coords.x - i -1][coords.y + i + 1] = true;
                    }
                }
                break;
            }
        }

        //South West
        for ( int i = 1; coords.x + i <Board.BOARD_SIZE && coords.y - i >= 0; i++) {
            this.owner.threatMap[coords.x + i][coords.y - i] = true;
            if (board.getPiece(coords.x + i, coords.y - i) != null) {
                if (board.getPiece(coords.x + i, coords.y - i).pieceType().equals("King")) {
                    if (coords.x - i - 1 < Board.BOARD_SIZE && coords.y - i -1 >= 0 && this.owner == board.getPiece(coords.x + i, coords.y - i).owner) {
                        this.owner.threatMap[coords.x + i + 1][coords.y - i -1] = true;
                    }
                }
                break;
            }
        }

        //South East
        for ( int i = 1; coords.x+i <Board.BOARD_SIZE && coords.y+i <Board.BOARD_SIZE; i++) {
            this.owner.threatMap[coords.x+i][coords.y+i] = true;
            if (board.getPiece(coords.x+i, coords.y+i) != null) {
                if (board.getPiece(coords.x + i, coords.y + i).pieceType().equals("King")) {
                    if (coords.x + i + 1 <Board.BOARD_SIZE && coords.y + i + 1 <Board.BOARD_SIZE && this.owner == board.getPiece(coords.x + i, coords.y + i).owner) {
                        this.owner.threatMap[coords.x + i + 1][coords.y +i +1] = true;
                    }
                }
                break;
            }
        }
    }

    @Override
    public boolean isLegalPieceMove(Board board, Player player, Point start, Point end) {
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

    public void printPiece() {
        System.out.print("B ");
    }
    public String pieceType() { return "Bishop"; }
}
