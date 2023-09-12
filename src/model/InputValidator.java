package src.model;

/* Class for input validation.
    * Validates and provides coordinates for both a shot and ship placement.
    */
public class InputValidator {
    private char row;
    private int col;
    private char startRow;
    private char endRow;
    private int startCol;
    private int endCol;
    private boolean isValid;

    /* Constructor for shot validation. */
    public InputValidator(String input) {
        // validate the input string against the specified regular expression pattern
        // and set the 'isValid' flag to true if it matches.
        isValid = isValidInput(input, Constants.SHOTREGEX);                                                                           
        if (isValid) {                                                              
            parseCoordinateInput(input);
        }
    }

    /* Constructor for ship placement. */
    public InputValidator(String input, int shipSize) {
        // validate the input string against the specified regular expression pattern
        // and set 'isValidRegex' to true if match.
        boolean isValidRegex = isValidInput(input, Constants.SHIPPLACEMENTREGEX);
        if (isValidRegex) {
            // validate that the input is also horizontal or vertical
            // and set 'isValid' flag to true if it is.
            parseShipPlacementInput(input);
            isValid = isValidPlacement(shipSize);
        }
    }

    private boolean isValidInput(String input, String regex) {
        return input.matches(regex);
    }

    private void parseCoordinateInput(String input) {                                             // split the input into coordinate.
        row = input.charAt(0);
        col = Integer.parseInt(input.substring(1));
    }

    private void parseShipPlacementInput(String input) {
        String[] positions = input.split("-");                                              // split input string on -
        String startInput = positions[0];                                                         // first coordinate.
        String endInput = positions[1];                                                           // second coordinate.
        startRow = startInput.charAt(0);                                                    // char at index 0 = A-J
        startCol = Integer.parseInt(startInput.substring(1));                          // parse the col number to int.
        endRow = endInput.charAt(0);
        endCol = Integer.parseInt(endInput.substring(1));
    }

    public boolean isValidPlacement(int shipSize) {
        char startRow = getStartRow();
        char endRow = getEndRow();
        int startCol = getStartCol();
        int endCol = getEndCol();
        
        // Check orientation (horizontal or vertical)
        boolean isHorizontal = startRow == endRow && startCol != endCol && startCol < endCol;       // also ensures that the input is not A2-A1 for instance, which also should work but it doesnt right now.
        boolean isVertical = startCol == endCol && startRow != endRow && startRow < endRow;         // same but for col, B3-A3

        // Check if the placement matches the ship's size
        int placementSize = isHorizontal ? Math.abs(endCol - startCol) + 1 : Math.abs(endRow - startRow) + 1;
        return (isHorizontal || isVertical) && placementSize == shipSize;
    }

    public char getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getStartRow() {
        return startRow;
    }

    public char getEndRow() {
        return endRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    public boolean isValid() {
        return isValid;
    }
}
