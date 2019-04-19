package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.PatternCardFieldDAO;

import java.util.ArrayList;
import java.util.Random;

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
     * Empty constructor
     */
    public PatternCard() {
    }

    /**
     * Full constructor
     *
     * @param id         int
     * @param difficulty int
     * @param standard   boolean
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

    private void makeColors() {
        colors = new ArrayList<String>();
        colors.add("blauw");
        colors.add("groen");
        colors.add("geel");
        colors.add("paars");
        colors.add("rood");
    }

    public void generateRandomCard() {
        rnd = new Random();
        makeColors();
        generateRandomDifficulty();
        for (int i = 0; i < getDifficulty() * 2; i++) {
            generateRandomPatternCardField();
        }
    }

    public boolean checkSidesColor(int xPos, int yPos, String color) {
        return checkSouthColor(xPos, yPos, color) && checkEastColor(xPos, yPos, color) && checkNorthColor(xPos, yPos, color) && checkWestColor(xPos, yPos, color);
    }

    private boolean checkSouthColor(int xPos, int yPos, String color) {
        if (yPos == 3) return true;
        PatternCardField pcd = patternCardFields[xPos][yPos + 1];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    private boolean checkNorthColor(int xPos, int yPos, String color) {
        if (yPos == 0) return true;
        PatternCardField pcd = patternCardFields[xPos][yPos - 1];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    private boolean checkEastColor(int xPos, int yPos, String color) {
        if (xPos == 4) return true;
        PatternCardField pcd = patternCardFields[xPos + 1][yPos];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    private boolean checkWestColor(int xPos, int yPos, String color) {
        if (xPos == 0) return true;
        PatternCardField pcd = patternCardFields[xPos - 1][yPos];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    public boolean checkSidesEyes(int xPos, int yPos, int eyes) {
        return checkSouthEyes(xPos, yPos, eyes) && checkEastEyes(xPos, yPos, eyes) && checkNorthEyes(xPos, yPos, eyes) && checkWestEyes(xPos, yPos, eyes);
    }

    private boolean checkSouthEyes(int xPos, int yPos, int eyes) {
        if (yPos == 3) return true;
        PatternCardField pcd = patternCardFields[xPos][yPos + 1];
        if (pcd.hasValue()) return !(pcd.getValue() == eyes);
        return true;
    }

    private boolean checkNorthEyes(int xPos, int yPos, int eyes) {
        if (yPos == 0) return true;
        PatternCardField pcd = patternCardFields[xPos][yPos - 1];
        if (pcd.hasValue()) return !(pcd.getValue() == eyes);
        return true;
    }

    private boolean checkEastEyes(int xPos, int yPos, int eyes) {
        if (xPos == 4) return true;
        PatternCardField pcd = patternCardFields[xPos + 1][yPos];
        if (pcd.hasValue()) return !(pcd.getValue() == eyes);
        return true;
    }

    private boolean checkWestEyes(int xPos, int yPos, int eyes) {
        if (xPos == 0) return true;
        PatternCardField pcd = patternCardFields[xPos - 1][yPos];
        if (pcd.hasValue()) return !(pcd.getValue() == eyes);
        return true;
    }

    private void generateRandomDifficulty() {
        setDifficulty(rnd.nextInt(6) + 1);
    }

    private void generateRandomPatternCardField() {
        if (rnd.nextBoolean()) {
            addRandomEyes();
        } else {
            addRandomColor();
        }
    }

    private void addRandomEyes() {
        int xPos = rnd.nextInt(5);
        int yPos = rnd.nextInt(4);
        int eyes = rnd.nextInt(6) + 1;
        if (!patternCardFields[xPos][yPos].hasFieldAttributes() && checkSidesEyes(xPos, yPos, eyes)) {
            patternCardFields[xPos][yPos].setValue(eyes);
        } else {
            addRandomEyes();
        }
    }

    private void addRandomColor() {
        int xPos = rnd.nextInt(5);
        int yPos = rnd.nextInt(4);
        String color = colors.get(rnd.nextInt(colors.size()));
        if (!patternCardFields[xPos][yPos].hasFieldAttributes() && checkSidesColor(xPos, yPos, color)) {
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
}
