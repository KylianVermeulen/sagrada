package nl.avans.sagrada.view;

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
        patternCardView = new PatternCardView();
        rootPane.getChildren().add(patternCardView);
        generateRandomCard();
        setRoot(rootPane);
    }


    public void generateRandomCard() {
        for (int i = 0; i < 12; i++) {
            generateRandomPatternCardField();
        }
    }

    private void generateRandomPatternCardField() {
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("blue");
        colors.add("green");
        colors.add("purple");
        colors.add("yellow");
        colors.add("orange");
        int xPos = rnd.nextInt(5);
        int yPos = rnd.nextInt(4);
        int eyes = rnd.nextInt(6) + 1;

        if (rnd.nextBoolean()) {
            if (!patternCardView.hasFieldAttributes(xPos, yPos)) {
                patternCardView.setEyes(eyes, xPos, yPos);
                patternCardView.addEyes(xPos, yPos);
            } else {
                generateRandomPatternCardField();
            }
        } else {
            if (!patternCardView.hasFieldAttributes(xPos, yPos)) {
                patternCardView.setColor(colors.get(rnd.nextInt(5)), xPos, yPos);
                patternCardView.addColor(xPos, yPos);
            } else {
                generateRandomPatternCardField();
            }
        }
    }
}
