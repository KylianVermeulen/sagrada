package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

public class ToolCardSnijLiniaal extends ToolCard {
    
    public ToolCardSnijLiniaal(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        // TODO Auto-generated method stub
        try {
            PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
            PatternCardFieldView patternCardView = (PatternCardFieldView) event.getTarget();
            
            PatternCardField patternCardField =  patternCardView.getPatternCardField();
            PatternCard patternCard = patternCardField.getPatternCard();
            Player player =patternCard.getPlayer();
            PatternCardField removeDieField = patternCard.getPatternCardField(die.getPatternCardField().getxPos(), die.getPatternCardField().getyPos());

            
            PatternCardField field = patternCard.getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos());
            
            if (patternCardField.hasDie() == false && patternCardField.canPlaceDieByAttributes(die)
                && patternCard.checkSidesColor(field, die.getColor(), true) && patternCard.checkSidesValue(field, die.getEyes(), true)) {

                removeDieField.setDie(null);
                playerFrameFieldDao.removeDie(die, removeDieField, player);
                
                die.setPatternCardField(field);
                field.setDie(die);
                playerFrameFieldDao.addDieToField(die, field, player);
                
                return patternCard;
            }
        } catch (Exception e) {}

        return null;
    }

}
