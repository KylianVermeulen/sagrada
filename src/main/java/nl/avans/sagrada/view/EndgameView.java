package nl.avans.sagrada.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class EndgameView extends BorderPane implements ViewInterface {
    private final int button_width = 150;
    private final int button_height = 50;
    private PlayerController playerController;
    private Game game;
    private Player winplayer;
    private Button statisticsButton;
    private Button lobbyButton;
    private Label victoryLabel;
    private ScoreBoardView endScore;

    public EndgameView(Game game, PlayerController playercontroller, Player winplayer) {
        this.game = game;
        this.playerController = playercontroller;
        this.winplayer = winplayer;

        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        String css = this.getClass().getResource("/css/endgameview.css").toExternalForm();
        getStylesheets().add(css);
    }

    public void render() {
        getChildren().clear();
        buildEndScore();
        setLobbyButton();
    }

    private void buildEndScore() {
        BorderPane center = new BorderPane();
        victoryLabel = new Label(winplayer.getAccount().getUsername() + " has won the game!");
        endScore = new ScoreBoardView(game);
        victoryLabel.setMinHeight(100);
        victoryLabel.setMaxHeight(75);
        victoryLabel.setMinWidth(Main.SCREEN_WIDTH);
        victoryLabel.setAlignment(Pos.CENTER);
        victoryLabel.setFont(Font.font("Cambria", 52));
        victoryLabel.setTextFill(Color.DARKGREEN);
        endScore.render();
        endScore.setMaxHeight(300);
        endScore.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        endScore.setMaxWidth(400);
        endScore.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(5))));
        center.setTop(victoryLabel);
        center.setCenter(endScore);
        setCenter(center);
    }

    private void setLobbyButton() {
        HBox buttonBar = new HBox();
        buttonBar.setSpacing(300);
        buttonBar.setMinHeight(200);
        buttonBar.setAlignment(Pos.CENTER);
        lobbyButton = new Button("Return to lobby");
        lobbyButton.setPrefSize(button_width, button_height);
        lobbyButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(5))));
        lobbyButton.setOnAction(e -> playerController.actionBackToLobby());
        buttonBar.getChildren().addAll(lobbyButton);
        setBottom(buttonBar);
    }
}
