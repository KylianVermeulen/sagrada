package nl.avans.sagrada.view;

import javafx.geometry.Pos;
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
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PlayerDAO;
import nl.avans.sagrada.model.Player;

public class PrivateObjectiveCardView extends CardView {

    private PlayerController playerController;
    private Player player;

    public PrivateObjectiveCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;
        player = new Player();

        render();
    }

    @Override
    public void render() {
        getChildren().clear();
        showImage(player.getImageUrl());
        showText();
    }

    public void showText() {
        PlayerDAO pDAO = new PlayerDAO();

        Text scorePoints = new Text("#");
        Text description = new Text("TINTEN " + pDAO.getPlayerById(1)
                + " - Persoonlijk \n Som van waardes op *getColor* \n" + " dobbelstenen");
        scorePoints.setFont(new Font("Segoe Script", 8));
        description.setFont(new Font("Segoe Script", 6));
        scorePoints.setTextAlignment(TextAlignment.CENTER);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrappingWidth(getViewWidth() / 1.2);
        BorderPane textPane = new BorderPane();
        StackPane scorePane = new StackPane();
        scorePane.getChildren().add(scorePoints);
        StackPane descriptionPane = new StackPane();
        descriptionPane.getChildren().add(description);
        textPane.setLeft(scorePane);
        textPane.setCenter(descriptionPane);
        scorePane.setPrefWidth(getViewWidth() / 6);
        textPane.setPrefSize((getViewWidth() / 1.2), (getViewHeight() / 4));
        textPane.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        textPane.setBackground(new Background(new BackgroundFill(Color.rgb(191, 191, 191), null, null)));
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        textPane.getStylesheets().add(css);
        textPane.setId("ObjectiveCardDescription");

        setBottom(textPane);

    }

    private void showImage(String url) {
        Image image = new Image(url);
        StackPane imagePane = new StackPane();
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(getViewHeight() / 1.25);
        imageView.setFitWidth(getViewWidth() + 10);
        imageView.setPreserveRatio(false);
        imagePane.getChildren().add(imageView);
        imagePane.setPrefSize(getViewWidth(), getViewHeight());
        setCenter(imagePane);

    }

}
