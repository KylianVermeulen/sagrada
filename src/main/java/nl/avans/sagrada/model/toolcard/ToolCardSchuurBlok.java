package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.task.UpdatePlayerFrameFieldTask;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Nadat je een dobbelsteen kiest, mag je hem draaien naar de tegenovergestelde zijde. 6 naar 1, 5
 * naar 2, 4 naar 3, enz.
 */
public class ToolCardSchuurBlok extends ToolCard {

    public ToolCardSchuurBlok(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        try {
            GameDieDao gameDieDao = new GameDieDao();
            PatternCardFieldView patternCardView = (PatternCardFieldView) event.getTarget();

            PatternCardField patternCardField = patternCardView.getPatternCardField();
            PatternCard patternCard = patternCardField.getPatternCard();
            Player player = patternCard.getPlayer();

            if (patternCardField.canPlaceDie(die)) {
                int newEyes = (7 - die.getEyes());
                die.setInFirstTurn(player.isFirstTurn());
                die.setEyes(newEyes);
                gameDieDao.updateDieEyes(player.getGame(), die);
                die.setPatternCardField(patternCardField);
                patternCardField.setDie(die);
                UpdatePlayerFrameFieldTask updatePlayerFrameFieldTask = new UpdatePlayerFrameFieldTask(die, patternCardField, player);
                Thread thread = new Thread(updatePlayerFrameFieldTask);
                thread.setName("Update Player Frame Field");
                thread.setDaemon(true);
                thread.start();
                setIsDone(true);
                return patternCard;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        return true;
    }
}
