package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.view.PatternCardView;

import java.util.ArrayList;
import java.util.Random;

public class MyScene extends Scene {
    private Random rnd;
    private PatternCardView patternCardView;

    public MyScene() {
        super(new Pane());
        rnd = new Random();
        TilePane rootPane = new TilePane();
        PatternCard patternCard1 = new PatternCard();
        PatternCard patternCard2 = new PatternCard();
        PatternCard patternCard3 = new PatternCard();
        PatternCard patternCard4 = new PatternCard();
        PatternCard patternCard5 = new PatternCard();
        PatternCard patternCard6 = new PatternCard();
        PatternCard patternCard7 = new PatternCard();
        PatternCard patternCard8 = new PatternCard();

        patternCard1.generateRandomCard();
        patternCard2.generateRandomCard();
        patternCard3.generateRandomCard();
        patternCard4.generateRandomCard();
        patternCard5.generateRandomCard();
        patternCard6.generateRandomCard();
        patternCard7.generateRandomCard();
        patternCard8.generateRandomCard();

        rootPane.getChildren().addAll(patternCard1.getRandomPatternCardView(), patternCard2.getRandomPatternCardView(),
                patternCard3.getRandomPatternCardView(), patternCard4.getRandomPatternCardView(),
                patternCard5.getRandomPatternCardView(), patternCard6.getRandomPatternCardView(),
                patternCard7.getRandomPatternCardView(), patternCard8.getRandomPatternCardView());
        setRoot(rootPane);
    }
}
