package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class ScoreBoardView extends BorderPane implements ViewInterface {
    private final int SCORE_LINE_SPACING = 10;
    private final Font SCORE_TITLE_FONT = new Font("Segoe Script", 26);
    private final Font SCORE_LINE_FONT = new Font("Segoe Script", 20);
    private final int SCOREBOARD_SIZE = 300;
    private Game game;
    private PlayerController playerController;
    private boolean endGame;

    /**
     * Filled Constructor
     * 
     * @param game Game
     */
    public ScoreBoardView(Game game, PlayerController playerController) {
        this.game = game;
        this.playerController = playerController;
        this.endGame = false;
        setPrefSize(SCOREBOARD_SIZE, SCOREBOARD_SIZE);
        setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
    }

    /**
     * Filled Constructor
     *
     * @param game Game
     */
    public ScoreBoardView(Game game, PlayerController playerController, boolean endGame) {
        this.game = game;
        this.playerController = playerController;
        this.endGame = endGame;
        setPrefSize(SCOREBOARD_SIZE, SCOREBOARD_SIZE);
        setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
    }

    /**
     * Builds the content for the scoreboard view.
     */
    private void buildContent() {
        VBox scoreBoardContent = new VBox();
        ArrayList<HBox> scoreLines = new ArrayList<HBox>();

        StackPane scoreTitlePane = new StackPane();
        Label scoreTitle = new Label("Public Scorebord");
        scoreTitle.setFont(SCORE_TITLE_FONT);
        scoreTitle.setTextAlignment(TextAlignment.CENTER);
        scoreTitlePane.getChildren().add(scoreTitle);
        scoreTitlePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < game.getPlayers().size(); i++) {
            HBox playerLine = new HBox();
            Label playerScore;
            playerLine.setSpacing(SCORE_LINE_SPACING);
            Label playerName =
                    new Label(game.getPlayers().get(i).getAccount().getUsername() + ": ");
            playerName.setFont(SCORE_LINE_FONT);
            playerName.setTextAlignment(TextAlignment.CENTER);

            if (endGame) {
                playerScore =
                        new Label(Integer.toString(game.getPlayers().get(i).calculateScore(true)));
            } else {
                playerScore =
                        new Label(Integer.toString(game.getPlayers().get(i).calculateScore(false)));
            }
            playerScore.setFont(SCORE_LINE_FONT);
            playerScore.setTextAlignment(TextAlignment.CENTER);
            playerLine.getChildren().addAll(playerName, playerScore);
            playerLine.setAlignment(Pos.CENTER);
            scoreLines.add(i, playerLine);
        }
        scoreBoardContent.getChildren().addAll(scoreLines);
        setTop(scoreTitlePane);
        setCenter(scoreBoardContent);
    }

    @Override
    public void render() {
        getChildren().clear();
        buildContent();
    }
}
