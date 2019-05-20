package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

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
            patternCardField = patternCard.getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos());

            if(patternCardField.hasDie() == false && patternCard.checkSidesColor(patternCardField, die.getColor(), true) && patternCard.isNextToDie(patternCardField) == true){
                removeDieField.setDie(null);
                playerFrameFieldDao.removeDie(die, removeDieField, player);

                die.setPatternCardField(patternCardField);
                patternCardField.setDie(die);
                playerFrameFieldDao.addDieToField(die, patternCardField, player);

                return patternCard;
            }
        } catch (Exception e){

        }
        return null;
    }
}
