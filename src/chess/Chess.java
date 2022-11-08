package chess;

import java.util.Scanner;

public class Chess {
    public boolean wDraw;
    private boolean bDraw  = false;
    private static boolean resign = false;

    public static void main(String[] args) {

        Game game = new Game();

        Scanner console = new Scanner(System.in);

        game.board.printBoard();

        while(game.ongoing) {

            System.out.format("%s's turn: ", game.curPlayer.getColor());
            String move = console.nextLine();

            if (move.equalsIgnoreCase("resign")){
                resign(game);
            }

            if(move.equalsIgnoreCase("draw")){
                if (!draw(game, console)) {
                    continue;
                }
            }

            if(!game.makeMove(move)) {
                System.out.println("Illegal move, try again");
                continue;
            }

            game.board.printBoard();

        }
        System.out.println("Checkmate!\n");
        System.out.format("%s Wins!\n", game.curOpponent.getColor());
    }



    public static boolean draw(Game game, Scanner console) {
        System.out.format("%s is asking for draw. Input yes or no: ", game.curPlayer.getColor());
        String answer = console.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Draw!");
            System.exit(0);
        } else {
            System.out.format("%s refuses to draw, the game continues\n\n", game.curOpponent.getColor());
        }
        return false;
    }



    public static void resign(Game game) {
        if(game.curPlayer.isWhite()){
            System.out.println("\nBlack Wins!\n");
        } else {
            System.out.println("\nWhite Wins!\n");
        }
        System.exit(0);
    }
}
