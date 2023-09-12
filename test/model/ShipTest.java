package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import src.model.Ship;
import src.model.Ship.ShipType;

public class ShipTest {
    private Ship destroyer;
    private Ship submarine;

    @Before
    public void setUp() {
        destroyer = new Ship(ShipType.DESTROYER);
        submarine = new Ship(ShipType.SUBMARINE);
    }

    @Test
    public void testGetTypeAsString() {
        assertEquals("Destroyer", destroyer.getTypeAsString());
        assertEquals("Submarine", submarine.getTypeAsString());
    }

    @Test
    public void testGetSize() {
        assertEquals(2, destroyer.getSize());
        assertEquals(3, submarine.getSize());
    }

    @Test
    public void testSetHitsAndGetHits() {
        destroyer.setHits(1);
        submarine.setHits(2);

        assertEquals(1, destroyer.getHits());
        assertEquals(2, submarine.getHits());
    }

    @Test
    public void testSetSunkAndIsSunk() {
        assertFalse(destroyer.isSunk());
        assertFalse(submarine.isSunk());

        destroyer.setSunk(true);
        submarine.setSunk(true);

        assertTrue(destroyer.isSunk());
        assertTrue(submarine.isSunk());
    }
}
