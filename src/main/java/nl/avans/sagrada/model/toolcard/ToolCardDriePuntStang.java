package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.task.UpdatePlayerFrameFieldTask;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Nadat je een dobbelsteen kiest, mag je de waarde ervan met 1 verhogen of verlagen. 1 mag geen 6
 * worden, 6 geen 1.
 */
public class ToolCardDriePuntStang extends ToolCard {

    public ToolCardDriePuntStang(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        if (die.getIsOnOfferTable()) {
            PatternCardFieldView patternCardFieldView = (PatternCardFieldView) event
                    .getTarget();
            PatternCardField targetField = patternCardFieldView.getPatternCardField();
            PatternCard patternCard = targetField.getPatternCard();
            Player player = patternCard.getPlayer();

            if (targetField.placeDie(die)) {
                die.setInFirstTurn(player.isFirstTurn());
                UpdatePlayerFrameFieldTask updatePlayerFrameFieldTask = new UpdatePlayerFrameFieldTask(die, targetField, player);
                Thread thread = new Thread(updatePlayerFrameFieldTask);
                thread.setName("Update Player Frame Field");
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

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        return (new PlayerDao().getCountPlacedDieInTurnRound(playerController.getPlayer()) < 1);
    }
}
