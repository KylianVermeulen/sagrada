package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameOverviewView extends ScrollPane implements ViewInterface {
    private final int PANE_WIDTH = Main.SCREEN_WIDTH / 5;
    private final int PANE_HEIGHT = Main.SCREEN_HEIGHT / 2 - 70;
    private final int LABEL_WIDTH = 90;
    private ArrayList<Game> games;
    private AccountController accountController;

    /**
     * Partial constructor
     *
     * @param accountController AccountController
     */
    public GameOverviewView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setPannable(true);
    }

    /**
     * Set games to view
     *
     * @param games ArrayList<Game>
     */
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    /**
     * Renders the view with all the information
     */
    @Override
    public void render() {
        getChildren().clear();
        VBox vbox = new VBox();
        for (Game game : games) {
            HBox hBox = new HBox();

            Label label = new Label("Game: " + game.getId());
            label.setPadding(new Insets(5, 4, 5, 4));
            label.setPrefWidth(LABEL_WIDTH);

            Button joinButton = buildButtonToJoin(game);

            hBox.getChildren().add(label);
            hBox.getChildren().add(joinButton);
            vbox.getChildren().add(hBox);
        }
        setContent(vbox);
    }

    /**
     * Builds a button to join a game
     *
     * @param game Game
     * @return Button
     */
    private Button buildButtonToJoin(Game game) {
        Button button = new Button("->");
        button.setOnAction(e -> accountController.actionJoinGame(game));
        return button;
    }
}
