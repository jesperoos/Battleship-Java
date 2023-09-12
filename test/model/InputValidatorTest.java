package test.model;

import static org.junit.Assert.*;
import org.junit.Test;
import src.model.InputValidator;

public class InputValidatorTest {

    @Test
    public void testValidShotInput() {
        InputValidator validator = new InputValidator("A1");
        assertTrue(validator.isValid());
        assertEquals('A', validator.getRow());
        assertEquals(1, validator.getCol());
    }

    @Test
    public void testInvalidShotInput() {
        InputValidator validator = new InputValidator("A0");
        assertFalse(validator.isValid());
    }

    @Test
    public void testValidShipPlacement() {
        InputValidator validator = new InputValidator("A1-A2", 2);
        assertTrue(validator.isValid());
        assertEquals('A', validator.getStartRow());
        assertEquals('A', validator.getEndRow());
        assertEquals(1, validator.getStartCol());
        assertEquals(2, validator.getEndCol());

        validator = new InputValidator("A1-C1", 3);
        assertTrue(validator.isValid());
        assertEquals('A', validator.getStartRow());
        assertEquals('C', validator.getEndRow());
        assertEquals(1, validator.getStartCol());
        assertEquals(1, validator.getEndCol());
    }

    @Test
    public void testInvalidShipPlacement() {
        // Invalid size for ship placement
        InputValidator validator1 = new InputValidator("A1-A2", 3);
        assertFalse(validator1.isValid());

        // Incorrect order of coordinates
        InputValidator validator2 = new InputValidator("A2-A1", 2);
        assertFalse(validator2.isValid());

        // Diagonal placement is not allowed
        InputValidator validator3 = new InputValidator("A1-B2", 2);
        assertFalse(validator3.isValid());
    }
}