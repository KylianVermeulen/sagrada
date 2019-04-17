package nl.avans.sagrada.model;


public class PatternCard {
    private int id;
    private int difficulty;
    private boolean standard;

    private PatternCardField[] patterncardFields;

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
    public PatternCardField[] getPatterncardFields() {
        return patterncardFields;
    }

    /**
     * Set patternCardFields to PatternCard
     *
     * @param patterncardFields PatternCardField[]
     */
    public void setPatterncardFields(PatternCardField[] patterncardFields) {
        this.patterncardFields = patterncardFields;
    }

    /**
     * Get best location for a die
     *
     * @param die GameDie
     * @return PatternCardField
     */
    public PatternCardField getBestLocationForDie(GameDie die) {
        return null;
    }

    /**
     * Generate random PatternCard
     */
    public void generateRandomCard() {
    }
}
