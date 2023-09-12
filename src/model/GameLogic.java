package src.model;

import src.model.Grid.PlacementException;

/* The GameLogic class is responsible for handling game events such as:
 * give a player ships before start,
 * communicate with player grids to place ships or mark hits,
 * handling the event of player shot,
 * player turn,
 * check for game over,
 */
public class GameLogic {
    Player player1;             // player objects.
    Player player2;
    private Player nextPlayer;  // keeping track of who's turn it is.
    private Player prevPlayer;
    boolean gameOver = false;

    /* Constructor for object creation. */
    public GameLogic(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.nextPlayer = player1;
        this.prevPlayer = player2;
    }

    /* Give player all ships for placement. 
     * These are the default ships for the game with unique identifiers, and different sizes.
    */
    public void giveShips(Player player) {
        player.addShip(new Ship(Ship.ShipType.DESTROYER));
        player.addShip(new Ship(Ship.ShipType.SUBMARINE));
        player.addShip(new Ship(Ship.ShipType.CRUISER));
        player.addShip(new Ship(Ship.ShipType.BATTLESHIP));
        player.addShip(new Ship(Ship.ShipType.CARRIER));
    }

    /* Checks the target grid of the player for hit. */
    public boolean checkIfHit(Grid targetGrid, char row, int col) {
        return targetGrid.checkIfHit(row, col);
    }

    /* Places a ship on the player grid. */
    public boolean placeShip(char startRow, int startCol, char endRow, int endCol, Ship ship, Grid playerGrid) throws PlacementException {
        return playerGrid.placeShip(startRow, startCol, endRow, endCol, ship);
    }

    /* Player takes shot on targets grid. */
    public boolean takeShot(Player shooter, Player target, char row, int col) {
        char[][] shooterTargetGrid = shooter.getPlayerTargetGrid().getGrid();       // get the shooters target grid.
        Grid targetGameGrid = target.getPlayerGrid();                               // get the player grid object of the target.
        boolean targetHit = false;
        int rowId = row - 'A';                                                      // convert input to match index.
        int colId = col - 1;

        if (rowId >= 0 && rowId < 10 && colId >= 0 && colId < 10) {                 // ensure that coordinates of shot are within index.
            if (checkIfHit(targetGameGrid, row, col)) {                             // if shot hits, update shooters target grid with H.
                shooterTargetGrid[rowId][colId] = Constants.HIT;                    // the targets game grid is updated inside the checkIfHit method.
                targetHit = true;
                shooter.incrementHits();                                            // increment shooters number of hits, (this value is never used but I thought maybe it would be good for some future development)
            } else {
                shooterTargetGrid[rowId][colId] = Constants.MISS;                   // if miss, update shooters target grid with M.
            }
        }
        this.nextPlayer = target;                                                   // set next/prev player, increment shooters number of shots fired.
        this.prevPlayer = shooter;                           
        shooter.incrementFiredShots();                  
        return targetHit;
    }

    /* Check if all of a players ships have been destroyed. -> Game Over. */
    public boolean isGameOver(Player player) {
        if (player.getPlayerShips().isEmpty()) { // if the players list of ships is empty, they have all been destroyed.
            return true;
        }
        return false;
    }

    /* Return player who's turn it is. */
    public Player getNextPlayer() {
        return this.nextPlayer;
    }

    /* Return previous player. */
    public Player getPrevPlayer() {
        return this.prevPlayer;
    }
}