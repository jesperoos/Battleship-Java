package src;

import java.util.Scanner;

import src.controller.GameController;
import src.model.GameLogic;
import src.model.Player;

/* The main class of the Battleship game.
 * Initializes the game components, setup, 
 * and runs the game.
 */
public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        GameLogic gameLogic = new GameLogic(player1, player2);
        GameController gameController = new GameController(gameLogic, scanner);
        gameController.setupGame();
        gameController.runGame();
        scanner.close();
    }
}