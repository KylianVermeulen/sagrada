package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.popups.Alert;
import nl.avans.sagrada.view.popups.AlertType;

public class ToolCardDriePuntStang extends ToolCard {
    PlayerController playerController;

    public ToolCardDriePuntStang(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        return null;
    }

    @Override
    public GameDie handleClick(MouseEvent event, PlayerController playerController, int i) {
        this.playerController = playerController;
        DieView dieView = (DieView) event.getTarget();
        GameDie targetDie = dieView.getGameDie();
        if (i == 1) {
            if(targetDie.getEyes() != 6){
                increaseEyes(targetDie);
                return targetDie;
            } else {
                Alert alert = new Alert("Helaas", "je kan de ogen van deze dobbelsteen niet verhogen", AlertType.ERROR);
                playerController.getMyScene().addAlertPane(alert);
            }
        }

        if (i == 0) {
            if(targetDie.getEyes() != 1){
                decreaseEyes(targetDie);
                return targetDie;
            } else {
                Alert alert = new Alert("Helaas", "je kan de ogen van deze dobbelsteen niet verlagen", AlertType.ERROR);
                playerController.getMyScene().addAlertPane(alert);
            }
        }
        return null;
    }

    private void increaseEyes(GameDie targetDie) {
        if (targetDie.getEyes() != 6) {
            targetDie.setEyes(targetDie.getEyes() + 1);
        }
    }

    private void decreaseEyes(GameDie targetDie) {
        if (targetDie.getEyes() != 1) {
            targetDie.setEyes(targetDie.getEyes() - 1);
        }
    }
}
