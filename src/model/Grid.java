package src.model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private char[][] gameGrid = new char[Constants.GRID_SIZE][Constants.GRID_SIZE];             // 10x10 grid.
    private String[][] shipIdentifiers = new String[Constants.GRID_SIZE][Constants.GRID_SIZE];  // Grid for ship identifiers.
    private List<Ship> ships = new ArrayList<>();                                               // array of ships.
    private String lastHitShipType;                                                             // type of the last ship hit.

    /* Constructor for object creation. */
    public Grid() {
        // Initialize the grid with row and column identifiers
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                this.gameGrid[row][col] = Constants.EMPTY_CELL;
            }
        }
    }

    /* Return grid. */
    public char[][] getGrid() {
        return this.gameGrid;
    }

    /* Return grid containing ship identifiers, this keeps track of ships hit and sunk. */
    public String[][] getShipIdentifiers() {
        return this.shipIdentifiers;
    }

    /* Method for placing a ship on the players game grid. */
    public boolean placeShip(char startRow, int startCol, char endRow, int endCol, Ship ship) throws PlacementException {
        // convert row A-J to a number between 0-9 to match grid index.
        // decrease value of cols by 1 to match grid index.
        int startRowId = startRow - 'A'; 
        int endRowId = endRow - 'A'; 
        startCol--;                     
        endCol--;
        boolean canPlaceShip = false;

        // check so that the inputs are in the correct format.
        if (startCol >= 0 && endCol < 10 && startRowId >= 0 && endRowId < 10) { 
            if (startRowId == endRowId) {                                               // horizontal placement.
                canPlaceShip = true;
                for (int col = startCol; col <= endCol; col++) {                        // loop through the input squares of ship placement.
                    if (this.gameGrid[startRowId][col] != Constants.EMPTY_CELL) {       // if square does not equal * , the square is occupied.
                        canPlaceShip = false;
                        break;                                                          // Ship can't be placed here
                    }
                }

                if (canPlaceShip) {                                                     // the squares are not occupied.
                    for (int col = startCol; col <= endCol; col++) { 
                        this.gameGrid[startRowId][col] = Constants.SHIP;                // place ship by changing * to S for ship.
                        this.shipIdentifiers[startRowId][col] = ship.getIdentifier();   // update the ship identifier grid as well.
                        this.ships.add(ship);                                           // add ship to grid ship list.
                    }
                } else {
                    throw new PlacementException ("Invalid placement, some cells are occupied or out of bounds."); // throws exception, but this is handled in the controller already so it shouldent reach this point.
                }
            }

            else if (startCol == endCol) {                                              // vertical placement.
                canPlaceShip = true;
                for (int row = startRowId; row <= endRowId; row++) { 
                    if (this.gameGrid[row][startCol] != Constants.EMPTY_CELL) {     
                        canPlaceShip = false;
                        break;                                                          // Ship can't be placed here
                    }
                }

                if (canPlaceShip) {
                    for (int row = startRowId; row <= endRowId; row++) {
                        this.gameGrid[row][startCol] = Constants.SHIP;                  // place ship by changing * to S for ship.
                        this.shipIdentifiers[row][startCol] = ship.getIdentifier();     // update the ship identifier grid as well.
                        this.ships.add(ship);                                           // add ship to grid ship list.
                    }
                } else {
                    throw new PlacementException ("Invalid placement, some cells are occupied or out of bounds."); // also throws exception, but should be handled earlier.
                }
            } else {
                throw new PlacementException ("Invalid placement of ship.");                                        // exception if neither rows, nor cols, match each other. Also handled earlier.
            }

        } else {
            throw new PlacementException ("Invalid input.");                                                        // exception for wrong input of either rows or cols, -> row was not A-J, or col was not 1-10. 
        }
        return canPlaceShip;                                                                                            // return if ship could be placed or not.
    }

    /* Method for checking the grid for ships on the shot coordinate. */
    public boolean checkIfHit(char row, int col) {
        int rowId = row - 'A';
        int colId = col - 1;
        if (rowId >= 0 && rowId < 10 && colId >= 0 && colId < 10) {             // make sure parameters match the grid index.
            if (this.gameGrid[rowId][colId] == Constants.SHIP) {                // ship squares marked with S -> change to H for hit.
                this.gameGrid[rowId][colId] = Constants.HIT;
                String shipIdentifier = this.shipIdentifiers[rowId][colId];     // get the ship identifier of the square.
                Ship ship;
                try {
                    ship = getShipByIdentifier(shipIdentifier);                 // get the ship that is placed on the square. throws ShipNotFoundException.
                    ship.setHits(ship.getHits() + 1);                           // increase the ships number of hits.
                    setLastHitShipType(ship.getTypeAsString());                 // set which ship was hit, for later prompts in the view.
                    if (ship.getHits() == ship.getSize()) { 
                        ship.setSunk(true);                                // if ship hits matches ship size -> ship is sunk.
                    }
                    return true;                                                // a ship was hit, return true.

                // I am not sure how to properly handle the event of error.
                } catch (ShipNotFoundException e) {  
                    System.out.println("Wops, something went wrong. Please contact support if the issue persists.");
                }
            }
        }
        return false; // shot missed -> return false.
    }
    
    /* Get the ship placed on grid of specific identifier. */
    public Ship getShipByIdentifier(String identifier) throws ShipNotFoundException {
        for (Ship ship : this.ships) {
            if (ship.getIdentifier().equals(identifier)) {
                return ship;
            }
        }
        throw new ShipNotFoundException("Ship with identifier " + identifier + " not found.");
    }
    
    /* Return the type of ship that was hit for view prompt. */
    public String getLastHitShipType() {
        return this.lastHitShipType;
    }

    /* Set the ship type of last hit ship. */
    public void setLastHitShipType(String shipType) {
        this.lastHitShipType = shipType;
    }
    
    /* Exception class for ship not found by identifier. */
    public class ShipNotFoundException extends Exception {
        public ShipNotFoundException(String msg) { 
            super(msg); // not sure how to handle these exceptions, but maybe logging?
        }
    }

    /* Exception class for placement error. */
    public class PlacementException extends Exception {
        public PlacementException(String msg) {
            super(msg); // not sure how to handle these exceptions, but maybe logging?
        }
    }
}
