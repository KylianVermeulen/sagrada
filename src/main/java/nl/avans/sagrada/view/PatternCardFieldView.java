package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

    /**
     * Partial constructor
     *
     * @param playerController PlayerController
     */
    public PatternCardFieldView(PlayerController playerController) {
        this.playerController = playerController;
        setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
        setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        images = new ArrayList<ImageView>();
        diceEyesArray();
        resizeImages();
        setOnMouseDragReleased(event -> {
            System.out.println("runs");
            try {
                GameDie gameDie = ((GameDie) ((DieView) event.getGestureSource()).getGameDie());
                patternCardField.placeDie(gameDie);
                this.render();
            } catch (Exception e) {
                System.out.println("no");
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
     * Set PatternCard to PatternCardFieldView
     *
     * @param patternCard PatternCard
     */
    public void setPatternCard(PatternCard patternCard) {
        this.patternCard = patternCard;
    }

    /**
     * Set PatternCardField to PatternCardFieldView
     *
     * @param patternCardField PatternCardField
     */
    public void setPatternCardField(PatternCardField patternCardField) {
        this.patternCardField = patternCardField;
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
            DieView dieView = new DieView();
            dieView.resize(35,35);
            dieView.setGameDie(patternCardField.getDie());
            dieView.render();
            getChildren().clear();
            getChildren().add(dieView);
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
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/1.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/2.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/3.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/4.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/5.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/6.png"))));
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
        setBackground(new Background(new BackgroundFill(patternCardField.getFXColor(), null, null)));
    }
}
