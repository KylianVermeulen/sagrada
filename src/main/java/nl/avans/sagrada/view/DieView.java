package nl.avans.sagrada.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.GameDie;

import java.util.ArrayList;

public class DieView extends Pane {

    private ArrayList<ImageView> images;
    private GameDie gameDie;

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    public DieView() {
        images = new ArrayList<ImageView>();
        setPrefSize(WIDTH, HEIGHT);
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        diceEyesArray();
        resizeImages();
    }

    public void render() {
        if (gameDie.hasColor()) {
            setColor(gameDie.getColor());
        }
        if (gameDie.hasEyes()) {
            setEyes(gameDie.getEyes());
        }
    }

    public void setEyes(int eyes) {
        getChildren().add(images.get(eyes - 1));
    }


    private void setColor(Color color) {
        setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    public void setGameDie(GameDie gameDie) {
        this.gameDie = gameDie;
    }

    private void resizeImages() {
        for (ImageView x : images) {
            x.setFitHeight(HEIGHT);
            x.setFitWidth(WIDTH);
        }
    }

    private void diceEyesArray() {
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/1.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/2.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/3.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/4.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/5.png"))));
        images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/diceeyes/6.png"))));
    }
}
