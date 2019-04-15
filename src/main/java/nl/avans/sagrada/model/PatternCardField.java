package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

import static nl.avans.sagrada.Main.CARD_HEIGHT;
import static nl.avans.sagrada.Main.CARD_WIDTH;

public class PatternCardField {
    private GameDie die;
    private int xPos;
    private int yPos;
    private String color;
    private int value;
    private PatternCard patternCard;

    public PatternCardField(PatternCard patternCard) {
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

    public boolean checkSides(String color) {
        if (checkEast(color) && checkNorth(color) && checkSouth(color) && checkWest(color)) {
            return true;
        }
        return false;
    }

    private boolean checkValid(PatternCardField pcd, String color) {
        if (!pcd.hasColor() || pcd.hasEyes() || pcd.hasNoAttributes()) {
            return true;
        }
        // Dubble check
        if (pcd.hasColor() && pcd.getColor().equals(color)) {
            return false;
        }
        return true;
    }

    private boolean checkSouth(String color) {
        // yPos - 1
        if (yPos == 0) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos - 1);
        return checkValid(pcd, color);
    }

    private boolean checkNorth(String color) {
        // yPos + 1
        if (yPos == 3) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos + 1);
        return checkValid(pcd, color);
    }

    private boolean checkEast(String color) {
        // xPos + 1
        if (xPos == 4) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos + 1, yPos);
        return checkValid(pcd, color);
    }

    private boolean checkWest(String color) {
        // xPos - 1
        if (xPos == 0) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos - 1, yPos);
        return checkValid(pcd, color);
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

    public void addDie() {

    }
}
