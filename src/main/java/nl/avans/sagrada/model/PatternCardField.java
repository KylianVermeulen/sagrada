package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class PatternCardField {
    private int xPos;
    private int yPos;
    private String color;
    private int value;

    private PatternCard patternCard;
    private GameDie die;

    public PatternCardField(int xPos, int yPos, String color, int value, PatternCard patternCard) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
        this.value = value;
        this.patternCard = patternCard;
    }

    public boolean hasColor() {
        if (color == null) {
            return false;
        }
        return true;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
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

    public String getStringColor(){
        return this.color;
    }

    public Color getColor() {
        switch (color) {
            case "red":
                return Color.RED;
            case "blauw":
                return Color.BLUE;
            case "geel":
                return Color.YELLOW;
            case "groen":
                return Color.GREEN;
            case "paars":
                return Color.PURPLE;
        }
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
