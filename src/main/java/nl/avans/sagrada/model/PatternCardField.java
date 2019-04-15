package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class PatternCardField {
    private GameDie die;
    private int positionX;
    private int positionY;
    private String color;
    private int value;
    private PatternCard patternCard;

    public boolean canPlaceDieOnField(GameDie die) {
        return false;
    }

    public boolean hasColor() {
        if (color == null) {
            return false;
        }
        return true;
    }

    public boolean hasEyes() {
        if (value == 0) {
            return false;
        }
        return true;
    }

    public int getEyes() {
        return this.value;
    }

    public void setEyes(int eyes) {
        this.value = eyes;

    }

    public Color getColor() {
        return Color.valueOf(color);
    }

    public void setColor(String color) {
        this.color = color;

    }

    public boolean hasAttributes() {
        if (hasColor() || hasEyes()) {
            return true;
        }
        return false;
    }
}
