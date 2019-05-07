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
     * @param isDie boolean
     * @return boolean
     */
    public boolean checkSidesColor(String color, boolean isDie) {
        return checkSouthColor(color, isDie)
                && checkEastColor(color, isDie)
                && checkNorthColor(color, isDie)
                && checkWestColor(color, isDie);
    }


    /**
     * Checks south of the patternCardField and checks if there is a color
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkSouthColor(String color) {
        if (yPos == 4) {
            return true;
        }
        PatternCardField patternCardField = patternCard.getPatternCardField(xPos, yPos + 1);
        return checkColorAndDieColor(color, patternCardField, isDie);
    }

    /**
     * Checks north of the patternCardField and checks if there is a color
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkNorthColor(String color) {
        if (yPos == 1) {
            return true;
        }
        PatternCardField patternCardField = patternCard.getPatternCardField(xPos, yPos - 1);
        return checkColorAndDieColor(color, patternCardField, isDie);
    }

    /**
     * Checks east of the patternCardField and checks if there is a color
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkEastColor(String color) {
        if (xPos == 5) {
            return true;
        }
        PatternCardField patternCardField = patternCard.getPatternCardField(xPos + 1, yPos);
        return checkColorAndDieColor(color, patternCardField, isDie);
    }

    /**
     * Checks west of the patternCardField and checks if there is a color
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkWestColor(String color) {
        if (xPos == 1) {
            return true;
        }
        PatternCardField patternCardField = patternCard.getPatternCardField(xPos - 1, yPos);
        return checkColorAndDieColor(color, patternCardField, isDie);
    }

    /**
     * Checks if the turn is valid
     *
     * @param color String
     * @param patternCardField PatternCardField
     * @param isDie boolean
     * @return
     */
    private boolean checkColorAndDieColor(String color, PatternCardField patternCardField, boolean isDie) {
        if (!isDie) {
            if (patternCardField.hasColor()) {
                return !patternCardField.getStringColor().equals(color);
            }
        }
        if (patternCardField.hasDie()) {
            return !(patternCardField.getDie().getColor().equals(color));
        }
        return true;
    }

    /**
     * Checks if the patternCardField neighbor to the selected patternCardField has an value
     * (value)
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    public boolean checkSidesValue(int value, boolean isDie) {
        return checkSouthValue(value, isDie)
                && checkEastValue(value, isDie)
                && checkNorthValue(value, isDie)
                && checkWestValue(value, isDie);
    }

    /**
     * Checks south of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkSouthValue(int value) {
        if (yPos == 4) {
            return true;
        }
        PatternCardField patternCardField = patternCard.getPatternCardField(xPos, yPos + 1);
        return checkValueAndValueDie(value, patternCardField, isDie);
    }

    private boolean checkValueAndValueDie(int value, PatternCardField patternCardField, boolean isDie) {
        if (!isDie) {
            if (patternCardField.hasValue()) {
                return !(patternCardField.getValue() == value);
            }
        }
        if (patternCardField.hasDie()) {
            return !(patternCardField.getDie().getEyes() == value);
        }
        return true;
    }

    /**
     * Checks north of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkNorthValue(int value) {
        if (yPos == 1) {
            return true;
        }
        PatternCardField patternCardField = patternCard.getPatternCardField(xPos, yPos - 1);
        return checkValueAndValueDie(value, patternCardField, isDie);
    }

    /**
     * Checks east of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkEastValue(int value) {
        if (xPos == 5) {
            return true;
        }
        PatternCardField patternCardField = patternCard.getPatternCardField(xPos + 1, yPos);
        return checkValueAndValueDie(value, patternCardField, isDie);
    }

    /**
     * Checks west of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkWestValue(int value) {
        if (xPos == 1) {
            return true;
        }
        PatternCardField patternCardField = patternCard.getPatternCardField(xPos - 1, yPos);
        return checkValueAndValueDie(value, patternCardField, isDie);
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
    public String getStringColor() {
        return this.color;
    }

    /**
     * Checks if the selected PatternCardField has a die on it
     *
     * @return boolean
     */
    public boolean hasDie() {
        if (die == null) {
            return false;
        }
        return true;
    }

    /**
     * Places die on the selected PatternCardField
     *
     * @param gameDie GameDie
     */
    public void placeDie(GameDie gameDie) {
        if (patternCard.isFirstTurn()) {
            if (nextToBorder()) {
                sideCheckPlaceDie(gameDie);
                return;
            }
            return;
        }
        if (hasDie()) {
            return;
        }
        sideCheckPlaceDie(gameDie);
    }

    /**
     * Checks if the selected PatternCardField is next to a border
     *
     * @return boolean
     */
    private boolean nextToBorder() {
        if (yPos == 0 || yPos == 3 || xPos == 0 || xPos == 4) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the placement is valid
     *
     * @param gameDie GameDie
     */
    private void sideCheckPlaceDie(GameDie gameDie) {
        int dieEyes = gameDie.getEyes();
        String dieStringColor = gameDie.getColor();
        if (checkSidesColor(dieStringColor, true) && checkSidesValue(dieEyes, true)) {
            if (hasColor()) {
                if (gameDie.getColor().equals(this.color)) {
                    checkTurn(gameDie);
                }
                return;
            }
            if (hasValue()) {
                if (gameDie.getEyes() == getValue()) {
                    checkTurn(gameDie);
                }
                return;
            }
            checkTurn(gameDie);
        }
    }

    /**
     * Checks if it's the first turn if not does a normal turn
     *
     * @param gameDie GameDie
     */
    private void checkTurn(GameDie gameDie) {
        if (patternCard.isFirstTurn()) {
            patternCard.setFirstTurn(false);
            this.die = gameDie;
        } else if (isNextToDie()) {
            this.die = gameDie;
        }
    }

    /**
     * Checks if the placed die is next to a die or diagonal to a die
     */
    private boolean isNextToDie() {
        if (checkNorthDie() || checkNorthEastDie() || checkEastDie() || checkSouthEastDie() || checkSouthDie() || checkSouthWestDie() || checkWestDie() || checkNorthWest()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if there is a die on the north PatternCardField
     *
     * @return boolean
     */
    private boolean checkNorthDie() {
        if (yPos == 0) {
            return false;
        }
        return patternCard.getPatternCardField(xPos, yPos - 1).hasDie();
    }

    /**
     * Checks if there is a die on the north-east PatternCardField
     *
     * @return boolean
     */
    private boolean checkNorthEastDie() {
        if (yPos == 0 || xPos == 4) {
            return false;
        }
        return patternCard.getPatternCardField(xPos + 1, yPos - 1).hasDie();
    }

    /**
     * Checks if there is a die on the east PatternCardField
     *
     * @return boolean
     */
    private boolean checkEastDie() {
        if (xPos == 4) {
            return false;
        }
        return patternCard.getPatternCardField(xPos + 1, yPos).hasDie();
    }

    /**
     * Checks if there is a die on the south-east PatternCardField
     *
     * @return boolean
     */
    private boolean checkSouthEastDie() {
        if (xPos == 4 || yPos == 3) {
            return false;
        }
        return patternCard.getPatternCardField(xPos + 1, yPos + 1).hasDie();
    }

    /**
     * Checks if there is a die on the south PatternCardField
     *
     * @return boolean
     */
    private boolean checkSouthDie() {
        if (yPos == 3) {
            return false;
        }
        return patternCard.getPatternCardField(xPos, yPos + 1).hasDie();
    }

    /**
     * Checks if there is a die on the south-west PatternCardField
     *
     * @return boolean
     */
    private boolean checkSouthWestDie() {
        if (yPos == 3 || xPos == 0) {
            return false;
        }
        return patternCard.getPatternCardField(xPos - 1, yPos + 1).hasDie();
    }

    /**
     * Checks if there is a die on the west PatternCardField
     *
     * @return boolean
     */
    private boolean checkWestDie() {
        if (xPos == 0) {
            return false;
        }
        return patternCard.getPatternCardField(xPos - 1, yPos).hasDie();
    }

    /**
     * Checks if there is a die on the north-west PatternCardField
     *
     * @return boolean
     */
    private boolean checkNorthWest() {
        if (xPos == 0 || yPos == 0) {
            return false;
        }
        return patternCard.getPatternCardField(xPos - 1, yPos - 1).hasDie();

    }

    /**
     * Returns the die on the selected PatternCardField
     *
     * @return GameDie
     */
    public GameDie getDie() {
        return this.die;
    }
}
