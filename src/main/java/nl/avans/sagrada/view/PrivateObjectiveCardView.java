package nl.avans.sagrada.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.model.Player;

public class PrivateObjectiveCardView extends CardView {
    private Player player;

    /**
     * Filled constructor
     *
     * @param player Player
     */
    public PrivateObjectiveCardView(Player player) {
        super();
        this.player = player;
    }

    /**
     * Sets the player
     * @param player Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Renders the private-objectivecard.
     */
    @Override
    public void render() {
        getChildren().clear();
        showImage(player.getImagePath());
        showText();
    }

    /**
     * Shows privateObjectiveCard without the text
     */
    public void viewWithoutText() {
        getChildren().clear();
        showImage(player.getImagePath());
    }

    /**
     * Shows the description and the points of the private-objectivecard.
     */
    public void showText() {
        Text scorePoints = new Text("#");
        Text description = new Text("TINTEN " + player.getPrivateObjectivecardColor()
                + " - Persoonlijk \n Som van waardes op " + player.getPrivateObjectivecardColor()
                + "\n dobbelstenen");
        scorePoints.setFont(new Font("Segoe Script", 15));
        description.setFont(new Font("Segoe Script", 6));
        scorePoints.setTextAlignment(TextAlignment.CENTER);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth(CARD_WIDTH / 1.2);
        BorderPane textPane = new BorderPane();
        textPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2.5))));
        StackPane scorePane = new StackPane();
        scorePane.getChildren().add(scorePoints);
        StackPane descriptionPane = new StackPane();
        descriptionPane.getChildren().add(description);
        textPane.setLeft(scorePane);
        textPane.setCenter(descriptionPane);
        scorePane.setPrefWidth(CARD_WIDTH / 6);
        textPane.setPrefSize((CARD_WIDTH / 1.2), (CARD_HEIGHT / 4));
        textPane.setBackground(
                new Background(new BackgroundFill(Color.rgb(191, 191, 191), null, null)));
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        textPane.getStylesheets().add(css);
        textPane.setId("ObjectiveCardDescription");
        setBottom(textPane);
    }

    /**
     * shows the immage of the private-objectivecard.
     */
    private void showImage(String path) {
        Image image = new Image(path);
        StackPane imagePane = new StackPane();
        imagePane.setMaxSize(CARD_WIDTH, CARD_HEIGHT);
        imagePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2.5))));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(CARD_HEIGHT / 1.25);
        imageView.setFitWidth(CARD_WIDTH + 10);
        imageView.setPreserveRatio(false);
        imagePane.getChildren().add(imageView);
        imagePane.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        setCenter(imagePane);
    }
}
