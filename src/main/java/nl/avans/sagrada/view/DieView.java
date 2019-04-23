package nl.avans.sagrada.view;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.GameDie;

import java.util.ArrayList;

public class DieView extends StackPane {

    private ArrayList<ImageView> images;
    private GameDie gameDie;

    private static final int WIDTH = 35;
    private static final int HEIGHT = 35;

    /**
     * Full constructor
     */
    public DieView() {
        images = new ArrayList<ImageView>();
        setPrefSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        diceEyesArray();
        resizeImages();


    }

    /**
     * Renders the visuals
     */
    public void render() {
        if (gameDie.hasColor()) {
            setColor(gameDie.getColor());
        }
        if (gameDie.hasEyes()) {
            setEyes(gameDie.getEyes());
        }
    }

    public void makeMovable() {
        setOnDragDetected(e -> {
            this.startFullDrag();
        });


        this.setOnMouseReleased(e -> {

        });
    }

    /**
     * Gets the eyes value out of gameDie and shows the visual of the value
     *
     * @param eyes int
     */
    public void setEyes(int eyes) {
        getChildren().add(images.get(eyes - 1));
    }


    /**
     * Gets the color value out of gameDie and shows the visual of the value
     *
     * @param color String
     */
    private void setColor(Color color) {
        setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    /**
     * Sets the gameDie of the View
     *
     * @param gameDie GameDie
     */
    public void setGameDie(GameDie gameDie) {
        this.gameDie = gameDie;
    }

    public GameDie getGameDie() {
        return this.gameDie;
    }

    /**
     * Resized the images to the WIDTH and HEIGHT of the pane
     */
    private void resizeImages() {
        for (ImageView image : images) {
            image.setFitHeight(HEIGHT);
            image.setFitWidth(WIDTH);
        }
    }

    /**
     * Initializes the values of the image array
     */
    private void diceEyesArray() {
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/1.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/2.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/3.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/4.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/5.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/6.png"))));
    }
}
