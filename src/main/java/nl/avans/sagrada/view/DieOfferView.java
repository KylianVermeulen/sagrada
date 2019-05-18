package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class DieOfferView extends TilePane implements ViewInterface {
    private Game game;

    /**
     * Full Constructor
     *
     * @param game Game
     */
    public DieOfferView(Game game) {
        this.game = game;
        
    }

    /**
     * Builds the panes with the dice
     */
    private void buildDice() {
        for (GameDie gameDie : game.getRoundDice()) {
            Pane paddingPane = new Pane();
            DieView dieView = new DieView(gameDie);
            dieView.resize(25, 25);
            paddingPane.setPadding(new Insets(2));
            dieView.render();
            paddingPane.getChildren().add(dieView);
            this.getChildren().add(paddingPane);
        }
    }

    @Override
    public void render() {
        buildDice();
    }
}
