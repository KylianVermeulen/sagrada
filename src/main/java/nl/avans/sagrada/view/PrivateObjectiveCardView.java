package nl.avans.sagrada.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.controller.PlayerController;

public class PrivateObjectiveCardView extends CardView {
    private final static int SQUAREWIDTH = 30;
    private final static int SQUAREHEIGHT = 30;

    private PlayerController playerController;

    public PrivateObjectiveCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;

        render();
    }

    @Override
    public void render() {
        getChildren().clear();
        showImage(null);
        showText();
    }

    public void showText() {
        Text scorePoints = new Text("#");
        Text description = new Text("TINTEN *GETCOLOR* - Persoonlijk \n Som van waardes op *getColor* \n dobbelstenen");
        scorePoints.setFont(new Font("Segoe Script", 8));
        description.setFont(new Font("Segoe Script", 8));
        description.setTextAlignment(TextAlignment.CENTER);
        BorderPane textPane = new BorderPane();
        StackPane scorePane = new StackPane();
        scorePane.getChildren().add(scorePoints);
        StackPane descriptionPane = new StackPane();
        descriptionPane.getChildren().add(description);
        textPane.setLeft(scorePane);
        textPane.setCenter(descriptionPane);
        scorePane.setPrefWidth(getViewWidth() / 6);

        setBottom(textPane);

    }

    private void showImage(String url) {
        Image image = new Image(url);
        StackPane imagePane = new StackPane();
        ImageView imageView = new ImageView();

    }

}
