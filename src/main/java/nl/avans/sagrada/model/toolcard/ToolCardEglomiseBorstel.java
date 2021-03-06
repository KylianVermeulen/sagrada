package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.task.UpdatePlayerFrameFieldTask;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Verplaats een dobbelsteen in je raam. Je mag de voorwaarden voor kleur negeren. Je moet alle
 * andere voorwaarden nog steeds respecteren.
 */
public class ToolCardEglomiseBorstel extends ToolCard {

    public ToolCardEglomiseBorstel(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
        PatternCardFieldView patternCardFieldView = (PatternCardFieldView) event.getTarget();
        PatternCardField targetField = patternCardFieldView.getPatternCardField();
        PatternCardField removeDieField = die.getPatternCardField();
        PatternCard patternCard = targetField.getPatternCard();
        Player player = patternCard.getPlayer();

        if (!targetField.hasDie() && patternCard.checkSidesValue(targetField, die.getEyes(), true)
                && patternCard.isNextToDie(targetField)) {
            if (targetField.hasValue()) {
                if (targetField.getValue() != die.getEyes()) {
                    return null;
                }
            }
            die.setInFirstTurn(player.isFirstTurn());
            playerFrameFieldDao.removeDie(removeDieField, player);
            removeDieField.setDie(null);
            die.setPatternCardField(targetField);
            targetField.setDie(die);
            UpdatePlayerFrameFieldTask updatePlayerFrameFieldTask = new UpdatePlayerFrameFieldTask(die, targetField, player);
            Thread thread = new Thread(updatePlayerFrameFieldTask);
            thread.setName("Update Player Frame Field");
            thread.setDaemon(true);
            thread.start();
            setIsDone(true);
            return patternCard;
        }
        return null;
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        PatternCard patternCard = playerController.getPlayer().getPatternCard();
        for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) {
                GameDie die = patternCard.getPatternCardField(x, y).getDie();
                if (die != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
