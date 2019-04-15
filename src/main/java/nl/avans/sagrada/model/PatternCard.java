package nl.avans.sagrada.model;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.view.PatternCardFieldView;

import java.util.ArrayList;
import java.util.Random;

public class PatternCard {
    private final int cardWidth = 5;
    private final int cardHeight = 4;
    private int id;
    private int difficulty;
    private boolean standard;
    private PatternCardField[][] card;
    private Random rnd;

    public PatternCard() {
        rnd = new Random();
        card = new PatternCardField[cardWidth][cardHeight];
        makeCard();
    }

    public PatternCardField getBestLocationForDice(GameDie die) {
        return null;
    }

    public void generateRandomCard() {

    }

    public void setDifficulty(int i){
        this.difficulty = i;
    }

    public int getDifficulty(){
        return this.difficulty;
    }


    private void makeCard() {
        setDifficulty(rnd.nextInt(6) + 1);
        for (int y = 0; y < cardHeight; y++) {
            for (int x = 0; x < cardWidth; x++) {
                PatternCardField patternCardField = new PatternCardField();
                card[x][y] = patternCardField;
            }
        }
    }

    public PatternCardField getPatternCardField(int x, int y) {
        return card[x][y];
    }
}
