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
    private int x;
    private int y;
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
    }

    private void diceEyesArray() {
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/1.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/2.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/3.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/4.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/5.png")));
        images.add(new Image(getClass().getResourceAsStream("/images/dice eyes/6.png")));
    }

    public void initPatternCardField() {
        patternCardField = patternCard.getPatternCardField(x, y);
    }

    public void addEyes() {
        getChildren().add(new ImageView(images.get(patternCardField.getEyes() - 1)));
    }

    public void addColor() {
        setBackground(new Background(new BackgroundFill(patternCardField.getColor(), null, null)));
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setColor(String color) {
        patternCardField.setColor(color);
    }

    public void setEyes(int eyes) {
        patternCardField.setEyes(eyes);
    }

    public boolean hasAttributes() {
        return patternCardField.hasAttributes();
    }

    public PatternCardField getPatternCardField() {
        return this.patternCardField;
    }
}