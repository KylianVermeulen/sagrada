package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.task.UpdatePlayerFrameFieldTask;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Nadat je een dobbelsteen kiest, mag je de gekozen dobbeslteen wisselen met een dobbelsteen op het
 * Rondespoor.
 */
public class ToolCardRondSnijder extends ToolCard {
    private PlayerController playerController;

    public ToolCardRondSnijder(int id, String name, int seqnr, String description) {
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
        return null;
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        this.playerController = playerController;
        return playerController.getPlayer().getGame().getRound() > 1;
    }
}
