package chess;

import chess.Pieces.*;

import java.awt.*;

public class Board {

    public static int BOARD_SIZE = 8;
    public final Square[][] board;
    public Piece lastTurnPawnMovedTwo; //Keeps track of a pawn that was moved 2 spaces last turn

    // Create a 2D array of Square type named board
    public Board() {
        Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = new Square(null, true);
                } else {
                    board[i][j] = new Square(null, false);
                }
            }
        }
        this.board = board;
        this.lastTurnPawnMovedTwo = null;
    }

    public void fillBoardTemp(Player[] players) {
        Player white = players[0];
        Player black = players[1];

       setPiece(new King(black, 0, 4), 0, 4);
       setPiece(new King(white, 7, 1), 7, 1);
       setPiece( new Pawn(white, 6, 0),6, 0);
       setPiece( new Pawn(white, 6, 2),6, 2);
       setPiece( new Pawn(black, 6, 1),6, 1);
        setPiece(new Rook(white, 7, 7), 7, 7);
        setPiece(new Rook(black, 6, 3), 6, 3);
    }

    //Fills a normal 8x8 chess board
    public void fillBoard(Player[] players) {

        Player white = players[0];
        Player black = players[1];

        //Put Rooks
        setPiece(new Rook(black, 0, 0), 0, 0);
        setPiece(new Rook(black, 0, 7), 0, 7);
        setPiece(new Rook(white, 7, 0), 7, 0);
        setPiece(new Rook(white, 7, 7), 7, 7);

        //Put Knights
        setPiece(new Knight(black, 0, 1), 0, 1);
        setPiece(new Knight(black, 0, 6), 0, 6);
        setPiece(new Knight(white, 7, 1), 7, 1);
        setPiece(new Knight(white, 7, 6), 7, 6);

        //Put Bishops
        setPiece(new Bishop(black, 0, 2), 0, 2);
        setPiece(new Bishop(black, 0, 5), 0, 5);
        setPiece(new Bishop(white, 7, 2),7, 2);
        setPiece(new Bishop(white, 7, 5), 7, 5);

        //Put Queens
        setPiece(new Queen(black, 0, 3), 0, 3);
        setPiece(new Queen(white, 7, 3), 7, 3);

        //Put Kings
        setPiece(new King(black, 0, 4), 0, 4);
        setPiece(new King(white, 7, 4), 7, 4);

        //Put Pawns
        for (int i = 0; i < BOARD_SIZE; i++) {
            setPiece(new Pawn(black, 1, i),1, i);
            setPiece(new Pawn(white, 6, i), 6, i);
        }
    }

    //Prints the board
    public void printBoard() {
        System.out.println("\n");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].printSquare();
            }
            System.out.print(8 - i + "\n");
        }
        System.out.println(" a  b  c  d  e  f  g  h\n");
    }

    //Returns the piece in the square at x,y
    public Piece getPiece(int row, int col) {
        return board[row][col].getPiece();
    }

    //Sets the square at x,y to piece
    public void setPiece(Piece piece, int row, int col) {
        board[row][col].setPiece(piece);
    }

    //Updates pawn move to be used to determine En Pessant
    public void updatePawnMovedTwo(Piece piece) {
        this.lastTurnPawnMovedTwo = piece;
    }

    public Piece lastTurnPawnMovedTwo() {
        return this.lastTurnPawnMovedTwo;
    }

    //Mingchao Huo
}
