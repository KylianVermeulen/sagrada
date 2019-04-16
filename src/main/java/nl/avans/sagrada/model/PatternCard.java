package nl.avans.sagrada.model;

import nl.avans.sagrada.view.PatternCardView;

import java.util.ArrayList;
import java.util.Random;

import static nl.avans.sagrada.Main.CARD_HEIGHT;
import static nl.avans.sagrada.Main.CARD_WIDTH;

public class PatternCard {
    private int id;
    private int difficulty;
    private ArrayList<String> colors;
    private boolean standard;
    private PatternCardField[][] patterncardFields;
    private Random rnd;
    private PatternCardView randomPatternCardView;

    /**
     * Empty constructor
     */
    public PatternCard() {
        rnd = new Random();
        patterncardFields = new PatternCardField[CARD_WIDTH][CARD_HEIGHT];
        makeCard();
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
    public PatternCardField[][] getPatterncardFields() {
        return patterncardFields;
    }

    /**
     * Set patternCardFields to PatternCard
     *
     * @param patterncardFields PatternCardField[]
     */
    public void setPatterncardFields(PatternCardField[][] patterncardFields) {
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

    public PatternCardView getRandomPatternCardView() {
        return this.randomPatternCardView;
    }

    public void generateRandomCard() {
        randomPatternCardView = new PatternCardView();
        makeColors();
        for (int i = 0; i < randomPatternCardView.getPatternCard().getDifficulty() * 2; i++) {
            generateRandomPatternCardField();
        }
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
        if (!randomPatternCardView.hasFieldAttributes(xPos, yPos) && randomPatternCardView.checkSidesEyes(xPos, yPos, eyes)) {
            randomPatternCardView.setEyes(eyes, xPos, yPos);
            randomPatternCardView.addEyes(xPos, yPos);
        } else {
            addRandomEyes();
        }
    }

    private void addRandomColor() {
        int xPos = rnd.nextInt(5);
        int yPos = rnd.nextInt(4);
        String color = colors.get(rnd.nextInt(colors.size()));
        if (!randomPatternCardView.hasFieldAttributes(xPos, yPos) && randomPatternCardView.checkSidesColor(xPos, yPos, color)) {
            randomPatternCardView.setColor(color, xPos, yPos);
            randomPatternCardView.addColor(xPos, yPos);
        } else {
            addRandomColor();
        }
    }

    private void makeColors() {
        colors = new ArrayList<String>();
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        colors.add("purple");
        colors.add("red");
    }

    private void makeCard() {
        setDifficulty(rnd.nextInt(6) + 1);
        for (int y = 0; y < CARD_HEIGHT; y++) {
            for (int x = 0; x < CARD_WIDTH; x++) {
                PatternCardField patternCardField = new PatternCardField(this);
                patterncardFields[x][y] = patternCardField;
                patternCardField.setXPos(x);
                patternCardField.setYPos(y);
            }
        }
    }

    public PatternCardField getPatternCardField(int x, int y) {
        return patterncardFields[x][y];
    }

    public boolean checkSidesColor(int xPos, int yPos, String color) {
        return checkSouthColor(xPos, yPos, color) && checkEastColor(xPos, yPos, color) && checkNorthColor(xPos, yPos, color) && checkWestColor(xPos, yPos, color);
    }

    private boolean checkSouthColor(int xPos, int yPos, String color) {
        if (yPos == 3) return true;
        PatternCardField pcd = patterncardFields[xPos][yPos + 1];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    private boolean checkNorthColor(int xPos, int yPos, String color) {
        if (yPos == 0) return true;
        PatternCardField pcd = patterncardFields[xPos][yPos - 1];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    private boolean checkEastColor(int xPos, int yPos, String color) {
        if (xPos == 4) return true;
        PatternCardField pcd = patterncardFields[xPos + 1][yPos];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    private boolean checkWestColor(int xPos, int yPos, String color) {
        if (xPos == 0) return true;
        PatternCardField pcd = patterncardFields[xPos - 1][yPos];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    public boolean checkSidesEyes(int xPos, int yPos, int eyes) {
        return checkSouthEyes(xPos, yPos, eyes) && checkEastEyes(xPos, yPos, eyes) && checkNorthEyes(xPos, yPos, eyes) && checkWestEyes(xPos, yPos, eyes);
    }

    private boolean checkSouthEyes(int xPos, int yPos, int eyes) {
        if (yPos == 3) return true;
        PatternCardField pcd = patterncardFields[xPos][yPos + 1];
        if (pcd.hasEyes()) return !(pcd.getEyes() == eyes);
        return true;
    }

    private boolean checkNorthEyes(int xPos, int yPos, int eyes) {
        if (yPos == 0) return true;
        PatternCardField pcd = patterncardFields[xPos][yPos - 1];
        if (pcd.hasEyes()) return !(pcd.getEyes() == eyes);
        return true;
    }

    private boolean checkEastEyes(int xPos, int yPos, int eyes) {
        if (xPos == 4) return true;
        PatternCardField pcd = patterncardFields[xPos + 1][yPos];
        if (pcd.hasEyes()) return !(pcd.getEyes() == eyes);
        return true;
    }

    private boolean checkWestEyes(int xPos, int yPos, int eyes) {
        if (xPos == 0) return true;
        PatternCardField pcd = patterncardFields[xPos - 1][yPos];
        if (pcd.hasEyes()) return !(pcd.getEyes() == eyes);
        return true;
    }
}
