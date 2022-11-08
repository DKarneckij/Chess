package chess.Pieces;

import chess.Board;
import chess.Player;

import java.awt.*;

public class Knight extends Piece {

    public Knight(Player player, int row, int col) {
        super(player, row, col);
    }

    public void updatePieceThreat(Board board) {
        int[] X = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] Y = { 1, 2, 2, 1, -1, -2, -2, -1 };
        for (int i = 0; i < 8; i++) {
            int x = coords.x + X[i];
            int y = coords.y + Y[i];
            if (x >= 0 && y >= 0 && x < Board.BOARD_SIZE && y < Board.BOARD_SIZE)
                this.owner.threatMap[x][y] = true;
        }

    }

    public boolean isLegalPieceMove(Board board, Player player, Point start, Point end) {
        return Math.abs((start.x - end.x) * (start.y - end.y)) == 2;
    }

    public void printPiece() {
        System.out.print("N ");
    }
    public String pieceType() { return "Knight"; }
}
