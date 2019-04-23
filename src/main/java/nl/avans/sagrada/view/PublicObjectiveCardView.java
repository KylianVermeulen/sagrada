package nl.avans.sagrada.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.PublicObjectiveCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class PublicObjectiveCardView extends CardView {
    private PublicObjectiveCard publicObjectiveCard;
    private PlayerController playerController;
    
    public PublicObjectiveCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;
    }
    
    public PublicObjectiveCard getPublicObjectiveCard() {
        return publicObjectiveCard;
    }
    
    public void setPublicObjectiveCard(PublicObjectiveCard publicObjectiveCard) {
        this.publicObjectiveCard = publicObjectiveCard;
    }
    
    public void showImage(String url) {
        Image image = new Image(url);
        System.out.println(url);
        StackPane imagePane = new StackPane();
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight((getViewHeight()/1.25));
        imageView.setFitWidth(getViewWidth() + 1);
        imageView.setPreserveRatio(false);
        imagePane.getChildren().add(imageView);
        imagePane.setPrefSize(getViewWidth(), (getViewHeight()/1.25));
        setCenter(imagePane);
    }
    
    public void showText() {
        Text scorePoints = new Text(Integer.toString(publicObjectiveCard.getSeqnr()));
        System.out.println(Integer.toString(publicObjectiveCard.getSeqnr()));
        Text description = new Text(publicObjectiveCard.getDescription());
        System.out.println(publicObjectiveCard.getDescription());
        scorePoints.setFont(Main.SAGRADA_FONT);
        description.setFont(Main.SAGRADA_FONT);
        description.setTextAlignment(TextAlignment.CENTER);
        scorePoints.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth((getViewWidth()/1.2));
        BorderPane textPane = new BorderPane();
        StackPane scorePane = new StackPane();
        StackPane descriptionPane = new StackPane();
        scorePane.getChildren().add(scorePoints);
        descriptionPane.getChildren().add(description);
        textPane.setLeft(scorePane);
        textPane.setCenter(descriptionPane);
        scorePane.setPrefWidth((getViewWidth()/6));
        textPane.setPrefSize((getViewWidth()/1.2), (getViewHeight()/4));
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        textPane.getStylesheets().add(css);
        textPane.setId("ObjectiveCardDescription");
        setBottom(textPane);
    }
    
    @Override
    public void render() {
        getChildren().clear();
        showImage(publicObjectiveCard.getImageUrl());
        showText();
    }
}
