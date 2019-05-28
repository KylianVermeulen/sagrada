package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class AllGamesOverView extends ScrollPane implements ViewInterface {
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
    public AllGamesOverView(AccountController accountController) {
        this.accountController = accountController;
        String css = this.getClass().getResource("/css/lobbyview.css").toExternalForm();
        getStylesheets().add(css);
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setMaxHeight(PANE_HEIGHT);
        setMinHeight(PANE_HEIGHT);
        setMaxWidth(PANE_WIDTH);
        setMinWidth(PANE_WIDTH);
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
        VBox vbox = new VBox();
        vbox.setMinHeight(PANE_HEIGHT - 2);
        vbox.setMinWidth(PANE_WIDTH - 2);
        for (Game game : games) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY, new BorderWidths(1))));
            hBox.setBackground(new Background(new BackgroundFill(Color.ROSYBROWN, null, null)));

            Label label = new Label("Game: " + game.getId());
            label.setPadding(new Insets(5, 4, 5, 4));
            label.setPrefWidth(LABEL_WIDTH);

            hBox.getChildren().add(label);
            vbox.getChildren().add(hBox);
        }
        setContent(vbox);
    }
}
