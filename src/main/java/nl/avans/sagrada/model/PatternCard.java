package nl.avans.sagrada.model;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.view.PatternCardFieldView;

public class PatternCard {
    private final int cardWidth = 5;
    private final int cardHeight = 4;
    private int id;
    private int difficult;
    private boolean standard;
    private PatternCardField[][] card;

    public PatternCard() {
        card = new PatternCardField[cardWidth][cardHeight];
        makeCard();
    }

    public PatternCardField getBestLocationForDice(GameDie die) {
        return null;
    }

    public void generateRandomCard() {

    }

    private void makeCard() {
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
