package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

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

    @Override
    public String getColor() {
        return super.getColor();
    }

    public int getEyes() {
        return this.eyes;
    }

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

    public boolean hasEyes() {
        return eyes != 0;
    }

    public boolean hasColor() {
        return super.getColor() != null;
    }
}
