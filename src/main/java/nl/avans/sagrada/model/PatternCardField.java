package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class PatternCardField {
    private int xPos;
    private int yPos;
    private String color;
    private int value;

    private PatternCard patternCard;
    private GameDie die;

    /**
     * Empty constructor
     */
    public PatternCardField() {
    }

    /**
     * Full constructor
     *
     * @param xPos int
     * @param yPos int
     * @param color String
     * @param value int
     * @param patternCard PatternCard
     */
    public PatternCardField(int xPos, int yPos, String color, int value, PatternCard patternCard) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
        this.value = value;
        this.patternCard = patternCard;
    }

    /**
     * Partial constructor
     *
     * @param xPos int
     * @param yPos int
     * @param patternCard PatternCard
     */
    public PatternCardField(int xPos, int yPos, PatternCard patternCard) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.patternCard = patternCard;
    }

    /**
     * @return boolean true when color is set
     */
    public boolean hasColor() {
        if (color == null) {
            return false;
        }
        return true;
    }

    /**
     * @return boolean true when value is set
     */
    public boolean hasValue() {
        if (value == 0) {
            return false;
        }
        return true;
    }

    /**
     * Get xPos from PatternCardField
     *
     * @return int
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Set xPos to PatternCardField
     * @param xPos int
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Get yPos from PatternCardField
     *
     * @return int
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Set yPos to PatternCardField
     * @param yPos int
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Get value from PatternCardField
     *
     * @return int
     */
    public int getValue() {
        return value;
    }

    /**
     * Set value to PatternCardField
     *
     * @param value int
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Get Color converted from color string from PatternCardField
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
     * Set color string to PatternCardField
     *
     * @param color String
     */
    public void setColor(String color) {
        this.color = color;
    }
}
