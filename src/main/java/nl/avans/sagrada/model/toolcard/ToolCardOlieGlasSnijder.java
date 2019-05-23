package nl.avans.sagrada.model.toolcard;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Verplaats tot 2 dobbelstenen van dezelfde kleur die overeenkomen met een steen op het Rondespoor.
 * Je moet alle andere voorwaarden nog steeds repsecteren.
 */
public class ToolCardOlieGlasSnijder extends ToolCard {
    private int numberOfUses;

    public ToolCardOlieGlasSnijder(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
        numberOfUses = 0;
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        if (die.getPatternCardField() != null) {
            GameDieDao gameDieDao = new GameDieDao();
            PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();

            PatternCardFieldView patternCardFieldView = (PatternCardFieldView) event.getTarget();
            PatternCardField patternCardField = patternCardFieldView.getPatternCardField();
            PatternCard patternCard = patternCardField.getPatternCard();
            PatternCardField removeDieField = die.getPatternCardField();
            Player player = patternCard.getPlayer();


            ArrayList<GameDie> dice = gameDieDao.getDiceOnRoundTrackFromGame(player.getGame());
            ArrayList<String> roundTrackDiceColors = new ArrayList<>();
            for (GameDie gameDie : dice) {
                if (!roundTrackDiceColors.contains(gameDie.getColor())) {
                    roundTrackDiceColors.add(gameDie.getColor());
                }
            }
            if (!roundTrackDiceColors.contains(die.getColor())) {
                return null;
            }

            patternCardField = patternCard
                    .getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos());

            if (!patternCardField.hasDie() && patternCardField.canPlaceDieByAttributes(die)
                    && patternCard.checkSidesColor(patternCardField, die.getColor(), true)
                    && patternCard.checkSidesValue(patternCardField, die.getEyes(), true)) {

                removeDieField.setDie(null);
                playerFrameFieldDao.removeDie(die, removeDieField, player);

                die.setPatternCardField(patternCardField);
                patternCardField.setDie(die);
                playerFrameFieldDao.addDieToField(die, patternCardField, player);

                numberOfUses++;
                if (numberOfUses >= 2) {
                    numberOfUses = 0;
                    setIsDone(true);
                } else {
                    setIsDone(false);
                }
                return patternCard;
            }
        }
        return null;
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        if (playerController.getPlayer().getGame().getRoundDice().size() >= 2) {
            return true;
        } else {
            return false;
        }
    }
}
