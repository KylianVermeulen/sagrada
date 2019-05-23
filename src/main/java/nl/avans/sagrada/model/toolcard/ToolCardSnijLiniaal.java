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
                    PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();

                    die.setIsOnOfferTable(false);
                    die.setPatternCardField(patternCardField);
                    patternCardField.setDie(die);
                    playerFrameFieldDao.addDieToField(die, patternCardField, player);
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
        return true;
    }
}
