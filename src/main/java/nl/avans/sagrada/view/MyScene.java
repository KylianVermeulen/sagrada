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
        patternCardView = new PatternCardView();
        patternCardView.setEyes(4,0,3);
        patternCardView.setEyes(3,3,3);
        patternCardView.setEyes(2,1,3);
        patternCardView.setEyes(6,3,2);
        patternCardView.setEyes(1,0,2);
        patternCardView.setColor("blue", 3,0);
        patternCardView.setColor("purple",1,3);
        patternCardView.setColor("orange",2,3);
        patternCardView.setColor("yellow",1,2);
        TilePane rootPane = new TilePane();
        rootPane.getChildren().add(patternCardView);
        setRoot(rootPane);
    }

}
