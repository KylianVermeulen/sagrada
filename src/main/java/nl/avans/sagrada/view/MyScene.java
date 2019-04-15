package nl.avans.sagrada.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.view.PatternCardView;

public class MyScene extends Scene {

    private PatternCard patternCard;
    private PatternCardView patternCardView;

    public MyScene() {
        super(new Pane());
        patternCard = new PatternCard();
        patternCardView = new PatternCardView();
        TilePane rootPane = new TilePane();
        rootPane.getChildren().add(patternCardView);
        setRoot(rootPane);
    }

}
