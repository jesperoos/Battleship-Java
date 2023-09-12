package src.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private String name;
    private Grid playerGrid;
    private Grid targetGrid;
    private List<Ship> ships;
    private char currentRow;
    private int currentCol;
    private int firedShots;
    private int hits;
    private Set<String> prevShots;
    
    /* Constructor for object creation. */
    public Player(String name) {
        this.name = name;
        this.playerGrid = new Grid();
        this.targetGrid = new Grid();
        ships = new ArrayList<>();
        this.currentRow = ' ';
        this.currentCol = -1;
        this.prevShots = new HashSet<>();
    }

    /* Remove ship from list, used when ship is sunk.
     * Not used at the moment cause a ship is removed from the controller class, by using iterator.
    */
    public void removeShip(Ship ship) {
        this.ships.remove(ship);
    }

    /* Add a ship to the players ship list. */
    public void addShip(Ship ship) {
        this.ships.add(ship);
    }

    /* Return players list of ships. */
    public List<Ship> getPlayerShips() {
        return ships;
    }

    /* Set player name. */
    public void setName(String name) {
        this.name = name;
    }

    /* Return player name. */
    public String getName() {
        return name;
    }

    /* Return player game grid, where players ships are placed. */
    public Grid getPlayerGrid() {
        return playerGrid;
    }

    /* Return players target grid, where player stores his shots. */
    public Grid getPlayerTargetGrid() {
        return targetGrid;
    }

    /* Set player shot coordinates. */
    public void setCurrentShot(char row, int col) {
        this.currentRow = row;
        this.currentCol = col;
    }

    /* Get shot row coordinate. */
    public char getCurrentRow() {
        return currentRow;
    }

    /* Get shot col coordinate. */
    public int getCurrentCol() {
        return currentCol;
    }

    /* Increment number of shots fired. */
    public void incrementFiredShots() {
        this.firedShots++;
    }

    /* Return fired shots. */
    public int getFiredShots() {
        return firedShots;
    }

    /* Increment number of shots hit. */
    public void incrementHits() {
        this.hits++;
    }

    /* Return players hits. */
    public int getHits() {
        return hits;
    }

    /* Bool for checking if a coordinate has already been fired on. */
    public boolean hasBeenTargeted(char row, int col) {
        String coordinate = String.valueOf(row) + col;
        return prevShots.contains(coordinate);
    }

    /* Adding coordinate to players previously targeted coordinates list.
     * This is used to check if a coordinate has already been fired upon.
     */
    public void addTargetCoordinate(char row, int col) {
        String coordinate = String.valueOf(row) + col;
        prevShots.add(coordinate);
    }
}
