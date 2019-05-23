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
     * Checks if the die can be placed based on the attributes that are
     * On the patterncardfield
     * @param die
     * @return boolean
     */
    public boolean canPlaceDieByAttributes(GameDie die) {
        if (hasFieldAttributes() == false) {
            return true;
        }
        else if (hasColor()) {
            if (getColor().equals(die.getColor())) {
                return true;
            }
        }
        else if (hasValue()) {
            if (getValue() == die.getEyes()) {
                return true;
            }
        }
        return false;
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
     * Places die on the selected PatternCardField
     * Returns a boolean if the die is placed
     *
     * @param gameDie GameDie
     * @return boolean
     */
    public boolean placeDie(GameDie gameDie) {
        if (patternCard.isFirstTurn()) {
            if (nextToBorder()) {
                if (sideCheckPlaceDie(gameDie)) {
                    if (sideCheckPlaceDie(gameDie)) {
                        gameDie.setPatternCardField(this);
                        setDie(gameDie);
                        return true;
                    }
                }
            }
        }
        if (hasDie() == false) {
            if (sideCheckPlaceDie(gameDie)) {
                gameDie.setPatternCardField(this);
                setDie(gameDie);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if we can place a die on the current patterncard field
     * @param gameDie
     * @return boolean
     */
    public boolean canPlaceDie(GameDie gameDie) {
        if (patternCard.isFirstTurn()) {
            if (nextToBorder()) {
                if (sideCheckPlaceDie(gameDie)) {
                    if (checkTurn(gameDie)) {
                        return true;
                    }
                }
            }
        }
        else if (hasDie() == false) {
            if (sideCheckPlaceDie(gameDie)) {
                return true;
            }
        }
        return false;
    }
    

    /**
     * Checks if the selected PatternCardField is next to a border
     *
     * @return boolean
     */
    private boolean nextToBorder() {
        if (yPos == 1 || yPos == 4 || xPos == 1 || xPos == 5) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the placement is valid
     * Based on the side check
     *
     * @param gameDie GameDie
     * @return boolean
     */
    private boolean sideCheckPlaceDie(GameDie gameDie) {
        int dieEyes = gameDie.getEyes();
        String dieStringColor = gameDie.getColor();
        if (patternCard.checkSidesColor(this, dieStringColor, true) && patternCard.checkSidesValue(this, dieEyes, true)) {
            if (hasColor()) {
                if (gameDie.getColor().equals(this.color)) {
                    return true;
                }
                return false;
            }
            if (hasValue()) {
                if (gameDie.getEyes() == getValue()) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }   

    /**
     * Checks if it's the first turn if not does a normal turn
     *
     * @param gameDie GameDie
     * @return boolean
     */
    private boolean checkTurn(GameDie gameDie) {
        if (patternCard.isFirstTurn()) {
            return true;
        } else if (patternCard.isNextToDie(this)) {
            return true;
        }
        else {
            return false;
        }
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

    /**
     * Gets the patterncard that the PatternCardField is a child of
     * @return PatternCard
     */
    public PatternCard getPatternCard() {
        return patternCard;
    }

    /**
     * Removes the die from the view
     */
    public void remove() {
        die = null;
    }
}
