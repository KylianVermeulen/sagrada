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
    ArrayList<String> colors;

    public MyScene() {
        super(new Pane());
        rnd = new Random();
        TilePane rootPane = new TilePane();
        patternCardView = new PatternCardView();
        rootPane.getChildren().add(patternCardView);
        generateRandomCard();
        setRoot(rootPane);
    }

//patternCardView.getPatternCard().getDifficulty() * 2
    public void generateRandomCard() {
        makeColors();
        for (int i = 0; i < 10; i++) {
            generateRandomPatternCardField();
        }
    }

    private void makeColors() {
        colors = new ArrayList<String>();
        colors.add("blue");
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
        if (!patternCardView.hasFieldAttributes(xPos, yPos)) {
            patternCardView.setEyes(eyes, xPos, yPos);
            patternCardView.addEyes(xPos, yPos);
        } else {
            addRandomEyes();
        }
    }

    private void addRandomColor() {

        int xPos = rnd.nextInt(5);
        int yPos = rnd.nextInt(4);
        String color = colors.get(rnd.nextInt(colors.size()));
        if (!patternCardView.hasFieldAttributes(xPos, yPos) && patternCardView.checkSides(xPos, yPos, color)) {
            patternCardView.setColor(color, xPos, yPos);
            patternCardView.addColor(xPos, yPos);
        } else {
            addRandomColor();
        }
    }
}
