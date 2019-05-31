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
 * Verplaats een dobbelsteen in je raam. Je mag de voorwaarden voor waardes negeren. Je moet alle
 * andere voorwaarden nog steeds respecteren.
 */
public class ToolCardFolieAandrukker extends ToolCard {

    public ToolCardFolieAandrukker(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        try {
            PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
            PatternCardFieldView patternCardFieldView = (PatternCardFieldView) event.getTarget();

            PatternCardField patternCardField = patternCardFieldView.getPatternCardField();
            PatternCard patternCard = patternCardField.getPatternCard();
            Player player = patternCard.getPlayer();

            PatternCardField removeDieField = die.getPatternCardField();

            if (!patternCardField.hasDie() && patternCard
                    .checkSidesColor(patternCardField, die.getColor(), true) && patternCard
                    .isNextToDie(patternCardField)) {
                die.setInFirstTurn(player.isFirstTurn());
                removeDieField.setDie(null);
                playerFrameFieldDao.removeDie(die, removeDieField, player);

                die.setPatternCardField(patternCardField);
                patternCardField.setDie(die);
                UpdatePlayerFrameFieldTask upfft = new UpdatePlayerFrameFieldTask(die, patternCardField, player);
                Thread thread = new Thread(upfft);
                thread.setName("Update PlayerFrameField thread");
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
