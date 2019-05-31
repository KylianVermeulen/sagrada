package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import javax.swing.text.html.ImageView;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.task.UpdatePlayerFrameFieldTask;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Nadat je een dobbelsteen kiest, mag je hem terug in de zak stoppen en een nieuwe steen uit de zak
 * trekken. Kies een waarde en plaats de nieuwe steen, of leg hem in het Aanbod.
 */
public class ToolCardFluxVerwijderaar extends ToolCard {

    private PlayerController playerController;

    public ToolCardFluxVerwijderaar(int id, String name, int seqnr, String description) {
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
                    die.setInFirstTurn(player.isFirstTurn());
                    UpdatePlayerFrameFieldTask upfft = new UpdatePlayerFrameFieldTask(die, targetField, player);
                    Thread thread = new Thread(upfft);
                    thread.setName("Update PlayerFrameField thread");
                    thread.setDaemon(true);
                    thread.start();
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
