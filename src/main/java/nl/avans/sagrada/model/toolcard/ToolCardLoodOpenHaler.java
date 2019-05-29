package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Verplaats exact 2 dobbelstenen. Je moet hierbij alle voorwaarden van plaatsing respecteren.
 */
public class ToolCardLoodOpenHaler extends ToolCard {
    private int numberOfUses;

    public ToolCardLoodOpenHaler(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
        numberOfUses = 0;
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
        PatternCardFieldView patternCardFieldView = (PatternCardFieldView) event.getTarget();

        PatternCardField patternCardField = patternCardFieldView.getPatternCardField();
        PatternCard patternCard = patternCardField.getPatternCard();
        Player player = patternCard.getPlayer();

        PatternCardField removeDieField = patternCard
                .getPatternCardField(die.getPatternCardField().getxPos(),
                        die.getPatternCardField().getyPos());
        patternCardField = patternCard
                .getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos());

        if (patternCardField.canPlaceDie(die) && die.getPatternCardField() != null) {
            // If the new location matches the new requirements we can make those changes
            removeDieField.setDie(null);
            playerFrameFieldDao.removeDie(die, removeDieField, player);

            die.setPatternCardField(patternCardField);
            patternCardField.setDie(die);
            playerFrameFieldDao.updateDieLocation(die, patternCardField, player);
            numberOfUses++;
            handleNumberOfUses();
            return patternCard;
        }
        return null;
    }

    /**
     * Checks if the toolcard is done
     */
    private void handleNumberOfUses() {
        if (numberOfUses >= 2) {
            setIsDone(true);
        } else {
            setIsDone(false);
        }
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        Player player = playerController.getPlayer();
        PatternCard patternCard = player.getPatternCard();

        int numberOfFoundDie = 0;
        for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) {
            for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
                PatternCardField currentPatternCardField = patternCard.getPatternCardField(x, y);
                if (currentPatternCardField.getDie() != null) {
                    numberOfFoundDie++;
                }
            }
        }
        if (numberOfFoundDie >= 2) {
            // We need a minimum of 2 dice
            return true;
        } else {
            return false;
        }
    }
}
