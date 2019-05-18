package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

public class ToolCardLoodOpenHaler extends ToolCard {
    private int numberOfDices = 2;

    public ToolCardLoodOpenHaler(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        while(numberOfDices > 0) {
            try {
                PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
                PatternCardFieldView patternCardFieldView = (PatternCardFieldView) event.getTarget();

                PatternCardField patternCardField = patternCardFieldView.getPatternCardField();
                PatternCard patternCard = patternCardField.getPatternCard();
                Player player = patternCard.getPlayer();

                PatternCardField removeDieField = patternCard.getPatternCardField(die.getPatternCardField().getxPos(), die.getPatternCardField().getyPos());
                patternCardField = patternCard.getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos());
                
                if (patternCardField.hasDie() == false && patternCardField.canPlaceDieByAttributes(die) && patternCard.checkSidesColor(patternCardField, die.getColor(), true)&& patternCard.checkSidesValue(patternCardField, die.getEyes(), true)) {
                    // If the new location matches the new requirements we can make those changes
                    removeDieField.setDie(null);
                    playerFrameFieldDao.removeDie(die, removeDieField, player);

                    die.setPatternCardField(patternCardField);
                    patternCardField.setDie(die);
                    playerFrameFieldDao.addDieToField(die, patternCardField, player);

                    return patternCard;

//                    return patternCard;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            return null;
        }
        return null;
        }
}
