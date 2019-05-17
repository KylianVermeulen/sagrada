package nl.avans.sagrada.model.toolcard;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.DieOfferView;
import nl.avans.sagrada.view.DieView;

public class ToolCardDriePuntStang extends ToolCard {
    
    public ToolCardDriePuntStang(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        return null;
    }

    @Override
    public void handleClick(MouseEvent event, Game game, Player player, PlayerController playerController) {
        DieView targetDieView = (DieView) event.getTarget();
        GameDie targetDie = targetDieView.getGameDie();

        DieOfferView dieOfferView = new DieOfferView(game, playerController);
        Button plusButton = new Button();
        Button minButton = new Button();
        dieOfferView.getChildren().addAll(plusButton, minButton);
        dieOfferView.render();
    }
}
