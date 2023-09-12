package src.model;

/* Constants that are used throughout the program. */
public class Constants {
    public static final int GRID_SIZE = 10;
    public static final char EMPTY_CELL = '*';
    public static final char SHIP = 'S';
    public static final char MISS = 'M';
    public static final char HIT = 'H';

    public static final int DESTROYER_SIZE = 2;
    public static final int SUBMARINE_SIZE = 3;
    public static final int CRUISER_SIZE = 3;
    public static final int BATTLESHIP_SIZE = 4;
    public static final int CARRIER_SIZE = 5;

    public static final String DESTROYER_IDENTIFIER = "De";
    public static final String SUBMARINE_IDENTIFIER = "Sm";
    public static final String CRUISER_IDENTIFIER = "Cr";
    public static final String BATTLESHIP_IDENTIFIER = "Bs";
    public static final String CARRIER_IDENTIFIER = "Ca";

    public static final String DESTROYER_TO_STRING = "Destroyer";
    public static final String SUBMARINE_TO_STRING = "Submarine";
    public static final String CRUISER_TO_STRING = "Cruiser";
    public static final String BATTLESHIP_TO_STRING = "Battleship";
    public static final String CARRIER_TO_STRING = "Carrier";

    public static final String SHIPPLACEMENTREGEX = "[A-J](10|[1-9])-[A-J](10|[1-9])";
    public static final String SHOTREGEX = "[A-J](10|[1-9])";
}
