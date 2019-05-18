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
    public GameDie handleClick(MouseEvent event, Game game, Player player, PlayerController playerController) {
        this.playerController = playerController;
        DieView targetDieView = (DieView) event.getTarget();
        GameDie targetDie = targetDieView.getGameDie();



        return targetDie;
    }

    public GameDie increaseDieEyes(GameDie gameDie){
        if(gameDie.getEyes() != 6){
            gameDie.setEyes(gameDie.getEyes() + 1);
            playerController.getMyScene().removePopupPane();
            return gameDie;
        }
        return null;
    }

    public GameDie decreaseDieEyes(GameDie gameDie){
        if(gameDie.getEyes() != 1){
            gameDie.setEyes(gameDie.getEyes() -1);
            playerController.getMyScene().removePopupPane();
            return gameDie;
        }
        return null;
    }
}
