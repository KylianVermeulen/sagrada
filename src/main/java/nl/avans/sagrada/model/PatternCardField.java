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
        if (yPos == 1 || yPos == 4 || xPos == 1 || xPos == 5) {
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
        if (patternCard.checkSidesColor(this, dieStringColor, true) && patternCard.checkSidesValue(this, dieEyes, true)) {
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
        } else if (patternCard.isNextToDie(this)) {
            this.die = gameDie;
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

    public PatternCard getPatternCard() {
        // TODO Auto-generated method stub
        return patternCard;
    }

    public void remove() {
        die = null;
    }
}
