package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class GameDie extends Die {
    private int eyes;
    private String color;
    private int roundTrack;
    private int round;
    private PatternCardField patternCardField;

    public GameDie(int eyes, String color) {
        super.setColor(color);
        this.eyes = eyes;
        this.color = super.getStringColor();
    }

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

    public String getStringColor() {
        return this.color;
    }

    public boolean hasEyes() {
        if (eyes == 0) return false;
        return true;
    }

    public boolean hasColor() {
        if (color == null) return false;
        return true;
    }

    public int getEyes() {
        return this.eyes;
    }
}