package nl.avans.sagrada.model;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.view.PatternCardFieldView;

import java.util.ArrayList;
import java.util.Random;

import static nl.avans.sagrada.Main.CARD_HEIGHT;
import static nl.avans.sagrada.Main.CARD_WIDTH;

public class PatternCard {
    private int id;
    private int difficulty;
    private boolean standard;
    private PatternCardField[][] card;
    private Random rnd;

    public PatternCard() {
        rnd = new Random();
        card = new PatternCardField[CARD_WIDTH][CARD_HEIGHT];
        makeCard();
    }

    public PatternCardField getBestLocationForDice(GameDie die) {
        return null;
    }

    public void generateRandomCard() {

    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(int i) {
        this.difficulty = i;
    }

    private void makeCard() {
        setDifficulty(rnd.nextInt(6) + 1);
        for (int y = 0; y < CARD_HEIGHT; y++) {
            for (int x = 0; x < CARD_WIDTH; x++) {
                PatternCardField patternCardField = new PatternCardField(this);
                card[x][y] = patternCardField;
                patternCardField.setXPos(x);
                patternCardField.setYPos(y);
            }
        }
    }

    public PatternCardField getPatternCardField(int x, int y) {
        return card[x][y];
    }

    public boolean checkSides(int xPos, int yPos, String color) {
        return checkSouth(xPos, yPos, color) && checkEast(xPos, yPos, color) && checkNorth(xPos, yPos, color) && checkWest(xPos, yPos, color);
    }

    public boolean checkSouth(int xPos, int yPos, String color) {
        if (yPos == 3) return true;
        PatternCardField pcd = card[xPos][yPos + 1];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    public boolean checkNorth(int xPos, int yPos, String color) {
        if (yPos == 0) return true;
        PatternCardField pcd = card[xPos][yPos - 1];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    public boolean checkEast(int xPos, int yPos, String color) {
        if (xPos == 4) return true;
        PatternCardField pcd = card[xPos + 1][yPos];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

    public boolean checkWest(int xPos, int yPos, String color) {
        if (xPos == 0) return true;
        PatternCardField pcd = card[xPos - 1][yPos];
        if (pcd.hasColor()) return !pcd.getStringColor().equals(color);
        return true;
    }

}
