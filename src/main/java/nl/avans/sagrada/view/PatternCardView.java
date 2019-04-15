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
    public PatternCardView(){
        setPadding(new Insets(10,0,0,10));
        setPrefSize(310,230);
        setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

        patternCard = new PatternCard();
        card = new PatternCardFieldView[cardWidth][cardHeight];
        makeCard();
    }

    private void makeCard() {
        for(int y = 0; y < cardHeight; y++){
            for(int x = 0; x < cardWidth; x++){
                PatternCardFieldView patternCardFieldView = new PatternCardFieldView(patternCard);
                card[x][y] = patternCardFieldView;
                patternCardFieldView.setY(y);
                patternCardFieldView.setX(x);

                Pane paddingPane = new Pane();
                paddingPane.setPadding(new Insets(5,5,5,5));
                paddingPane.getChildren().add(patternCardFieldView);
                getChildren().add(paddingPane);
            }
        }
    }
}
