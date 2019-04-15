package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.PatternCard;

public class PatternCardView extends TilePane {
    private final int cardWidth = 5;
    private final int cardHeight = 4;
    private PatternCard patternCard;
    private PatternCardFieldView[][] card;

    public PatternCardView() {
        setPadding(new Insets(10, 0, 0, 10));
        setPrefSize(310, 230);
        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        patternCard = new PatternCard();
        card = new PatternCardFieldView[cardWidth][cardHeight];
        makeCard();
    }

    public void addColor(int x, int y) {
        card[x][y].addColor();
    }

    public void addEyes(int x, int y) {
        card[x][y].addEyes();
    }


    public void setEyes(int eyes, int x, int y) {
        card[x][y].setEyes(eyes);
    }

    public void setColor(String color, int x, int y) {
        card[x][y].setColor(color);

    }

    private void makeCard() {
        for (int y = 0; y < cardHeight; y++) {
            for (int x = 0; x < cardWidth; x++) {
                PatternCardFieldView patternCardFieldView = new PatternCardFieldView(patternCard);
                card[x][y] = patternCardFieldView;
                patternCardFieldView.setY(y);
                patternCardFieldView.setX(x);
                patternCardFieldView.initPatternCardField();
                Pane paddingPane = new Pane();
                paddingPane.setPadding(new Insets(5, 5, 5, 5));
                paddingPane.getChildren().add(patternCardFieldView);
                getChildren().add(paddingPane);
            }
        }
    }

    public boolean hasFieldAttributes(int x, int y) {
        return card[x][y].hasAttributes();
    }
}
