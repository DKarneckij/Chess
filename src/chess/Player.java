package chess;

import chess.Pieces.King;
import chess.Pieces.Piece;

import java.util.ArrayList;

public class Player {
    public boolean white;
    public ArrayList<Piece> pieces;
    public boolean[][] threatMap;
    public King king;
    public Player opponent;
    public ArrayList<Piece> pinnedPieces;

    public Player(boolean white) {
        this.white = white;
        threatMap = new boolean[8][8];
        pieces = new ArrayList<Piece>();
        System.out.println();
        this.king = null;
        this.opponent = null;
        pinnedPieces = new ArrayList<Piece>();
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
        if (piece.pieceType().equals("King")) {
            this.king = (King) piece;
        }
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public String getColor() {
        if (white) {
            return "White";
        }
        return "Black";
    }

    public void updateThreatMap(Board board) {
        resetThreatMap();
        for (Piece piece : pieces) {
            piece.updatePieceThreat(board);
        }
    }

    public void resetThreatMap() {
        for (int i = 0; i < threatMap.length; i++) {
            for (int j = 0; j < threatMap.length; j++) {
                threatMap[i][j] = false;
            }
        }
    }

    public void removeKingFromThreatMap(Board board) {
        removePiece(king);
        updateThreatMap(board);
        addPiece(king);
    }

    public void removeThreatMapPinnedAndKing(Board board, Piece attacker) {

        opponent.removePiece(attacker);
        opponent.updateThreatMap(board);
        opponent.addPiece(attacker);


        ArrayList<Piece> tempPieces = new ArrayList<Piece>(pieces);

        for (Piece piece: tempPieces) {
            if (piece.pieceType().equals("King")) {
                continue;
            }
            pieces.remove(piece);
            updateThreatMap(board);
            if (king.isChecked()) {
                pinnedPieces.add(piece);
            }
            pieces.add(piece);
        }

        for (Piece piece : pinnedPieces) {
            pieces.remove(piece);
        }

        removePiece(king);
        updateThreatMap(board);

        pieces.addAll(pinnedPieces);
        pieces.add(king);

        pinnedPieces.clear();
        opponent.updateThreatMap(board);
    }

    public void printThreatMap() {
        System.out.format("%s's Threat Map:\n", this.getColor());
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if (!threatMap[i][j]) {
                    System.out.print("## ");
                } else {
                    System.out.print("XX ");
                }
            }
            System.out.print(8 - i + "\n");
        }
        System.out.println("a  b  c  d  e  f  g  h\n");
    }

    public void setOpponent(Player player) {
        this.opponent = player;
    }

    public boolean isWhite() {
        return white;
    }

}
