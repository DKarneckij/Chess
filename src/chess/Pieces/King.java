package chess.Pieces;

import chess.Board;
import chess.Player;

import java.awt.*;

public class King extends Piece {
    public boolean hasMoved;
    public King(Player player, int row, int col) {
        super(player, row, col);
        this.hasMoved = false;
    }

    public boolean isCheckMated(Board board) {

        if (canMoveAway(board)) {
            return false;
        }

        Piece attacker = findAttacker(board);

        owner.removeThreatMapPinnedAndKing(board, attacker);

        boolean canStopAttacker = false;

        if (attacker.pieceType().equals("Knight")) {
            if(canStopKnight(attacker)) {
                canStopAttacker = true;
            }
        } else if (attacker.pieceType().equals("Queen")) {
            if (attacker.coords.x != coords.x && attacker.coords.y != coords.y) {
                if (canStopBishop(attacker)) {
                    canStopAttacker = true;
                }
            } else {
                if (canStopRook(attacker)) {
                    canStopAttacker = true;
                }
            }
        } else if (attacker.pieceType().equals("Bishop")) {
            if (canStopBishop(attacker)) {
                canStopAttacker = true;
            }
        } else if (attacker.pieceType().equals("Rook")) {
            if (canStopRook(attacker)) {
                canStopAttacker = true;
            }
        }

        owner.updateThreatMap(board);

        return !canStopAttacker;
    }

    public boolean canStopKnight(Piece attacker) {
        return owner.threatMap[attacker.coords.x][attacker.coords.y];
    }

    public boolean canStopBishop(Piece attacker) {

        int xDir = (attacker.coords.x - coords.x)
                / Math.abs(attacker.coords.x - coords.x);
        int yDir = (attacker.coords.y - coords.y)
                / Math.abs(attacker.coords.y - coords.y);

        int spacesApart = Math.abs(attacker.coords.x - coords.x);
        for (int i = 1; i <= spacesApart; i++) {
            if (owner.threatMap[coords.x + (xDir * i)][coords.y + (yDir * i)]) {
                return true;
            }
        }

        return false;
    }

    public boolean canStopRook(Piece attacker) {
        if (attacker.coords.x < coords.x && attacker.coords.y == coords.y){ //Attacker North
            for (int x = coords.x - 1; x >= attacker.coords.x; x--){
                if (owner.threatMap[x][coords.y]){
                    return true;
                }
            }
        } else if (attacker.coords.x > coords.x && attacker.coords.y == coords.y){ //Attacker South
            for (int x = coords.x + 1; x <= attacker.coords.x; x++){
                if (owner.threatMap[x][coords.y]){
                    return true;
                }
            }
        } else if (attacker.coords.x == coords.x && attacker.coords.y < coords.y){ //Attacker West
            for (int y = coords.y - 1; y >= attacker.coords.y; y--){
                if (owner.threatMap[coords.x][y]){
                    return true;
                }
            }
        } else if (attacker.coords.x == coords.x && attacker.coords.y > coords.y){ //Attacker East
            for (int y = coords.y + 1; y <= attacker.coords.y; y++){
                if (owner.threatMap[coords.x][y]){
                    return true;
                }
            }

        }
        return false;
    }

    public Piece findAttacker(Board board) {

        Piece searchedPiece = null;

        //Look for knight
        int[] X = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] Y = { 1, 2, 2, 1, -1, -2, -2, -1 };
        for (int i = 0; i < 8; i++) {
            int x = coords.x + X[i];
            int y = coords.y + Y[i];
            if (x >= 0 && y >= 0 && x < Board.BOARD_SIZE && y < Board.BOARD_SIZE)
                searchedPiece = board.getPiece(x, y);
            if (searchedPiece == null) {
                continue;
            }
            if (searchedPiece.pieceType().equals("Knight") && owner.isWhite() != searchedPiece.isWhite()) {
                //Piece is a knight of the other color
                return searchedPiece;
            }
        }

        /*
         * look for pawn, since pawn can only move in one direction
         * (North for white, South for black),
         * there will be two cases that check for white king or
         * black king
         * */

        if(board.getPiece(coords.x, coords.y).isWhite()){
            //for white king
            for (int i = -1; i < 2; i+=2){
                if(coords.x - 1 >= 0 && coords.x - 1 < Board.BOARD_SIZE && coords.y+i >=0 && coords.y + i < Board.BOARD_SIZE){
                    searchedPiece = board.getPiece(coords.x -1, coords.y+i);

                    if (searchedPiece == null){
                        continue;
                    }
                    if (searchedPiece.pieceType().equals("Pawn") && owner.isWhite() != searchedPiece.isWhite()){
                        return searchedPiece;
                    }
                }
            }
        }

        if(!board.getPiece(coords.x, coords.y).isWhite()){
            //for black king
            for (int i = -1; i < 2; i+=2){
                if(coords.x + 1 < Board.BOARD_SIZE && coords.x + 1 >= 0  && coords.y+i >=0 && coords.y + i < Board.BOARD_SIZE){
                    searchedPiece = board.getPiece(coords.x +1, coords.y+i);
                    if (searchedPiece == null){
                        continue;
                    }
                    if (searchedPiece.pieceType().equals("Pawn") && owner.isWhite() != searchedPiece.isWhite()){
                        return searchedPiece;
                    }
                }
            }
        }


        /*
         * Look for Rooks, since there are 4 directions
         * check each direction
         * */
        for (int y = coords.y + 1; y < Board.BOARD_SIZE; y++) {
            //East
            if (board.getPiece(coords.x, y) != null){
                searchedPiece = board.getPiece(coords.x, y);
                if((searchedPiece.pieceType().equals("Rook") || searchedPiece.pieceType().equals("Queen")) && owner.isWhite() != searchedPiece.isWhite()){
                    return searchedPiece;
                }
            }
        }

        for (int y = coords.y - 1; y >= 0; y--) {
            //West
            if (board.getPiece(coords.x, y) != null){
                searchedPiece = board.getPiece(coords.x, y);
                if((searchedPiece.pieceType().equals("Rook") || searchedPiece.pieceType().equals("Queen")) && owner.isWhite() != searchedPiece.isWhite()){
                    return searchedPiece;
                }
            }
        }
        for (int x = coords.x + 1; x < Board.BOARD_SIZE; x++) {
            //South
            if (board.getPiece(x, coords.y) != null){
                searchedPiece = board.getPiece(x, coords.y);
                if((searchedPiece.pieceType().equals("Rook") || searchedPiece.pieceType().equals("Queen")) && owner.isWhite() != searchedPiece.isWhite()){
                    return searchedPiece;
                }
            }
        }

        for (int x = coords.x - 1; x >= 0; x--) {
            //North
            if (board.getPiece(x, coords.y) != null){
                searchedPiece = board.getPiece(x, coords.y);
                if((searchedPiece.pieceType().equals("Rook") || searchedPiece.pieceType().equals("Queen")) && owner.isWhite() != searchedPiece.isWhite()){
                    return searchedPiece;
                }
            }
        }

        /*
         * Looking for Bishops, 4 diagonal directions
         * */
        for ( int i = 1; coords.x - i >=0 && coords.y - i >=0 ; i++) {
            //North West
            if (board.getPiece(coords.x - i, coords.y - i) != null) {
                searchedPiece = board.getPiece(coords.x - i, coords.y - i);
                if((searchedPiece.pieceType().equals("Bishop") || searchedPiece.pieceType().equals("Queen")) && owner.isWhite() != searchedPiece.isWhite()){
                    return searchedPiece;
                }
            }
        }

        for ( int i = 1; coords.x - i >=0 && coords.y + i <Board.BOARD_SIZE ; i++) {
            //North East
            if (board.getPiece(coords.x - i, coords.y + i) != null) {
                searchedPiece = board.getPiece(coords.x - i, coords.y + i);
                if((searchedPiece.pieceType().equals("Bishop") || searchedPiece.pieceType().equals("Queen")) && owner.isWhite() != searchedPiece.isWhite()){
                    return searchedPiece;
                }
            }
        }

        for ( int i = 1; coords.x + i <Board.BOARD_SIZE && coords.y + i <Board.BOARD_SIZE ; i++) {
            //South East
            if (board.getPiece(coords.x + i, coords.y + i) != null) {
                searchedPiece = board.getPiece(coords.x + i, coords.y + i);
                if((searchedPiece.pieceType().equals("Bishop") || searchedPiece.pieceType().equals("Queen")) && owner.isWhite() != searchedPiece.isWhite()){
                    return searchedPiece;
                }
            }
        }

        for ( int i = 1; coords.x + i <Board.BOARD_SIZE && coords.y - i >=0 ; i++) {
            //South West
            if (board.getPiece(coords.x + i, coords.y - i) != null) {
                searchedPiece = board.getPiece(coords.x + i, coords.y - i);
                if((searchedPiece.pieceType().equals("Bishop") || searchedPiece.pieceType().equals("Queen")) && owner.isWhite() != searchedPiece.isWhite()){
                    return searchedPiece;
                }
            }
        }
        return null;
    }

    public boolean canMoveAway(Board board) {

        int[] X = {-1, -1, -1, 0, 0, 1, 1, 1 };
        int[] Y = {-1, 0, 1, -1, 1, -1, 0, 1 };
        for (int i = 0; i < 8; i++) {
            int x = coords.x + X[i];
            int y = coords.y + Y[i];
            if (x >= 0 && y >= 0 && x < Board.BOARD_SIZE && y < Board.BOARD_SIZE)
                if (!this.owner.opponent.threatMap[x][y] && board.getPiece(x, y) == null) {
                    //Square not threatened and not taken
                    return true;
                }
        }
        return false;
    }

    public boolean isChecked() {
        return this.owner.opponent.threatMap[coords.x][coords.y];
    }

    public void updatePieceThreat(Board board){
        int[] X = {-1, -1, -1, 0, 0, 1, 1, 1 };
        int[] Y = {-1, 0, 1, -1, 1, -1, 0, 1 };
        for (int i = 0; i < 8; i++) {
            int x = coords.x + X[i];
            int y = coords.y + Y[i];
            if (x >= 0 && y >= 0 && x < Board.BOARD_SIZE && y < Board.BOARD_SIZE)
                this.owner.threatMap[x][y] = true;
        }
    }


    public void movePiece(Board board, Point end) {

        if (isCastle(board, end)) {

            int colDir = (end.y - coords.y) / Math.abs(end.y - coords.y);
            int rookCol;

            if (colDir == -1) {
                rookCol = 0;
            } else {
                rookCol = 7;
            }

            Piece rook = board.getPiece(coords.x, rookCol);
            board.setPiece(null, coords.x, rookCol);
            board.setPiece(rook, coords.x, end.y - colDir);
        }

        super.movePiece(board, end);

        hasMoved = true;


    }

    public boolean isLegalPieceMove(Board board, Player player, Point start, Point end) {

        if (Math.abs(end.x - start.x) < 2 && Math.abs(end.y - start.y) < 2) {
            //Adjacent square
            return true;
        }

        if (hasMoved || isChecked()) {
            return false;
        }

        return isCastle(board, end);
    }

    public boolean isCastle(Board board, Point end) {

        if (Math.abs(end.x - coords.x) < 2 && Math.abs(end.y - coords.y) < 2) {
            return false;
        }

        int colDir = (end.y - coords.y) / Math.abs(end.y - coords.y);

        int i = 1;
        for (; coords.y + (colDir * i) >= 1 && coords.y + (colDir * i) < 7; i++ ) {
            if (board.getPiece(coords.x,coords.y + (colDir * i)) != null
                    || owner.opponent.threatMap[coords.x][coords.y + (colDir * i)]) {
                return false;
            }
        }

        Rook rook = (Rook) board.getPiece(coords.x, coords.y + (colDir * (i)));

        return !rook.hasMoved;
    }

    public String pieceType() { return "King"; }
    public void printPiece() {
        System.out.print("K ");
    }

}
