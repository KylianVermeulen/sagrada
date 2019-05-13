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
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class ScoreboardView extends BorderPane implements ViewInterface {
    private final int SCORE_LINE_SPACING = 10;
    private Game game;

    public ScoreboardView(Game game) {
        this.game = game;
        setPrefSize(300, 300);
        setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));

    }

    private void buildContent() {
        VBox scoreBoardContent = new VBox();
        ArrayList<HBox> scoreLines = new ArrayList<HBox>();

        StackPane scoreTitlePane = new StackPane();
        Label scoreTitle = new Label("Scorebord");
        scoreTitle.setFont(new Font("Segoe Script", 26));
        scoreTitle.setTextAlignment(TextAlignment.CENTER);
        scoreTitlePane.getChildren().add(scoreTitle);
        scoreTitlePane.setAlignment(Pos.CENTER);

        for (int i = 0; i < game.getPlayers().size(); i++) {
            HBox playerLine = new HBox();
            playerLine.setSpacing(SCORE_LINE_SPACING);
            Label playerName =
                    new Label(game.getPlayers().get(i).getAccount().getUsername() + ": ");
            playerName.setFont(new Font("Segoe Script", 20));
            playerName.setTextAlignment(TextAlignment.CENTER);
            Label playerScore =
                    new Label(Integer.toString(game.getPlayers().get(i).calculateScore(true)));
            playerScore.setFont(new Font("Segoe Script", 20));
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
