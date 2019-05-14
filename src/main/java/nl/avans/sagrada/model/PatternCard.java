package nl.avans.sagrada.model;

import java.util.ArrayList;
import java.util.Random;
import nl.avans.sagrada.dao.PatternCardFieldDao;
import nl.avans.sagrada.view.PatternCardFieldView;

public class PatternCard {
    public static final int CARD_SQUARES_WIDTH = 5;
    public static final int CARD_SQUARES_HEIGHT = 4;
    private boolean firstTurn = true;
    private Random rnd;
    private int id;
    private int difficulty;
    private boolean standard;
    private PatternCardField[][] patternCardFields;
    private ArrayList<String> colors;

    /**
     * Partial constructor
     *
     * @param id int
     * @param standard boolean
     */
    public PatternCard(int id, boolean standard) {
        this.id = id;
        this.standard = standard;
        if (standard) {
            patternCardFields = getPatternCardFields();
        } else {
            patternCardFields = makeNewPatternCardFields();
            generateRandomCard();
        }
    }

    /**
     * Full constructor
     *
     * @param id int
     * @param difficulty int
     * @param standard boolean
     */
    public PatternCard(int id, int difficulty, boolean standard) {
        this.id = id;
        this.difficulty = difficulty;
        this.standard = standard;
        patternCardFields = getPatternCardFields();
    }

    /**
     * Checks if it's the first turn
     *
     * @return boolean
     */
    public boolean isFirstTurn() {
        return this.firstTurn;
    }

    /**
     * Sets the firstTurn value
     *
     * @param firstTurn boolean
     */
    public void setFirstTurn(boolean firstTurn) {
        this.firstTurn = firstTurn;
    }

    /**
     * makes an String array with all the possible colors
     */
    private void makeColors() {
        colors = new ArrayList<String>();
        colors.add("blauw");
        colors.add("groen");
        colors.add("geel");
        colors.add("paars");
        colors.add("rood");
    }

    /**
     * Generates a random PatternCard
     */
    public void generateRandomCard() {
        rnd = new Random();
        makeColors();
        generateRandomDifficulty();
        for (int i = 0; i < getDifficulty() * 2; i++) {
            generateRandomPatternCardField();
        }
    }

    /**
     * Generates a random difficulty
     */
    private void generateRandomDifficulty() {
        setDifficulty(rnd.nextInt(6) + 1);
    }

    /**
     * Random 50/50 chance if it's going to add a color or an value to the selected
     * PatternCardField
     */
    private void generateRandomPatternCardField() {
        if (rnd.nextBoolean()) {
            addRandomValue();
        } else {
            addRandomColor();
        }
    }

    /**
     * Adds random value  to the selected patternCardField if it's not a valid patternCardField the
     * method will run again
     */
    private void addRandomValue() {
        int xPos = rnd.nextInt(4) + 1;
        int yPos = rnd.nextInt(3) + 1;
        int value = rnd.nextInt(6) + 1;
        if (!patternCardFields[xPos][yPos].hasFieldAttributes() && checkSidesValue(patternCardFields[xPos][yPos], value, false)) {
            patternCardFields[xPos][yPos].setValue(value);
        } else {
            addRandomValue();
        }
    }

    /**
     * Adds random color to the selected patternCardField if it's not a valid patternCardField the
     * method will run again
     */
    private void addRandomColor() {
        int xPos = rnd.nextInt(4) + 1;
        int yPos = rnd.nextInt(3) + 1;
        String color = colors.get(rnd.nextInt(colors.size()));
        if (!patternCardFields[xPos][yPos].hasFieldAttributes() && checkSidesColor(patternCardFields[xPos][yPos], color, false)) {
            patternCardFields[xPos][yPos].setColor(color);
        } else {
            addRandomColor();
        }
    }

    /**
     * Get id from PatternCard
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Set id to PatternCard
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get difficulty from PatternCard
     *
     * @return int
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Set difficulty to PatternCard
     *
     * @param difficult int
     */
    public void setDifficulty(int difficult) {
        this.difficulty = difficult;
    }

    /**
     * Get standard from PatternCard
     *
     * @return boolean true when standard
     */
    public boolean isStandard() {
        return standard;
    }

    /**
     * Set standard to PatternCard
     *
     * @param standard boolean
     */
    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    /**
     * Get patternCardFields from PatternCard
     *
     * @return PatternCardField[]
     */
    public PatternCardField[][] getPatternCardFields() {
        PatternCardFieldDao patternCardFieldDao = new PatternCardFieldDao();
        ArrayList<PatternCardField> patternCardFieldsList = patternCardFieldDao
                .getPatternCardFieldsOfPatterncard(this);
        return makePatternCardFields(patternCardFieldsList);
    }

    /**
     * Set patternCardFields to PatternCard
     *
     * @param patternCardFields PatternCardField[]
     */
    public void setPatterncardFields(PatternCardField[][] patternCardFields) {
        this.patternCardFields = patternCardFields;
    }

    /**
     * Get a PatternCardField by location
     *
     * @param x int
     * @param y int
     * @return PatternCardField
     */
    public PatternCardField getPatternCardField(int x, int y) {
        return patternCardFields[x][y];
    }

    public void saveNewPatternCardFields() {
        ArrayList<PatternCardField> list = new ArrayList<>();
        PatternCardFieldDao patternCardFieldDao = new PatternCardFieldDao();
        for (int x = 1; x <= CARD_SQUARES_WIDTH; x++) {
            for (int y = 1; y <= CARD_SQUARES_HEIGHT; y++) {
                list.add(patternCardFields[x][y]);
            }
        }
        patternCardFieldDao.addPatternCardFields(list, this);
    }

    /**
     * Convert ArrayList to 2D Array of PatternCardField
     *
     * @param patternCardFieldsList ArrayList<PatternCardField>
     * @return PatternCardField[][]
     */
    private PatternCardField[][] makePatternCardFields(
            ArrayList<PatternCardField> patternCardFieldsList) {
        PatternCardField[][] patterncardFields = new PatternCardField[CARD_SQUARES_WIDTH + 1][
                CARD_SQUARES_HEIGHT + 1];
        int i = 0;
        for (int x = 1; x <= CARD_SQUARES_WIDTH; x++) {
            for (int y = 1; y <= CARD_SQUARES_HEIGHT; y++) {
                patterncardFields[x][y] = patternCardFieldsList.get(i);
                i++;
            }
        }
        return patterncardFields;
    }

    /**
     * Makes an empty PatternCard
     *
     * @return PatternCard[][]
     */
    private PatternCardField[][] makeNewPatternCardFields() {
        PatternCardField[][] patterncardFields = new PatternCardField[CARD_SQUARES_WIDTH + 1][
                CARD_SQUARES_HEIGHT + 1];
        for (int x = 1; x <= CARD_SQUARES_WIDTH; x++) {
            for (int y = 1; y <= CARD_SQUARES_HEIGHT; y++) {
                PatternCardField patternCardField = new PatternCardField(x, y, this);
                patterncardFields[x][y] = patternCardField;
            }
        }
        return patterncardFields;
    }

    /**
     * Places die on the selected PatternCardField
     *
     * @param xPos int
     * @param yPos int
     * @param gameDie GameDie
     */
    public void placeDie(int xPos, int yPos, GameDie gameDie) {
        PatternCardField patternCardField = patternCardFields[xPos][yPos]; 
        patternCardField.placeDie(gameDie);
    }
    
    /**
     * Removed the die from the patterncardfield
     * @param patternCardField
     */
    public void removeDie(PatternCardField patternCardField) {
        patternCardField.setDie(null);
    }
    
    /**
     * Places a die on the patterncard field
     * @param patternCardField
     * @param die
     */
    public void placeDie(PatternCardField patternCardField, GameDie die) {
        int xPos = patternCardField.getxPos();
        int yPos = patternCardField.getyPos();
        
        placeDie(xPos, yPos, die);
    }

    /**
     * Check the color of the north east placed die, return null when there is no die and when the
     * color is not the same. Return the pattern card field when there is a placed die with the same
     * color.
     *
     * @param color The color.
     * @return The pattern card field.
     */
    public PatternCardField checkPatternCardFieldNorthEastDieColor(
            PatternCardField patternCardField, String color) {
        if (patternCardField.getxPos() == 5 || patternCardField.getyPos() == 1) {
            return null;
        }
        if (!getPatternCardField(patternCardField.getxPos() + 1, patternCardField.getyPos() - 1)
                .hasDie()) {
            return null;
        }
        GameDie gameDie = getPatternCardField(patternCardField.getxPos() + 1,
                patternCardField.getyPos() - 1).getDie();
        if (gameDie.getColor().equals(color)) {
            Integer[] loc = {patternCardField.getxPos() + 1, patternCardField.getyPos() - 1};
            return getPatternCardField(patternCardField.getxPos() + 1,
                    patternCardField.getyPos() - 1);
        }
        return null;
    }

    /**
     * Check the color of the south east placed die, return null when there is no die and when the
     * color is not the same. Return the pattern card field when there is a placed die with the same
     * color.
     *
     * @param color The color.
     * @return The pattern card field.
     */
    public PatternCardField checkPatternCardFieldSouthEastDieColor(
            PatternCardField patternCardField, String color) {
        if (patternCardField.getxPos() == 5 || patternCardField.getyPos() == 4) {
            return null;
        }
        if (!getPatternCardField(patternCardField.getxPos() + 1, patternCardField.getyPos() + 1)
                .hasDie()) {
            return null;
        }
        GameDie gameDie = getPatternCardField(patternCardField.getxPos() + 1,
                patternCardField.getyPos() + 1).getDie();
        if (gameDie.getColor().equals(color)) {
            return getPatternCardField(patternCardField.getxPos() + 1,
                    patternCardField.getyPos() + 1);
        }
        return null;
    }

    /**
     * Check the color of the south west placed die, return null when there is no die and when the
     * color is not the same. Return the pattern card field when there is a placed die with the same
     * color.
     *
     * @param color The color.
     * @return The pattern card field.
     */
    public PatternCardField checkPatternCardFieldSouthWestDieColor(
            PatternCardField patternCardField, String color) {
        if (patternCardField.getxPos() == 1 || patternCardField.getyPos() == 4) {
            return null;
        }
        if (!getPatternCardField(patternCardField.getxPos() - 1, patternCardField.getyPos() + 1)
                .hasDie()) {
            return null;
        }
        GameDie gameDie = getPatternCardField(patternCardField.getxPos() - 1,
                patternCardField.getyPos() + 1).getDie();
        if (gameDie.getColor().equals(color)) {
            return getPatternCardField(patternCardField.getxPos() - 1,
                    patternCardField.getyPos() + 1);
        }
        return null;
    }

    /**
     * Check the color of the north west placed die, return null when there is no die and when the
     * color is not the same. Return the pattern card field when there is a placed die with the same
     * color.
     *
     * @param color The color.
     * @return The pattern card field.
     */
    public PatternCardField checkPatternCardFieldNorthWestDieColor(
            PatternCardField patternCardField, String color) {
        if (patternCardField.getxPos() == 1 || patternCardField.getyPos() == 1) {
            return null;
        }
        if (!getPatternCardField(patternCardField.getxPos() - 1, patternCardField.getyPos() - 1)
                .hasDie()) {
            return null;
        }
        GameDie gameDie = getPatternCardField(patternCardField.getxPos() - 1,
                patternCardField.getyPos() - 1).getDie();
        if (gameDie.getColor().equals(color)) {
            return getPatternCardField(patternCardField.getxPos() - 1,
                    patternCardField.getyPos() - 1);
        }
        return null;
    }

    /**
     * Checks if the patternCardField neighbor to the selected patternCardField
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    public boolean checkSidesColor(PatternCardField patternCardField, String color, boolean isDie) {
        return checkSouthColor(patternCardField, color, isDie)
                && checkEastColor(patternCardField, color, isDie)
                && checkNorthColor(patternCardField, color, isDie)
                && checkWestColor(patternCardField, color, isDie);
    }

    /**
     * Checks south of the patternCardField and checks if there is a color
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkSouthColor(PatternCardField patternCardField, String color, boolean isDie) {
        if (patternCardField.getyPos() == 4) {
            return true;
        }
        PatternCardField patternCardFieldNext = getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos() + 1);
        return checkColorAndDieColor(patternCardFieldNext, color, isDie);
    }

    /**
     * Checks north of the patternCardField and checks if there is a color
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkNorthColor(PatternCardField patternCardField, String color, boolean isDie) {
        if (patternCardField.getyPos() == 1) {
            return true;
        }
        PatternCardField patternCardFieldNext = getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos() - 1);
        return checkColorAndDieColor(patternCardFieldNext, color, isDie);
    }

    /**
     * Checks east of the patternCardField and checks if there is a color
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkEastColor(PatternCardField patternCardField, String color, boolean isDie) {
        if (patternCardField.getxPos() == 5) {
            return true;
        }
        PatternCardField patternCardFieldNext = getPatternCardField(patternCardField.getxPos() + 1, patternCardField.getyPos());
        return checkColorAndDieColor(patternCardFieldNext, color, isDie);
    }

    /**
     * Checks west of the patternCardField and checks if there is a color
     *
     * @param color String
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkWestColor(PatternCardField patternCardField, String color, boolean isDie) {
        if (patternCardField.getxPos() == 1) {
            return true;
        }
        PatternCardField patternCardFieldNext = getPatternCardField(patternCardField.getxPos() - 1, patternCardField.getyPos());
        return checkColorAndDieColor(patternCardFieldNext, color, isDie);
    }

    /**
     * Checks if the turn is valid
     *
     * @param color String
     * @param patternCardField PatternCardField
     * @param isDie boolean
     * @return
     */
    private boolean checkColorAndDieColor(PatternCardField patternCardField, String color, boolean isDie) {
        if (!isDie) {
            if (patternCardField.hasColor()) {
                return !patternCardField.getColor().equals(color);
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
    public boolean checkSidesValue(PatternCardField patternCardField, int value, boolean isDie) {
        return checkSouthValue(patternCardField, value, isDie)
                && checkEastValue(patternCardField, value, isDie)
                && checkNorthValue(patternCardField, value, isDie)
                && checkWestValue(patternCardField, value, isDie);
    }

    /**
     * Checks south of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkSouthValue(PatternCardField patternCardField, int value, boolean isDie) {
        if (patternCardField.getyPos() == 4) {
            return true;
        }
        PatternCardField patternCardFieldNext = getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos() + 1);
        return checkValueAndValueDie(patternCardFieldNext, value, isDie);
    }

    /**
     * Checks north of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkNorthValue(PatternCardField patternCardField, int value, boolean isDie) {
        if (patternCardField.getyPos() == 1) {
            return true;
        }
        PatternCardField patternCardFieldNext = getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos() - 1);
        return checkValueAndValueDie(patternCardFieldNext, value, isDie);
    }

    /**
     * Checks east of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkEastValue(PatternCardField patternCardField, int value, boolean isDie) {
        if (patternCardField.getxPos() == 5) {
            return true;
        }
        PatternCardField patternCardFieldNext = getPatternCardField(patternCardField.getxPos() + 1, patternCardField.getyPos());
        return checkValueAndValueDie(patternCardFieldNext, value, isDie);
    }

    /**
     * Checks west of the patternCardField and checks if there is an eye value
     *
     * @param value int
     * @param isDie boolean
     * @return boolean
     */
    private boolean checkWestValue(PatternCardField patternCardField, int value, boolean isDie) {
        if (patternCardField.getxPos() == 1) {
            return true;
        }
        PatternCardField patternCardFieldNext = getPatternCardField(patternCardField.getxPos() - 1, patternCardField.getyPos());
        return checkValueAndValueDie(patternCardFieldNext, value, isDie);
    }

    private boolean checkValueAndValueDie(PatternCardField patternCardField, int value, boolean isDie) {
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
     * Checks if the placed die is next to a die or diagonal to a die
     */
    public boolean isNextToDie(PatternCardField patternCardField) {
        if (checkNorthDie(patternCardField) || checkNorthEastDie(patternCardField) || checkEastDie(patternCardField) || checkSouthEastDie(patternCardField)
                || checkSouthDie(patternCardField) || checkSouthWestDie(patternCardField) || checkWestDie(patternCardField) || checkNorthWest(patternCardField)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if there is a die on the north PatternCardField
     *
     * @return boolean
     */
    private boolean checkNorthDie(PatternCardField patternCardField) {
        if (patternCardField.getyPos() == 1) {
            return false;
        }
        return getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos() - 1).hasDie();
    }

    /**
     * Checks if there is a die on the north-east PatternCardField
     *
     * @return boolean
     */
    private boolean checkNorthEastDie(PatternCardField patternCardField) {
        if (patternCardField.getyPos() == 1 || patternCardField.getxPos() == 5) {
            return false;
        }
        return getPatternCardField(patternCardField.getxPos() + 1, patternCardField.getyPos() - 1).hasDie();
    }

    /**
     * Checks if there is a die on the east PatternCardField
     *
     * @return boolean
     */
    private boolean checkEastDie(PatternCardField patternCardField) {
        if (patternCardField.getxPos() == 5) {
            return false;
        }
        return getPatternCardField(patternCardField.getxPos() + 1, patternCardField.getyPos()).hasDie();
    }

    /**
     * Checks if there is a die on the south-east PatternCardField
     *
     * @return boolean
     */
    private boolean checkSouthEastDie(PatternCardField patternCardField) {
        if (patternCardField.getxPos() == 5 || patternCardField.getyPos() == 4) {
            return false;
        }
        return getPatternCardField(patternCardField.getxPos() + 1, patternCardField.getyPos() + 1).hasDie();
    }

    /**
     * Checks if there is a die on the south PatternCardField
     *
     * @return boolean
     */
    private boolean checkSouthDie(PatternCardField patternCardField) {
        if (patternCardField.getyPos() == 4) {
            return false;
        }
        return getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos() + 1).hasDie();
    }

    /**
     * Checks if there is a die on the south-west PatternCardField
     *
     * @return boolean
     */
    private boolean checkSouthWestDie(PatternCardField patternCardField) {
        if (patternCardField.getyPos() == 4 || patternCardField.getxPos() == 1) {
            return false;
        }
        return getPatternCardField(patternCardField.getxPos() - 1, patternCardField.getyPos() + 1).hasDie();
    }

    /**
     * Checks if there is a die on the west PatternCardField
     *
     * @return boolean
     */
    private boolean checkWestDie(PatternCardField patternCardField) {
        if (patternCardField.getxPos() == 1) {
            return false;
        }
        return getPatternCardField(patternCardField.getxPos() - 1, patternCardField.getyPos()).hasDie();
    }

    /**
     * Checks if there is a die on the north-west PatternCardField
     *
     * @return boolean
     */
    private boolean checkNorthWest(PatternCardField patternCardField) {
        if (patternCardField.getxPos()== 1 || patternCardField.getyPos() == 1) {
            return false;
        }
        return getPatternCardField(patternCardField.getxPos() - 1, patternCardField.getyPos() - 1).hasDie();

    }
}
