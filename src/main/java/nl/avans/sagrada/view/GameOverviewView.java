package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Game;

import javax.swing.text.View;
import java.util.ArrayList;

public class GameOverviewView extends VBox implements ViewInterface {
    private static final int PANE_WIDTH = Main.SCREEN_WIDTH / 5;
    private static final int PANE_HEIGHT = Main.SCREEN_HEIGHT / 2;
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
        for (Game game : games) {
            HBox hBox = new HBox();

            Label label = new Label("Game: " +  game.getId());
            label.setPadding(new Insets(5, 4, 5, 4));
            label.setPrefWidth(60);

            Button joinButton = buildButtonToJoin(game);

            hBox.getChildren().add(label);
            hBox.getChildren().add(joinButton);
            getChildren().add(hBox);
        }
    }

    /**
     * Builds a button to join a game
     * @param game Game
     * @return Button
     */
    private Button buildButtonToJoin(Game game) {
        Button button = new Button("->");
        button.setOnAction(e -> accountController.joinGame(game));
        return button;
    }
}
