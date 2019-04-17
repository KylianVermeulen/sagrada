package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.PatternCardFieldDAO;

import java.util.ArrayList;

public class PatternCard {
    private int id;
    private int difficulty;
    private boolean standard;

    private PatternCardField[][] patternCardFields;

    public static final int CARD_SQUARES_WIDTH = 5;
    public static final int CARD_SQUARES_HEIGHT = 4;

    /**
     * Empty constructor
     */
    public PatternCard() {
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
        ArrayList<PatternCardField> patterncardFieldsList = patternCardFieldDAO.getPatterncardFieldsOfPatterncard(this);
        return makePatternCardFields(patterncardFieldsList);
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
}
