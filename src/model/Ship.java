package src.model;

public class Ship {

    public enum ShipType {
        DESTROYER, SUBMARINE, CRUISER, BATTLESHIP, CARRIER
    }

    private String identifier;
    private int size;
    private int hits;
    private boolean sunk;
    private ShipType type;

    /* Constructor for object creation. */
    public Ship(ShipType type) {
        this.type = type;
        
        // Set the size and identifier based on the ship type
        switch (type) {
            case DESTROYER:
                this.size = Constants.DESTROYER_SIZE;
                this.identifier = Constants.DESTROYER_IDENTIFIER;
                break;
            case SUBMARINE:
                this.size = Constants.SUBMARINE_SIZE;
                this.identifier = Constants.SUBMARINE_IDENTIFIER;
                break;
            case CRUISER:
                this.size = Constants.CRUISER_SIZE;
                this.identifier = Constants.CRUISER_IDENTIFIER;
                break;
            case BATTLESHIP:
                this.size = Constants.BATTLESHIP_SIZE;
                this.identifier = Constants.BATTLESHIP_IDENTIFIER;
                break;
            case CARRIER:
                this.size = Constants.CARRIER_SIZE;
                this.identifier = Constants.CARRIER_IDENTIFIER;
                break;
            default:
                this.size = 0; // Handle unknown ship types
                this.identifier = "Unknown"; // Handle unknown ship types
                break;
        }
    }

    /* Returns the type of ship as a string. */
    public String getTypeAsString() {
        // Convert the enum value to a more readable string
        switch (type) {
            case DESTROYER:
                return Constants.DESTROYER_TO_STRING;
            case SUBMARINE:
                return Constants.SUBMARINE_TO_STRING;
            case CRUISER:
                return Constants.CRUISER_TO_STRING;
            case BATTLESHIP:
                return Constants.BATTLESHIP_TO_STRING;
            case CARRIER:
                return Constants.CARRIER_TO_STRING;
            default:
                return "Unknown"; // Handle unknown ship types
        }
    }

    /* Return ship size. */
    public int getSize() {
        return size;
    }
    
    /* Return ship identifier. */
    public String getIdentifier() {
        return identifier;
    }
    
    /* Set ship hits. */
    public void setHits(int hits) {
        this.hits = hits;
    }
    
    /* Return ship hits, meaning ship HP. */
    public int getHits() {
        return hits;
    }
    
    /* Set bool sunk. */
    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    /* Return bool sunk. */
    public boolean isSunk() {
        return sunk;
    }
}
