package nl.avans.sagrada.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;

import java.util.ArrayList;

public class PatternCardFieldView extends StackPane {
    private PatternCard patternCard;
    private PatternCardField patternCardField;
    private PlayerController playerController;

    private ArrayList<Image> images;

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    /**
     * Partial constructor
     *
     * @param playerController PlayerController
     */
    public PatternCardFieldView(PlayerController playerController) {
        this.playerController = playerController;
        setPrefSize(WIDTH, HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        setOnMouseClicked(e -> onClick());

        images = new ArrayList<Image>();
        diceEyesArray();
    }

    /**
     * Set PatternCard to PatternCardFieldView
     * @param patternCard PatternCard
     */
    public void setPatternCard(PatternCard patternCard) {
        this.patternCard = patternCard;
    }

    /**
     * Set PatternCardField to PatternCardFieldView
     * @param patternCardField PatternCardField
     */
    public void setPatternCardField(PatternCardField patternCardField) {
        this.patternCardField = patternCardField;
    }

    /**
     * Renders the view with all the information
     */
    public void render() {
        getChildren().clear();
        if (patternCardField.hasColor()) {
            addColor();
        }
        if (patternCardField.hasValue()) {
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
        getChildren().add(new ImageView(images.get(patternCardField.getValue() - 1)));
    }

    /**
     * Changes the background color of the PatternCardFieldView
     */
    public void addColor() {
        setBackground(new Background(new BackgroundFill(patternCardField.getColor(), null, null)));
    }
}