package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

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

        String dieColor = die.getColor();
        int dieEyes = die.getEyes();
        int targetValue = targetField.getValue();

        if (!targetField.hasDie() && patternCard.checkSidesValue(targetField, die.getEyes(), true)
                && patternCard.isNextToDie(targetField)) {
            if (targetField.hasValue()) {
                if (targetField.getValue() != die.getEyes()) {
                    return null;
                }
            }
            playerFrameFieldDao.removeDie(die, removeDieField, player);
            removeDieField.setDie(null);
            die.setPatternCardField(targetField);
            targetField.setDie(die);
            playerFrameFieldDao.addDieToField(die, targetField, player);


            return patternCard;
        }
        return null;
    }

}
