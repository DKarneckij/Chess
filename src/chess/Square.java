package chess;

import chess.Pieces.*;

public class Square {
    public Piece piece;
    public boolean white;

    public Square(Piece piece, boolean white) {
        this.piece = piece;
        this.white = white;
    }

    public void printSquare() {
        //No piece on square
        if(piece == null) {
            if(white) {
                System.out.print("   ");}
            else {
                System.out.print("## "); }
        }
        //Prints color then piece
        else {
            if(piece.isWhite()) {
                System.out.print("w"); }
            else {
                System.out.print("b"); }
            piece.printPiece();
        }
    }

    //Gets piece from square
    public Piece getPiece() {
        return piece;
    }

    //Set method for initializing the board
    public void setPiece(Piece piece) {
        this.piece = piece;
    }


}
