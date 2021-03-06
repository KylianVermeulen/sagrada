package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.task.UpdateDieTask;
import nl.avans.sagrada.task.UpdatePlayerFrameFieldTask;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Nadat je een dobbelsteen kiest, mag je hem leggen in een vak dat niet grenst aan een andere
 * steen. Je moet alle andere voorwaarden nog steeds repsecteren.
 */
public class ToolCardSnijLiniaal extends ToolCard {

    public ToolCardSnijLiniaal(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        try {
            if (die.getIsOnOfferTable()) {
                PatternCardFieldView patternCardView = (PatternCardFieldView) event.getTarget();

                PatternCardField patternCardField = patternCardView.getPatternCardField();
                PatternCard patternCard = patternCardField.getPatternCard();
                Player player = patternCard.getPlayer();

                patternCardField = patternCard.getPatternCardField(patternCardField.getxPos(),
                        patternCardField.getyPos());

                if (patternCardField.hasDie() == false && patternCardField
                        .canPlaceDieByAttributes(die)
                        && patternCard.checkSidesColor(patternCardField, die.getColor(), true)
                        && patternCard.checkSidesValue(patternCardField, die.getEyes(), true)) {
                    // If the new location meats the new requirements we can make those changes
                    die.setInFirstTurn(player.isFirstTurn());

                    die.setIsOnOfferTable(false);
                    die.setPatternCardField(patternCardField);
                    patternCardField.setDie(die);
                    UpdateDieTask udt = new UpdateDieTask(player.getGame(), die);
                    Thread updateGameTread = new Thread(udt);
                    updateGameTread.setDaemon(true);
                    updateGameTread.setName("Update gamedie thread");
                    updateGameTread.start();
                    UpdatePlayerFrameFieldTask updatePlayerFrameFieldTask = new UpdatePlayerFrameFieldTask(die, patternCardField, player);
                    Thread thread = new Thread(updatePlayerFrameFieldTask);
                    thread.setName("Update Player Frame Field");
                    thread.setDaemon(true);
                    thread.start();
                    setIsDone(true);
                    return patternCard;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        return (new PlayerDao().getCountPlacedDieInTurnRound(playerController.getPlayer()) < 1);
    }
}
