package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class GameDie extends Die {
    private int eyes;
    private int roundTrack;
    private int round;
    private PatternCardField patternCardField;

    /**
     * Full constructor
     * @param number the id of the die
     * @param color String
     * @param int eye
     */
    public GameDie(int number, String color, int eyes) {
        super(number, color);
        this.eyes = eyes;
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
    
    public void setPatternCardField(PatternCardField patternCardField) {
        this.patternCardField = patternCardField;
    }
    
    public PatternCardField getPatternCardField() {
        return patternCardField;
    }
}
