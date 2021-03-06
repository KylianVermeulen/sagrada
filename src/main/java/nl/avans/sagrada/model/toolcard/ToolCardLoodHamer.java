package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
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
 * Werp alle dobbelstenen in het Aanbod opnieuw. Je mag dit enkel doen tijdens je 2e beurt, voor je
 * een steen kiest.
 */
public class ToolCardLoodHamer extends ToolCard {

    public ToolCardLoodHamer(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        try {
            PatternCardFieldView patternCardView = (PatternCardFieldView) event.getTarget();

            PatternCardField patternCardField = patternCardView.getPatternCardField();
            PatternCard patternCard = patternCardField.getPatternCard();
            Player player = patternCard.getPlayer();

            if (patternCardField.canPlaceDie(die)) {
                die.setInFirstTurn(player.isFirstTurn());
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
        PlayerDao playerDao = new PlayerDao();
        if (playerDao.getCountPlacedDieInTurnRound(player) < 1) {
            if (player.getSeqnr() > player.getGame().getPlayers().size()) {
                doToolCardAction(playerController);
                return true;
            }
        }
        return false;
    }

    /**
     * Performs the reroll acion.
     *
     * @param playerController The playercontroller.
     */
    private void doToolCardAction(PlayerController playerController) {
        Player player = playerController.getPlayer();
        player.getGame().rerollRoundDice();
        playerController.setActiveToolCardNull();
        playerController.viewGame(false);
    }
}
