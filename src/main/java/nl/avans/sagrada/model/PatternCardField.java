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
     * Checks if the patternCardField neighbor to the selected patternCardField
     *
     * @param color String
     * @return boolean
     */
    public boolean checkSidesColor(String color) {
        return checkSouthColor(color) && checkEastColor(color) && checkNorthColor(color)
                && checkWestColor(color);
    }


    /**
     * Checks south of the patternCardField and checks if there is a color
     *
     * @param color String
     * @return boolean
     */
    private boolean checkSouthColor(String color) {
        if (yPos == 4) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos + 1);
        if (pcd.hasColor()) {
            return !pcd.getColor().equals(color);
        }
        return true;
    }

    /**
     * Checks north of the patternCardField and checks if there is a color
     *
     * @param color String
     * @return boolean
     */
    private boolean checkNorthColor(String color) {
        if (yPos == 1) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos - 1);
        if (pcd.hasColor()) {
            return !pcd.getColor().equals(color);
        }
        return true;
    }

    /**
     * Checks east of the patternCardField and checks if there is a color
     *
     * @param color String
     * @return boolean
     */
    private boolean checkEastColor(String color) {
        if (xPos == 5) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos + 1, yPos);
        if (pcd.hasColor()) {
            return !pcd.getColor().equals(color);
        }
        return true;
    }

    /**
     * Checks west of the patternCardField and checks if there is a color
     *
     * @param color String
     * @return boolean
     */
    private boolean checkWestColor(String color) {
        if (xPos == 1) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos - 1, yPos);
        if (pcd.hasColor()) {
            return !pcd.getColor().equals(color);
        }
        return true;
    }

    /**
     * Checks if the patternCardField neighbor to the selected patternCardField has an value
     * (value)
     *
     * @param value int
     * @return boolean
     */
    public boolean checkSidesValue(int value) {
        return checkSouthValue(value) && checkEastValue(value) && checkNorthValue(value)
                && checkWestValue(value);
    }

    /**
     * Checks south of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @return boolean
     */
    private boolean checkSouthValue(int value) {
        if (yPos == 4) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos + 1);
        if (pcd.hasValue()) {
            return !(pcd.getValue() == value);
        }
        return true;
    }

    /**
     * Checks north of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @return boolean
     */
    private boolean checkNorthValue(int value) {
        if (yPos == 1) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos - 1);
        if (pcd.hasValue()) {
            return !(pcd.getValue() == value);
        }
        return true;
    }

    /**
     * Checks east of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @return boolean
     */
    private boolean checkEastValue(int value) {
        if (xPos == 5) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos + 1, yPos);
        if (pcd.hasValue()) {
            return !(pcd.getValue() == value);
        }
        return true;
    }

    /**
     * Checks west of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @return boolean
     */
    private boolean checkWestValue(int value) {
        if (xPos == 1) {
            return true;
        }
        PatternCardField pcd = patternCard.getPatternCardField(xPos - 1, yPos);
        if (pcd.hasValue()) {
            return !(pcd.getValue() == value);
        }
        return true;
    }

    /**
     * @return boolean true when color is set
     */
    public boolean hasColor() {
        return color != null;
    }

    /**
     * @return boolean true when value is set
     */
    public boolean hasValue() {
        return value != 0;
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
     *
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
     *
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
    public Color getFXColor() {
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
     * Checks if the selected patternCardField has a color or a value (value)
     *
     * @return boolean
     */
    public boolean hasFieldAttributes() {
        if (hasColor()) {
            return true;
        }
        return hasValue();
    }

    /**
     * Return the color as a String value
     *
     * @return String
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Set color string to PatternCardField
     *
     * @param color String
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return The game die on this pattern card field.
     */
    public GameDie getDie() {
        return die;
    }

    /**
     * @param die Set the game die on this pattern card field.
     */
    public void setDie(GameDie die) {
        this.die = die;
    }

    /**
     * @return True when is pattern card field has a placed game die
     */
    public boolean hasDie() {
        return die != null;
    }
}
