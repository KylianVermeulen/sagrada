package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class GameDie extends Die {
    private int eyes;
    private int roundTrack;
    private int round;
    private boolean isOnOfferTable;
    private PatternCardField patternCardField;

    /**
     * Full constructor
     *
     * @param number the id of the die
     * @param color String
     * @param eyes int
     */
    public GameDie(int number, String color, int eyes) {
        super(number, color);
        this.eyes = eyes;
        isOnOfferTable = false;
    }

    /**
     * Constructor with round
     *
     * @param number int
     * @param color String
     * @param eyes int
     * @param round int
     */
    public GameDie(int number, String color, int eyes, int round) {
        super(number, color);
        this.eyes = eyes;
        this.round = round;
        isOnOfferTable = false;
    }

    /**
     * Constructor with die
     *
     * @param die Die
     * @param eyes int
     */
    public GameDie(Die die, int eyes) {
        super(die);
        this.eyes = eyes;
        isOnOfferTable = false;
    }

    /**
     * Constructor with die and round
     *
     * @param die Die
     * @param eyes int
     * @param round int
     */
    public GameDie(Die die, int eyes, int round) {
        super(die);
        this.eyes = eyes;
        this.round = round;
        isOnOfferTable = false;
    }

    /**
     * The string color from a die.
     *
     * @return The color.
     */
    @Override
    public String getColor() {
        return super.getColor();
    }

    /**
     * The eyes/value from a die.
     *
     * @return The eyes/value.
     */
    public int getEyes() {
        return this.eyes;
    }

    /**
     * The JavaFX color from a die.
     *
     * @return Color object JavaFX.
     */
    public Color getFXColor() {
        switch (super.getColor()) {
            case "rood":
                return Color.RED;
            case "blauw":
                return Color.BLUE;
            case "geel":
                return Color.YELLOW;
            case "groen":
                return Color.GREEN;
            case "paars":
                return Color.PURPLE;
            default:
                return Color.WHITE;
        }
    }

    /**
     * Check if the die has eyes.
     *
     * @return True when has eyes.
     */
    public boolean hasEyes() {
        return eyes != 0;
    }

    /**
     * Check if the die has a color.
     *
     * @return True when has color.
     */
    public boolean hasColor() {
        return super.getColor() != null;
    }

    /**
     * Gets the round
     *
     * @return int
     */
    public int getRound() {
        return this.round;
    }

    /**
     * Sets the patterncardfield a die is located on
     */
    public void setPatternCardField(PatternCardField patternCardField) {
        this.patternCardField = patternCardField;
    }

    /**
     * Returns the patterncardfield the die in on
     */
    public PatternCardField getPatternCardField() {
        return patternCardField;
    }

    /**
     * Sets if the gamedie is on the offer table
     */
    public void setIsOnOfferTable(boolean onTable) {
        isOnOfferTable = onTable;
    }

    /**
     * Returns a boolean if the die is currently on the offer table
     *
     * @return boolean
     */
    public boolean getIsOnOfferTable() {
        return isOnOfferTable;
    }

    public void setEyes(int newEyes) {
        this.eyes = newEyes;
    }
}
