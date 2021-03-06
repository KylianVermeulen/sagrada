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
 * Na je eerste beurt mag je meteen een tweede dobbelsteen kiezen. Sla deze ronde je 2e beurt over.
 */
public class ToolCardGlasBreekTang extends ToolCard {

    public ToolCardGlasBreekTang(int id, String name, int seqnr, String description) {
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

            if (patternCardField.placeDie(die) && die.getPatternCardField() != null) {
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
        Player player = playerController.getPlayer();
        if (player.getSeqnr() <= player.getGame().getPlayers().size()) {
            return true;
        }
        return false;
    }
}
