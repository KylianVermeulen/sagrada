package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class PatternCardFieldView extends StackPane {
    private int xPos;
    private int yPos;
    private PatternCardField patternCardField;
    private PatternCard patternCard;
    private ArrayList<Image> images;
    private Text text;

    public PatternCardFieldView(PatternCard patternCard) {
        this.patternCard = patternCard;
        images = new ArrayList<Image>();
        setPrefSize(50, 50);
        diceEyesArray();
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.setOnMouseClicked(e -> onClick());
    }

    /**
     * Method when the PatternCardField is clicked
     */
    private void onClick() {
        System.out.println("x: " + xPos + " y: " + yPos);
    }

    /**
     * Adds dice eye images to the image array
     */
    private void diceEyesArray() {
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/1.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/2.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/3.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/4.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/5.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/6.png")));
    }

    /**
     * Connects the PatternCardField to PatternCardFieldView with the same positions
     */
    public void initPatternCardField() {
        patternCardField = patternCard.getPatternCardField(xPos, yPos);
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

    /**
     * Adds dice view WIP
     */
    public void addDie() {
        // WIP
    }

    /**
     * Sets the Y value
     *
     * @param y int
     */
    public void setY(int y) {
        this.yPos = y;
    }

    /**
     * Sets the X value
     *
     * @param x int
     */
    public void setX(int x) {
        this.xPos = x;
    }

    /**
     * Sets the PatternCardField color
     *
     * @param color String
     */
    public void setColor(String color) {
        patternCardField.setColor(color);
    }

    /**
     * Sets the PatternCardField eyes
     *
     * @param eyes int
     */
    public void setEyes(int eyes) {
        patternCardField.setEyes(eyes);
    }

    public boolean hasAttributes() {
        return patternCardField.hasAttributes();
    }

}