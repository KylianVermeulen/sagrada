package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

import java.util.Random;

public class GameDie extends Die {
    private int eyes;
    private int roundTrack;
    private int round;
    private PatternCardField patternCardField;

    /**
     * Full constructor
     *
     * @param eyes int
     * @param color String
     */
    public GameDie(int number, String color, int eyes) {
        super(number, color);
        this.eyes = eyes;

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
     * Generates random die
     */
    public void generateDie() {

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
}
