package nl.avans.sagrada.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;

import java.util.ArrayList;

public class PatternCardFieldView extends StackPane {
    private PatternCard patternCard;
    private PatternCardField patternCardField;

    private ArrayList<Image> images;

    public PatternCardFieldView(PatternCard patternCard, PatternCardField patternCardField) {
        this.patternCard = patternCard;
        this.patternCardField = patternCardField;

        images = new ArrayList<Image>();
        setPrefSize(50, 50);
        diceEyesArray();

        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.setOnMouseClicked(e -> onClick());

        render();
    }

    public void render() {
        if (patternCardField.hasColor()) {
            addColor();
        }
        if (patternCardField.hasEyes()) {
            addEyes();
        }
    }

    /**
     * Method when the PatternCardField is clicked
     */
    private void onClick() {
        System.out.println("x: " + patternCardField.getxPos() + " y: " + patternCardField.getyPos());
    }

    /**
     * Adds dice eye images to the image array
     */
    private void diceEyesArray() {
        images.add(new Image(getClass().getResourceAsStream("/images/diceeyes/1.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/diceeyes/2.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/diceeyes/3.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/diceeyes/4.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/diceeyes/5.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/diceeyes/6.png")));
    }

    /**
     * Adds dice eye image to the PatternCardFieldView
     */
    public void addEyes() {
        getChildren().add(new ImageView(images.get(patternCardField.getEyes() - 1)));
    }

    /**
     * Changes the background color of the PatternCardFieldView
     */
    public void addColor() {
        setBackground(new Background(new BackgroundFill(patternCardField.getColor(), null, null)));
    }
}