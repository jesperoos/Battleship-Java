package test.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import src.model.*;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player("testPlayer");
    }

    @Test
    public void testGetName() {
        assertEquals("testPlayer", player.getName());
    }

    @Test
    public void testAddShip() {
        Ship ship = new Ship(Ship.ShipType.DESTROYER);
        player.addShip(ship);

        assertTrue(player.getPlayerShips().contains(ship));
    }

    @Test
    public void testIncrementFiredShots() {
        player.incrementFiredShots();
        player.incrementFiredShots();

        assertEquals(2, player.getFiredShots());
    }

    @Test
    public void testIncrementHits() {
        player.incrementHits();
        player.incrementHits();

        assertEquals(2, player.getHits());
    }

    @Test
    public void testPlayerTargetGrid() {
        Grid targetGrid = player.getPlayerTargetGrid();
        assertNotNull(targetGrid);
        char[][] grid = targetGrid.getGrid();

        // Ensure the grid is initialized to all '*'
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                assertEquals('*', grid[row][col]);
            }
        }
    }
}
