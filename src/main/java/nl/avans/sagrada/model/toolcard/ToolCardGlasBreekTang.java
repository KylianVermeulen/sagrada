package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

public class ToolCardGlasBreekTang extends ToolCard {

    public ToolCardGlasBreekTang(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        try {
            PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
            PatternCardFieldView patternCardView = (PatternCardFieldView) event.getTarget();

            PatternCardField patternCardField = patternCardView.getPatternCardField();
            PatternCard patternCard = patternCardField.getPatternCard();
            Player player = patternCard.getPlayer();

            if (patternCardField.hasDie() == false && patternCardField.canPlaceDieByAttributes(die)
                    && patternCard.checkSidesColor(patternCardField, die.getColor(), true)
                    && patternCard.checkSidesValue(patternCardField, die.getEyes(), true)) {
                die.setPatternCardField(patternCardField);
                patternCardField.setDie(die);
                playerFrameFieldDao.addDieToField(die, patternCardField, player);

                return patternCard;
            }
        } catch (Exception e) {
        }
        return null;
    }

}
