package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class GameDie extends Die {
    private int eyes;
    private String color;
    private int roundTrack;
    private int round;
    private PatternCardField patternCardField;

    /**
     * Full constructor
     *
     * @param eyes int
     * @param color String
     */
    public GameDie(int eyes, String color) {
        super.setColor(color);
        this.eyes = eyes;
        this.color = super.getStringColor();
    }


    /**
     * Returns the color as a javafx Color
     *
     * @return Color
     */
    public Color getColor() {
        switch (color) {
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
     * Returns the color as a String
     *
     * @return String
     */
    public String getStringColor() {
        return this.color;
    }

    /**
     * Checks if the gameDie has a eye value
     *
     * @return boolean
     */
    public boolean hasEyes() {
        if (eyes == 0) return false;
        return true;
    }

    /**
     * Checks if the gameDie has a Color
     *
     * @return boolean
     */
    public boolean hasColor() {
        if (color == null) return false;
        return true;
    }

    /**
     * Returns the amount of eyes
     *
     * @return int
     */
    public int getEyes() {
        return this.eyes;
    }
}
