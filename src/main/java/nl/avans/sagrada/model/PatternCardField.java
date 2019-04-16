package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class PatternCardField {
    private int xPos;
    private int yPos;
    private String color;
    private int value;
    private PatternCard patternCard;
    private GameDie die;

    public PatternCardField(PatternCard patternCard) {
        this.patternCard = patternCard;
    }

    public PatternCardField(int xPos, int yPos, String color, int value, PatternCard patternCard) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
        this.value = value;
        this.patternCard = patternCard;
    }

    public boolean canPlaceDieOnField(GameDie die) {
        if (hasColor()) {

        }
        return false;
    }


    public boolean hasColor() {
        if (color == null) {
            return false;
        }
        return true;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public boolean hasNoAttributes() {
        if (!hasEyes() && !hasColor()) {
            return true;
        }
        return false;
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

    public void addDie() {

    }
}
