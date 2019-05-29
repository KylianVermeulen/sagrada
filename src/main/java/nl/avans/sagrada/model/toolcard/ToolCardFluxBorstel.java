package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Nadat je een dobbelsteen kiest, mag je hem opnieuw werpen. Als je hem niet kunt plaatsen, leg hem
 * dan terug in het Aanbod.
 */
public class ToolCardFluxBorstel extends ToolCard {
    private PlayerController playerController;

    public ToolCardFluxBorstel(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        if (die.isPopUpDie()) {
            if (event.getTarget() instanceof PatternCardFieldView) {
                PatternCardFieldView patternCardFieldView = (PatternCardFieldView) event
                        .getTarget();
                PatternCardField targetField = patternCardFieldView.getPatternCardField();
                PatternCard patternCard = targetField.getPatternCard();
                Player player = patternCard.getPlayer();

                if (targetField.placeDie(die)) {
                    playerController.removePopupPane();
                    new PlayerFrameFieldDao().updateDieLocation(die, targetField, player);
                    die.setPatternCardField(targetField);
                    targetField.setDie(die);
                    die.setIsOnOfferTable(false);
                    setIsDone(true);
                    return patternCard;
                }
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        this.playerController = playerController;
        return true;
    }
}
