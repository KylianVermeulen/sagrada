package nl.avans.sagrada.model.toolcard;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.DieOfferView;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.popups.Popup;

import javax.swing.border.Border;

public class ToolCardDriePuntStang extends ToolCard {
    private Popup popup;
    private PlayerController playerController;
    
    public ToolCardDriePuntStang(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        return null;
    }

    @Override
    public GameDie increaseEyes(MouseEvent event, Game game, Player player, PlayerController playerController) {
        DieView dieView = (DieView) event.getTarget();
        GameDie targetDie = dieView.getGameDie();
        targetDie.setEyes(targetDie.getEyes() + 1);
        return targetDie;
    }

    @Override
    public GameDie decreaseEyes(MouseEvent event, Game game, Player player, PlayerController playerController) {
        DieView dieView = (DieView) event.getTarget();
        GameDie targetDie = dieView.getGameDie();
        targetDie.setEyes(targetDie.getEyes() - 1);
        return targetDie;
    }
}
