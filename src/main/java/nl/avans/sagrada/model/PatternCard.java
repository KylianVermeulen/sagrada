package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.PatternCardFieldDAO;

import java.util.ArrayList;
import java.util.Random;

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
     * Empty PatternCard
     */
    public PatternCard() {
        patternCardFields = makeNewPatternCardFields();
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
        if (standard) {
            patternCardFields = getPatternCardFields();
        } else {
            patternCardFields = makeNewPatternCardFields();
            generateRandomCard();
        }
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
     * Random 50/50 chance if it's going to add a color or an value to the selected PatternCardField
     */
    private void generateRandomPatternCardField() {
        if (rnd.nextBoolean()) {
            addRandomValue();
        } else {
            addRandomColor();
        }
    }

    /**
     * Adds random value  to the selected patternCardField if it's not a valid patternCardField the method will run again
     */
    private void addRandomValue() {
        int xPos = rnd.nextInt(5);
        int yPos = rnd.nextInt(4);
        int value = rnd.nextInt(6) + 1;
        if (!patternCardFields[xPos][yPos].hasFieldAttributes() && patternCardFields[xPos][yPos].checkSidesValue(value, false)) {
            patternCardFields[xPos][yPos].setValue(value);
        } else {
            addRandomValue();
        }
    }

    /**
     * Adds random color to the selected patternCardField if it's not a valid patternCardField the method will run again
     */
    private void addRandomColor() {
        int xPos = rnd.nextInt(5);
        int yPos = rnd.nextInt(4);
        String color = colors.get(rnd.nextInt(colors.size()));
        if (!patternCardFields[xPos][yPos].hasFieldAttributes() && patternCardFields[xPos][yPos].checkSidesColor(color, false)) {
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
        PatternCardFieldDAO patternCardFieldDAO = new PatternCardFieldDAO();
        ArrayList<PatternCardField> patternCardFieldsList = patternCardFieldDAO.getPatternCardFieldsOfPatterncard(this);
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

    /**
     * Convert ArrayList to 2D Array of PatternCardField
     *
     * @param patternCardFieldsList ArrayList<PatternCardField>
     * @return PatternCardField[][]
     */
    private PatternCardField[][] makePatternCardFields(ArrayList<PatternCardField> patternCardFieldsList) {
        PatternCardField[][] patterncardFields = new PatternCardField[CARD_SQUARES_WIDTH][CARD_SQUARES_HEIGHT];
        int i = 0;
        for (int x = 0; x < CARD_SQUARES_WIDTH; x++) {
            for (int y = 0; y < CARD_SQUARES_HEIGHT; y++) {
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
        PatternCardField[][] patterncardFields = new PatternCardField[CARD_SQUARES_WIDTH][CARD_SQUARES_HEIGHT];
        for (int x = 0; x < CARD_SQUARES_WIDTH; x++) {
            for (int y = 0; y < CARD_SQUARES_HEIGHT; y++) {
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
        patternCardFields[xPos][yPos].placeDie(gameDie);
    }
}
