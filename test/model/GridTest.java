package test.model;
import static org.junit.Assert.*;
import org.junit.Test;

import src.model.Grid;
import src.model.Ship;
import src.model.Ship.ShipType;

public class GridTest {
    
    @Test
    public void testGridInitialization() {
        Grid grid = new Grid();
        char[][] gameGrid = grid.getGrid();

        // Assert that the gameGrid is not null and has the expected dimensions
        assertNotNull(gameGrid);
        assertEquals(10, gameGrid.length);
        assertEquals(10, gameGrid[0].length);
    }

    @Test
    public void testPlaceShip() {
        Grid grid = new Grid();
        Ship ship = new Ship(ShipType.DESTROYER);
        
        // Test placing the ship horizontally
        try {
            assertTrue(grid.placeShip('A', 1, 'A', 2, ship));
        } catch (Grid.PlacementException e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }

        // Test placing the ship vertically
        Ship submarine = new Ship(ShipType.SUBMARINE);
        try {
            assertTrue(grid.placeShip('B', 3, 'D', 3, submarine));
        } catch (Grid.PlacementException e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }

        // Test placing the ship with invalid coordinates (should fail)
        Ship cruiser = new Ship(ShipType.CRUISER);
        try {
            assertTrue(grid.placeShip('C', 4, 'C', 6, cruiser));
        } catch (Grid.PlacementException e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void testCheckIfHit() throws Grid.PlacementException {
        Grid grid = new Grid();
        Ship destroyer = new Ship(ShipType.DESTROYER);
        Ship submarine = new Ship(ShipType.SUBMARINE);
        
        // Place ships on the grid
        assertTrue(grid.placeShip('A', 1, 'A', 2, destroyer));
        assertTrue(grid.placeShip('B', 3, 'D', 3, submarine));

        // Test hitting a ship and checking if it's marked as hit
        assertTrue(grid.checkIfHit('A', 1)); // Hit the Destroyer
        assertEquals(grid.getLastHitShipType(), "Destroyer");
        assertTrue(grid.checkIfHit('B', 3)); // Hit the Submarine
        
        // Test hitting the same location again (should return false)
        assertFalse(grid.checkIfHit('A', 1));
        
        // Test hitting an empty location (should return false)
        assertFalse(grid.checkIfHit('C', 5));
        
        // Test hitting a location with a sunk ship (should return false)
        grid.checkIfHit('A', 2); // Hit the Destroyer again
        assertTrue(destroyer.isSunk()); // Sink the Destroyer
        assertFalse(grid.checkIfHit('A', 2)); // Try hitting the sunk Destroyer
    }
}
