package nl.avans.sagrada.model.toolcard;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

public class ToolCardOlieGlasSnijder extends ToolCard {
    private int countDie;
    
    public ToolCardOlieGlasSnijder(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
        countDie = 0;
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        if (die.getIsOnOfferTable()) {
            GameDieDao gameDieDao = new GameDieDao();;
            PatternCardFieldView patternCardView = (PatternCardFieldView) event.getTarget();
            PatternCardField patternCardField =  patternCardView.getPatternCardField();
            PatternCard patternCard = patternCardField.getPatternCard();
            Player player = patternCard.getPlayer();
            ArrayList<GameDie> dice = gameDieDao.getDiceOfRoundTrackFromGame(player.getGame());
            ArrayList<String> roundTrackDiceColors = new ArrayList<>();
            for (GameDie gameDie : dice) {
                if (!roundTrackDiceColors.contains(gameDie.getColor())) {
                    roundTrackDiceColors.add(gameDie.getColor());
                }
            }
            if (!roundTrackDiceColors.contains(die.getColor())) {
                return new PatternCard(0);
            }

            patternCardField = patternCard.getPatternCardField(patternCardField.getxPos(), patternCardField.getyPos());

            if (patternCardField.hasDie() == false && patternCardField.canPlaceDieByAttributes(die)
                    && patternCard.checkSidesColor(patternCardField, die.getColor(), true) && patternCard.checkSidesValue(patternCardField, die.getEyes(), true)) {
                PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();

                die.setIsOnOfferTable(false);
                die.setPatternCardField(patternCardField);
                patternCardField.setDie(die);
                playerFrameFieldDao.addDieToField(die, patternCardField, player);

                if (countDie == 0) {
                    countDie++;
                    return null;
                } else {
                    countDie = 0;
                    return patternCard;
                }
            }
        }
        return null;
    }
}
