package nl.avans.sagrada.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Player;

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
        showSquare();
        showDescription();
    }

    private void showDescription() {
        HBox bottomPrivateCard = new HBox();
        Text description = new Text(
                "TINTEN " + /* player.getPrivateObjectivecardColor().toUpperCase() + */ " - Persoonlijk \n"
                        + "Som van waades op " + /* player.getPrivateObjectivecardColor() +*/ "\n" + "dobbelstenen");

        bottomPrivateCard.getChildren().add(description);
        setBottom(bottomPrivateCard);
    }

    private void showSquare() {
        StackPane squarePane = new StackPane();

        Rectangle colorSquare = new Rectangle();
        colorSquare.setWidth(SQUAREWIDTH);
        colorSquare.setHeight(SQUAREHEIGHT);
        colorSquare.setFill(Color.BLUE);
        colorSquare.setStroke(Color.BLACK);
        colorSquare.setStrokeWidth(2);

        squarePane.getChildren().add(colorSquare);
        squarePane.setMaxSize(getViewWidth(), getViewHeight() / 6);

        squarePane.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));

        setCenter(squarePane);

    }

}
