package nl.avans.sagrada.model;

import java.util.ArrayList;
import java.util.Random;
import nl.avans.sagrada.dao.PatternCardFieldDao;

public class PatternCard {
    public static final int CARD_SQUARES_WIDTH = 5;
    public static final int CARD_SQUARES_HEIGHT = 4;
    private Random rnd;
    private int id;
    private int difficulty;
    private boolean standard;
    private PatternCardField[][] patternCardFields;
    private ArrayList<String> colors;

    /**
     * Partial constructor
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
        if (!patternCardFields[xPos][yPos].hasFieldAttributes() && patternCardFields[xPos][yPos]
                .checkSidesValue(value)) {
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
        if (!patternCardFields[xPos][yPos].hasFieldAttributes() && patternCardFields[xPos][yPos]
                .checkSidesColor(color)) {
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
//        for (PatternCardField patternCardField : list) {
//            patternCardFieldDao.addPatternCardField(patternCardField, this);
//        }
    }

    /**
     * Convert ArrayList to 2D Array of PatternCardField
     *
     * @param patternCardFieldsList ArrayList<PatternCardField>
     * @return PatternCardField[][]
     */
    private PatternCardField[][] makePatternCardFields(
            ArrayList<PatternCardField> patternCardFieldsList) {
        PatternCardField[][] patterncardFields = new PatternCardField[CARD_SQUARES_WIDTH + 1][CARD_SQUARES_HEIGHT + 1];
        int i = 0;
        for (int x = 1; x <= CARD_SQUARES_WIDTH; x++) {
            for (int y = 1; y <= CARD_SQUARES_HEIGHT; y++) {
                patterncardFields[x][y] = patternCardFieldsList.get(i);
                i++;
            }
        }
        return patterncardFields;
    }

    private PatternCardField[][] makeNewPatternCardFields() {
        PatternCardField[][] patterncardFields = new PatternCardField[CARD_SQUARES_WIDTH + 1][CARD_SQUARES_HEIGHT + 1];
        for (int x = 1; x <= CARD_SQUARES_WIDTH; x++) {
            for (int y = 1; y <= CARD_SQUARES_HEIGHT; y++) {
                PatternCardField patternCardField = new PatternCardField(x, y, this);
                patterncardFields[x][y] = patternCardField;
            }
        }
        return patterncardFields;
    }
}
