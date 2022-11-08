package chess;

import chess.Pieces.Pawn;
import chess.Pieces.Piece;

import java.awt.*;

public class Game {

    public Board board;
    public Player[] players;
    public Player curPlayer;
    public Player curOpponent;
    public boolean ongoing;

    public Game() {

        this.board = new Board();
        this.players = new Player[]{new Player(true), new Player(false)};
        this.board.fillBoardTemp(players);

        this.curPlayer = players[1];
        this.curOpponent = players[0];
        players[0].setOpponent(players[1]);
        players[1].setOpponent(players[0]);

        players[0].updateThreatMap(board);
        players[1].updateThreatMap(board);

        this.ongoing = true;
    }

    public boolean makeMove(String move) {

        //Turn start and end into Points
        Point start = new Point((int) '8' - (int) move.charAt(1), (int) move.charAt(0) - (int) 'a');
        Point end = new Point((int) '8' - (int) move.charAt(4), (int) move.charAt(3) - (int) 'a');

        Piece pieceToMove = board.getPiece(start.x, start.y);

        if (pieceToMove == null) {
            //Starting piece is null
            return false;
        }

        if (!pieceToMove.pieceType().equals("Pawn")) {
            //Updates that current turn isn't a pawn moving up 2 squares
            board.updatePawnMovedTwo(null);
        }

        if (!pieceToMove.isLegalMove(board, curPlayer, start, end)) {
            //Move is not legal
            return false;
        }

        if (moveWillCauseCheck(start, end)) {
            //Moving piece will cause check
            return false;
        }

        if (move.length() == 5) {
            //Complete normal move if it won't be checked, if it will, return false
            board.getPiece(start.x, start.y).movePiece(board, end);
        } else {
            //Pawn promotion move
            Pawn pawn = (Pawn) board.getPiece(start.x, start.y);
            pawn.promotePawn(board, end, move.charAt(6));
        }

        curPlayer.updateThreatMap(board);

        if (curOpponent.king.isChecked()) {
            if (curOpponent.king.isCheckMated(board)) {
                ongoing = false;
            } else {
                System.out.println("\nCheck\n");
            }
        }

        swapPlayers();

        return true;
    }

    public boolean moveWillCauseCheck(Point start, Point end) {
        Piece startPiece = board.getPiece(start.x, start.y);
        Piece endPiece = board.getPiece(end.x, end.y);

        boolean willCauseCheck = false;

        if (endPiece != null) {
            endPiece.owner.removePiece(endPiece);
        }

        startPiece.updateCoords(end.x, end.y);
        board.setPiece(null, start.x, start.y);
        board.setPiece(startPiece, end.x, end.y);
        curOpponent.updateThreatMap(board);

        if (curPlayer.king.isChecked()) {
            willCauseCheck = true;
        }

        startPiece.updateCoords(start.x, start.y);
        board.setPiece(startPiece, start.x, start.y);
        board.setPiece(endPiece, end.x, end.y);
        curOpponent.updateThreatMap(board);

        if (endPiece != null) {
            endPiece.owner.addPiece(endPiece);
        }

        return willCauseCheck;
    }

    public void swapPlayers() {
        if (curPlayer.white) {
            curOpponent = curPlayer;
            curPlayer = players[1];
        }
        else {
            curOpponent = curPlayer;
            curPlayer = players[0];
        }
    }





}