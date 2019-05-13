package nl.avans.sagrada.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.PublicObjectiveCard;

public class PublicObjectiveCardView extends CardView {
    private PublicObjectiveCard publicObjectiveCard;
    private PlayerController playerController;

    /**
     * Filled constructor
     *
     * @param playerController PlayerController
     */
    public PublicObjectiveCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
        getStylesheets().add(css);
        setId("publicObjectiveCard");
    }

    /**
     * Returns the public objective card instance that is linked to this view.
     *
     * @return public objective card instance
     */
    public PublicObjectiveCard getPublicObjectiveCard() {
        return publicObjectiveCard;
    }

    /**
     * Links a public objective card instance to the view.
     *
     * @param publicObjectiveCard PublicObjectiveCard
     */
    public void setPublicObjectiveCard(PublicObjectiveCard publicObjectiveCard) {
        this.publicObjectiveCard = publicObjectiveCard;
    }

    /**
     * Displays the image for the public objective card.
     *
     * @param url String
     */
    public void showImage(String url) {
        Image image = new Image(url);
        StackPane imagePane = new StackPane();
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight((CardView.CARD_HEIGHT / 1.25));
        imageView.setFitWidth(CardView.CARD_WIDTH + 1);
        imageView.setPreserveRatio(false);
        imagePane.getChildren().add(imageView);
        imagePane.setPrefSize(CardView.CARD_WIDTH, (CardView.CARD_HEIGHT / 1.25));
        setCenter(imagePane);
    }

    /**
     * Displays the text at the bottom of the card for the public objective card.
     */
    public void showText() {
        Text scorePoints = new Text(Integer.toString(publicObjectiveCard.getPoints()));
        Text description = new Text(publicObjectiveCard.getDescription());
        scorePoints.setFont(Main.SAGRADA_FONT);
        description.setFont(Main.SAGRADA_FONT);
        description.setTextAlignment(TextAlignment.CENTER);
        scorePoints.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth((CardView.CARD_WIDTH / 1.2));
        BorderPane textPane = new BorderPane();
        StackPane scorePane = new StackPane();
        StackPane descriptionPane = new StackPane();
        scorePane.getChildren().add(scorePoints);
        descriptionPane.getChildren().add(description);
        textPane.setLeft(scorePane);
        textPane.setCenter(descriptionPane);
        scorePane.setPrefWidth((CardView.CARD_WIDTH / 6));
        textPane.setPrefSize((CardView.CARD_WIDTH / 1.2), (CardView.CARD_HEIGHT / 4));
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        textPane.getStylesheets().add(css);
        textPane.setId("ObjectiveCardDescription");
        setBottom(textPane);
    }

    @Override
    public void render() {
        getChildren().clear();
        showImage(publicObjectiveCard.getImagePath());
        showText();
    }
}
