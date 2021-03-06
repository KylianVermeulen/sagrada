package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class PatternCardFieldView extends StackPane implements ViewInterface {
    private final int FIELD_WIDTH = 40;
    private final int FIELD_HEIGHT = 40;
    private PatternCard patternCard;
    private PatternCardField patternCardField;
    private PlayerController playerController;
    private ArrayList<ImageView> images;
    private boolean hasBestHighlight;
    private boolean hasHighlight;

    /**
     * Partial constructor
     *
     * @param playerController PlayerController
     */
    public PatternCardFieldView(PlayerController playerController) {
        this.playerController = playerController;
        hasBestHighlight = false;
        hasHighlight = false;
        setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
        setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        images = new ArrayList<ImageView>();
        diceEyesArray();
        resizeImages();
        setOnMouseDragReleased(event -> {
            try {
                MouseEvent dragEvent = (MouseEvent) event;
                GameDie gameDie = ((GameDie) ((DieView) event.getGestureSource()).getGameDie());
                playerController.actionPlaceDie(patternCard, patternCardField, gameDie, dragEvent);
                this.render();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        addHover();
    }

    /**
     * Adds hover effect
     */
    private void addHover() {
        setOnMouseDragEntered(e -> {
            this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        });
        setOnMouseDragExited(e -> {
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            this.render();
        });
    }

    /**
     * Add a pane with orange red border to the StackPane.
     */
    public void addBestHighlight() {
        hasBestHighlight = true;
        Pane pane = new Pane();
        pane.setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
        getChildren().add(pane);
    }

    /**
     * Add a pane with gray border to the StackPane.
     */
    public void addHighlight() {
        hasHighlight = true;
        Pane pane = new Pane();
        pane.setBorder(new Border(new BorderStroke(Color.SLATEGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK)));
        getChildren().add(pane);
    }

    /**
     * Remove the pane with border from the StackPane.
     */
    public void removeHighlight() {
        hasBestHighlight = false;
        hasHighlight = false;
        render();
    }

    /**
     * Set PatternCard to PatternCardFieldView
     *
     * @param patternCard PatternCard
     */
    public void setPatternCard(PatternCard patternCard) {
        this.patternCard = patternCard;
    }

    /**
     * Renders the view with all the information
     */
    @Override
    public void render() {
        getChildren().clear();
        if (patternCardField.hasColor()) {
            addColor();
        }
        if (patternCardField.hasValue()) {
            addEyes();
        }
        if (patternCardField.hasDie()) {
            DieView dieView = new DieView(playerController);
            dieView.resize(35, 35);
            dieView.setGameDie(patternCardField.getDie());
            dieView.render();
            getChildren().clear();
            getChildren().add(dieView);
        }
        if (hasHighlight) {
            addHighlight();
        }
        if (hasBestHighlight) {
            addBestHighlight();
        }
    }

    /**
     * Resized the images 5px smaller then the pane to prevent it from resizing
     */
    private void resizeImages() {
        for (ImageView image : images) {
            image.setFitHeight(FIELD_HEIGHT - 5);
            image.setFitWidth(FIELD_WIDTH - 5);
        }
    }

    /**
     * Adds dice eye images to the image array
     */
    private void diceEyesArray() {
        images.add(
                new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/1.png"))));
        images.add(
                new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/2.png"))));
        images.add(
                new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/3.png"))));
        images.add(
                new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/4.png"))));
        images.add(
                new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/5.png"))));
        images.add(
                new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/6.png"))));
    }

    /**
     * Adds dice eye image to the PatternCardFieldView
     */
    public void addEyes() {
        getChildren().add(images.get(patternCardField.getValue() - 1));
    }

    /**
     * Changes the background color of the PatternCardFieldView
     */
    public void addColor() {
        setBackground(
                new Background(new BackgroundFill(patternCardField.getFXColor(), null, null)));
    }

    /**
     * Returns the patterncardfield that the view contains
     *
     * @return PatternCardField
     */
    public PatternCardField getPatternCardField() {
        return patternCardField;
    }

    /**
     * Set PatternCardField to PatternCardFieldView
     *
     * @param patternCardField PatternCardField
     */
    public void setPatternCardField(PatternCardField patternCardField) {
        this.patternCardField = patternCardField;
    }
}
