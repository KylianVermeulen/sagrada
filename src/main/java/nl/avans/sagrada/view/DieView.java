package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class DieView extends Pane implements ViewInterface {

    private final int DIE_WIDTH = 40;
    private final int DIE_HEIGHT = 40;
    private ArrayList<ImageView> images;
    private GameDie gameDie;

    /**
     * Full constructor
     */
    public DieView() {
        images = new ArrayList<ImageView>();
        setPrefSize(DIE_WIDTH, DIE_HEIGHT);
        setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        new BorderWidths(3))));
        diceEyesArray();
        resizeImages();
    }

    public DieView(GameDie gameDie) {
        this.gameDie = gameDie;
        images = new ArrayList<ImageView>();
        setPrefSize(WIDTH, HEIGHT);
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        diceEyesArray();
        resizeImages();
    }

    /**
     * Renders the visuals
     */
    @Override
    public void render() {
        if (gameDie.hasColor()) {
            setColor(gameDie.getFXColor());
        }
        if (gameDie.hasEyes()) {
            setEyes(gameDie.getEyes());
        }
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

    /**
     * Resized the images to the WIDTH and HEIGHT of the pane
     */
    private void resizeImages() {
        for (ImageView image : images) {
            image.setFitHeight(DIE_HEIGHT);
            image.setFitWidth(DIE_WIDTH);
        }
    }

    /**
     * Resized the images to the given width and height
     *
     * @param height int
     * @param width int
     */
    public void resizeImages(int height, int width) {
        for (ImageView image : images) {
            image.setFitHeight(height);
            image.setFitWidth(width);
        }
    }

    /**
     * Initializes the values of the image array
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
}
