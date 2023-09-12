package src.controller;

import java.util.Iterator;
import java.util.Scanner;
import src.model.GameLogic;
import src.model.InputValidator;
import src.model.Player;
import src.model.Ship;
import src.model.Grid.PlacementException;
import src.view.BattleshipView;

/* Controller class to handle interaction between view and model. */
public class GameController {
    private GameLogic gameLogic;            // GameLogic object.
    private Scanner scanner;                // Scanner object.
    private boolean gameOver = false;       // Game Over bool to stop game on end.
    private BattleshipView view;            // View object.
    
    /* Constructor for object creation. */
    public GameController(GameLogic gameLogic, Scanner scanner) {
        this.gameLogic = gameLogic;
        this.scanner = scanner;
        this.view = new BattleshipView();
    }
    
    /* Method for setting up game,
     * it gives the players their default ships, and lets them place them on their game grids. 
     * One player at a time.
     */
    public void setupGame() {
        gameLogic.giveShips(gameLogic.getNextPlayer());
        gameLogic.giveShips(gameLogic.getPrevPlayer());
        placeShipsPlayer(gameLogic.getNextPlayer());
        placeShipsPlayer(gameLogic.getPrevPlayer());
        // hardcodedShipPlacement(gameLogic.getNextPlayer());
        // hardcodedShipPlacement(gameLogic.getPrevPlayer()); 
    }
    
    /* A hardcoded ship placement method I used for easier testing. */
    public void hardcodedShipPlacement(Player player) {
        // Hardcoded ship placements
        try {
            // Destroyer
            player.getPlayerGrid().placeShip('A', 1, 'A', 2, player.getPlayerShips().get(0));
            // Submarine
            player.getPlayerGrid().placeShip('B', 3, 'D', 3, player.getPlayerShips().get(1));
            // Cruiser
            player.getPlayerGrid().placeShip('E', 5, 'G', 5, player.getPlayerShips().get(2));
            // Battleship
            player.getPlayerGrid().placeShip('H', 7, 'H', 10, player.getPlayerShips().get(3));
            // Carrier
            player.getPlayerGrid().placeShip('J', 1, 'J', 5, player.getPlayerShips().get(4));
        } catch (PlacementException e) {
            e.printStackTrace();
        }
    }

    /* Method for placing the players ships on game grid. */
    public void placeShipsPlayer(Player player) {
        String input;

        view.displayInstructionsShipPLacement(player.getName());                                    // display instructions for how to place a ship on the grid. (different ship sizes, input format requirements)
        
        for (Ship ship : player.getPlayerShips()) {                                                 // iterate players list of ships.
            view.displayGrid(player.getPlayerGrid());                                               // display the players game grid before each new placement.
            view.displayShipPlacementInfo(ship);                                                    // display placement info about the current ship.
            boolean isValidPlacement = false;
            
            while(!isValidPlacement) {
                input = collectPlayerInput();
                InputValidator validator = new InputValidator(input, ship.getSize());
                if (!validator.isValid()) {
                    view.displayError("Invalid input format. Please use the format 'A1-A3' or 'A1-C1'.");                   // prompt user for entering valid format.
                    continue;
                }
                char startRow = validator.getStartRow();                                                                        // variables for handling user input.
                char endRow = validator.getEndRow();
                int startCol = validator.getStartCol();
                int endCol = validator.getEndCol();

                try {
                    isValidPlacement = gameLogic.placeShip(startRow, startCol, endRow, endCol, ship, player.getPlayerGrid());   // true if ship was placed.
                    if (isValidPlacement) {
                        view.displayShipPlaced(startRow, startCol, endRow, endCol, ship.getTypeAsString());                     // display prompt for placed ship.
                    }
                } catch (Exception e) {
                    view.displayError("Invalid placement, some cells are occupied or out of bounds.");                      // display error message if invalid input or placement.
                }
            }
        }
        view.displayPlayerShipsPlaced(player.getName());                                                                            // prompt for player finished placing ships. 
    }

    /* Method for running the game.
     * Called from Game.java after setupGame().
     */
    public void runGame() {
        Player winner = null;                                                                                                              // winning player is null in the beginning.
        while (!gameOver) {                                                                                                                // while loop to keep game going until it's over.
            Player currentPlayer = gameLogic.getNextPlayer();                                                                              // get next players turn.
            Player prevPlayer = gameLogic.getPrevPlayer();                                                                                 // get target player.
            System.out.println();
            view.displayGameState(currentPlayer.getName(), currentPlayer.getFiredShots(), currentPlayer.getPlayerTargetGrid());            // display game state before player takes shot.
            
            boolean isValidCoordinate = false;
            while(!isValidCoordinate) {
                view.promptForCoordinates();                                                                                   // display prompt for requested input format.
                inputValidationResult validationResult = getPlayerShotCoordinate(currentPlayer);
                if (validationResult.equals(inputValidationResult.ALREADY_FIRED)) {
                    view.displayHasBeenTargeted(currentPlayer.getCurrentRow(),currentPlayer.getCurrentCol());                  // display prompt for coordinate already fired upon.

                } else if (validationResult.equals(inputValidationResult.WRONG_FORMAT)) {
                    view.displayError("Invalid input format. Please use the format 'A1'.");                                // display error message.

                } else if (validationResult.equals(inputValidationResult.VALID)) {                                             // input is valid.
                    isValidCoordinate = true;

                } else {
                    view.displayError("Invalid input format. Please try again.");                                          // extra invalid input message.
                }
            }

            view.takeShotPrompt(currentPlayer.getName(), currentPlayer.getCurrentRow(), currentPlayer.getCurrentCol());                    // display shot fired prompt.
            boolean hit = gameLogic.takeShot(currentPlayer, prevPlayer, currentPlayer.getCurrentRow(), currentPlayer.getCurrentCol());     // method call for taking shot. Returns true if hit.
           
            if (hit) {
                String shipType = prevPlayer.getPlayerGrid().getLastHitShipType();                                          // if hit, get the type of ship that was hit.
                view.displayHit(shipType, prevPlayer.getName());                                                            // prompt for ship hit by player.
                Iterator<Ship> iterator = prevPlayer.getPlayerShips().iterator();                                           
                while (iterator.hasNext()) {
                    Ship ship = iterator.next();
                    if (ship.isSunk()) {                                                                                    // iterate targets list of ship to check if a ship was sunk.
                        view.displayShipSunk(currentPlayer.getName(), prevPlayer.getName(), ship.getTypeAsString());                // if sunk, display prompt and remove ship from targets list of ships.
                        iterator.remove();
                        gameOver = gameLogic.isGameOver(prevPlayer);                                                        // check if all targets ship have been destroyed. -> Game Over.
                        if (gameOver) {
                            winner = currentPlayer;                                                                         // set winner.
                        }
                    }
                }      
            } else {
                view.displayMiss();                                                                                          // prompt for shot missed.
            }
        }
        view.displayGameResults(winner);                                                                                     // prompt for declaring winner.
    }

    /* Collect input for shot. */
    private inputValidationResult getPlayerShotCoordinate(Player currentPlayer) {
        String input = collectPlayerInput();
        char row;
        int col;

        InputValidator validator = new InputValidator(input);  // check so that the input follows format A1, or A10 for instance.

        if (validator.isValid()) {
            row = validator.getRow();
            col = validator.getCol();
            currentPlayer.setCurrentShot(row, col);
            if (!currentPlayer.hasBeenTargeted(row, col)) {                             // check if the players input coordinate has already been fired at.
                currentPlayer.addTargetCoordinate(row, col);                            // if not, add it.
                return inputValidationResult.VALID;                                     // input is valid.

            } else {
                return inputValidationResult.ALREADY_FIRED;                             // coordinate has already been fired upon.
            }
        } else {
            return inputValidationResult.WRONG_FORMAT;
        }
    }

    /* Enum for inputValidationResults */
    public enum inputValidationResult {
        VALID,
        WRONG_FORMAT,
        ALREADY_FIRED
    }

    /* Get input from user. */
    public String collectPlayerInput() {
        return scanner.next();
    }
}
