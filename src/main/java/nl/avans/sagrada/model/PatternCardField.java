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
        return checkSouthColor(this.xPos, this.yPos, color)
                && checkEastColor(this.xPos, this.yPos, color)
                && checkNorthColor(this.xPos, this.yPos, color)
                && checkWestColor(this.xPos, this.yPos, color);
    }


    /**
     * Checks south of the patternCardField and checks if there is a color
     *
     * @param xPos int
     * @param yPos int
     * @param color String
     * @return boolean
     */
    private boolean checkSouthColor(int xPos, int yPos, String color) {
        if (yPos == 3) return true;
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos + 1);
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    /**
     * Checks north of the patternCardField and checks if there is a color
     *
     * @param xPos int
     * @param yPos int
     * @param color String
     * @return boolean
     */
    private boolean checkNorthColor(int xPos, int yPos, String color) {
        if (yPos == 0) return true;
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos - 1);
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    /**
     * Checks east of the patternCardField and checks if there is a color
     *
     * @param xPos int
     * @param yPos int
     * @param color String
     * @return boolean
     */
    private boolean checkEastColor(int xPos, int yPos, String color) {
        if (xPos == 4) return true;
        PatternCardField pcd = patternCard.getPatternCardField(xPos + 1, yPos);
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    /**
     * Checks west of the patternCardField and checks if there is a color
     *
     * @param xPos int
     * @param yPos int
     * @param color String
     * @return boolean
     */
    private boolean checkWestColor(int xPos, int yPos, String color) {
        if (xPos == 0) return true;
        PatternCardField pcd = patternCard.getPatternCardField(xPos - 1, yPos);
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    /**
     * Checks if the patternCardField neighbor to the selected patternCardField has an value (eyes)
     *
     * @param eyes int
     * @return boolean
     */
    public boolean checkSidesEyes(int eyes) {
        return checkSouthEyes(this.xPos, this.yPos, eyes)
                && checkEastEyes(this.xPos, this.yPos, eyes)
                && checkNorthEyes(this.xPos, this.yPos, eyes)
                && checkWestEyes(this.xPos, this.yPos, eyes);
    }

    /**
     * Checks south of the patternCardField and checks if there is an eye value
     *
     * @param xPos int
     * @param yPos int
     * @param eyes int
     * @return boolean
     */
    private boolean checkSouthEyes(int xPos, int yPos, int eyes) {
        if (yPos == 3) return true;
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos + 1);
        if (pcd.hasValue()) return !(pcd.getValue() == eyes);
        return true;
    }

    /**
     * Checks north of the patternCardField and checks if there is an eye value
     *
     * @param xPos int
     * @param yPos int
     * @param eyes int
     * @return boolean
     */
    private boolean checkNorthEyes(int xPos, int yPos, int eyes) {
        if (yPos == 0) return true;
        PatternCardField pcd = patternCard.getPatternCardField(xPos, yPos - 1);
        if (pcd.hasValue()) return !(pcd.getValue() == eyes);
        return true;
    }

    /**
     * Checks east of the patternCardField and checks if there is an eye value
     *
     * @param xPos int
     * @param yPos int
     * @param eyes int
     * @return boolean
     */
    private boolean checkEastEyes(int xPos, int yPos, int eyes) {
        if (xPos == 4) return true;
        PatternCardField pcd = patternCard.getPatternCardField(xPos + 1, yPos);
        if (pcd.hasValue()) return !(pcd.getValue() == eyes);
        return true;
    }

    /**
     * Checks west of the patternCardField and checks if there is an eye value
     *
     * @param xPos int
     * @param yPos int
     * @param eyes int
     * @return boolean
     */
    private boolean checkWestEyes(int xPos, int yPos, int eyes) {
        if (xPos == 0) return true;
        PatternCardField pcd = patternCard.getPatternCardField(xPos - 1, yPos);
        if (pcd.hasValue()) return !(pcd.getValue() == eyes);
        return true;
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

    /**
     * Checks if the selected patternCardField has a color or a value (eyes)
     *
     * @return boolean
     */
    public boolean hasFieldAttributes() {
        if (hasColor()) return true;
        if (hasValue()) return true;
        return false;
    }

    /**
     * Return the color as a String value
     *
     * @return String
     */
    public String getStringColor() {
        return this.color;
    }
}
