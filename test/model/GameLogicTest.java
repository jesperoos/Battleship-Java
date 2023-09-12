package test.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import src.model.GameLogic;
import src.model.Grid;
import src.model.Player;
import src.model.Ship;
import src.model.Ship.ShipType;

public class GameLogicTest {
    private GameLogic gameLogic;
    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        gameLogic = new GameLogic(player1, player2);
    }

    @Test
    public void testTakeShot() {
        Grid player2Grid = player2.getPlayerGrid();
        Ship destroyer = new Ship(ShipType.DESTROYER);
        
        // Place the destroyer on player2's grid
        try {
            player2Grid.placeShip('A', 1, 'A', 2, destroyer);
            player2.addShip(destroyer); // add the ship to player 2s list of ships
        } catch (Exception e) {
            fail("Placement should be successful.");
        }

        // Player 1 takes a shot and hits the destroyer
        assertTrue(gameLogic.takeShot(player1, player2, 'A', 1));

        // Player 2's grid should now have 'H' in the hit position
        char[][] player2GridState = player2Grid.getGrid();
        assertEquals('H', player2GridState[0][0]);

        // Player 1's fired shots count should increase
        assertEquals(1, player1.getFiredShots());

        // Player 2's destroyer should have 1 hit
        assertEquals(1, destroyer.getHits());

        // Player 2's grid should not have any ships sunk
        assertFalse(gameLogic.isGameOver(player2));

        // Player 1 takes a second shot and hits the destroyer
        assertTrue(gameLogic.takeShot(player1, player2, 'A', 2));

        // Player 2's destroyer should now be sunk
        assertTrue(destroyer.isSunk());
    }

    @Test
    public void testGiveShips() {
        Player testPlayer = new Player("Player 3");
        gameLogic.giveShips(testPlayer);

        assertEquals(5, testPlayer.getPlayerShips().size());
    }
}
