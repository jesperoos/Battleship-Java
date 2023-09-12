package src.view;

import src.model.Grid;
import src.model.Player;
import src.model.Ship;

public class BattleshipView {
    
    /* Displays the instructions for how to setup the ships on the battlefield. */
    public void displayInstructionsShipPLacement(String name) {
        System.out.println(name + ", your turn to setup ships on the battlefield.");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
        System.out.println("There are 5 different ships, Destroyer, Submarine, Cruiser, Battleship, and Carrier.");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
        System.out.println("These have different sizes, shown below.");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
        System.out.println("Destroyer, size 2");
        System.out.println("Submarine, size 3");
        System.out.println("Cruiser, size 3");
        System.out.println("Battleship, size 4");
        System.out.println("Carrier, size 5");
        System.out.println();
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
        System.out.println("A ship can only be placed vertically, or horizontally on the grid.");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
        System.out.println("Example: Destroyer at A1-A2, Submarine at B3-D3, and so on.");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
        System.out.println("Choose wisely!");
        System.out.println();
        try {
            Thread.sleep(2000); // Sleep for 2 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    /* Prints a grid to the terminal. */
    public void displayGrid(Grid grid) {
        // Print column headers (A, B, C, ..., J)
        System.out.print("  ");

        for (int i = 1; i < 11; i++) {
            System.out.print(i + " ");
        }
        System.out.println(); 

        // Print the grid with row identifiers and cell values
        for (int row = 0; row < 10; row++) {
            // Print the row identifier (A, B, C, ..., J)
            System.out.print((char) ('A' + row) + " ");
            // Print the cell values for the current row
            for (int col = 0; col < 10; col++) {
                System.out.print(grid.getGrid()[row][col] + " ");
            }
            System.out.println();
        }
    }

    /* Displays an error message. */
    public void displayError(String msg) {
        System.out.println();
        System.out.println(msg);
    }

    /* Displays type of ship and its size during setup. */
    public void displayShipPlacementInfo(Ship ship) {
        System.out.println("Place a " + ship.getTypeAsString() + ", size: " + ship.getSize());
    }

    /* Prompt when a player has finished setting up ships. */
    public void displayPlayerShipsPlaced(String name) {
        System.out.println(name + " has placed all ships.");
        System.out.println();
    }

    /* Displays the players name, fired shots, and target grid before the player takes his shot. */
    public void displayGameState(String name, int firedShots, Grid playerTargetGrid) {
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
        System.out.println(name + ", Choose your next shot!");
        System.out.println("Current number of shots fired: " + firedShots);
        System.out.println("your target grid:");
        displayGrid(playerTargetGrid);
    }

    /* Displays guide for shot coordinates input. */
    public void promptForCoordinates() {
        System.out.println("Enter coordinates (e.g., A1):");
    }

    /* Prompt for when a player takes a shot. */
    public void takeShotPrompt(String name, char currentRow, int currentCol) {
        System.out.println(name + " shoots at: " + currentRow+currentCol + "...");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    /* Displays the type of ship, and targeted players name, on hit. */
    public void displayHit(String shipType, String targetPlayerName) {
        System.out.println("You hit " + targetPlayerName + "s " + shipType + ".");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    /* Prompt for shot missed. */
    public void displayMiss() {
        System.out.println("It's a miss.");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    /* Prompt for player sunk ship. */
    public void displayShipSunk(String shooter, String target, String type) {
        System.out.println(shooter + " sunk " + target + "s " + type + "!");
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    /* Displays the "win screen" when game is ended. */
    public void displayGameResults(Player winner) {
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
        System.out.println(winner.getName() + " wins the game, good fight!");
        System.out.println("Shots fired:\t" + winner.getFiredShots());
        System.out.println("Ships afloat:\t" + winner.getPlayerShips().size());
    }

    /* Error prompt for when attempting to fire at coordinates that have already been fired at. */
    public void displayHasBeenTargeted(char row, int col) {
        System.out.println("You have already fired on " + row+col+".");
    }

    /* Prompt for confirmation of ship placement during setup. */
    public void displayShipPlaced(char startRow, int startCol, char endRow, int endCol, String shipType) {
        System.out.println("You placed your " + shipType + " at: " + startRow+startCol+ "-" + endRow+endCol);
        try {
            Thread.sleep(1000); // Sleep for 1 sec.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }
}
