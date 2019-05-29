package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.task.GetDieOfferOfRoundTask;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class DieOfferView extends TilePane implements ViewInterface {
    private Game game;
    private PlayerController playerController;
    private PatternCardView patternCardView;

    /**
     * Full Constructor
     *
     * @param game Game
     */
    public DieOfferView(Game game, PatternCardView patternCardView, PlayerController playerController) {
        this.game = game;
        this.playerController = playerController;
        this.patternCardView = patternCardView;
    }

    /**
     * Builds the panes with the dice
     */
    private void buildDice() {
        GetDieOfferOfRoundTask gdoort = new GetDieOfferOfRoundTask(game);
        gdoort.setOnSucceeded(e -> {
            ArrayList<GameDie> gameDice = gdoort.getValue();
            for (GameDie gameDie : gameDice) {
                Pane paddingPane = new Pane();
                DieView dieView = new DieView(gameDie, playerController, patternCardView);
                dieView.setPlayerController(playerController);
                dieView.resize(25, 25);
                paddingPane.setPadding(new Insets(2));
                dieView.render();
                paddingPane.getChildren().add(dieView);
                getChildren().add(paddingPane);
            } 
        });
        Thread thread = new Thread(gdoort);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void render() {
        buildDice();
    }
}
