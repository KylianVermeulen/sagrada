package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

public class ToolCardSchuurBlok extends ToolCard {

    public ToolCardSchuurBlok(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        try {
            GameDieDao gameDieDao = new GameDieDao();
            PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
            PatternCardFieldView patternCardView = (PatternCardFieldView) event.getTarget();

            PatternCardField patternCardField = patternCardView.getPatternCardField();
            PatternCard patternCard = patternCardField.getPatternCard();
            Player player = patternCard.getPlayer();

            if (patternCardField.hasDie() == false && patternCardField.canPlaceDieByAttributes(die)
                    && patternCard.checkSidesColor(patternCardField, die.getColor(), true)
                    && patternCard.checkSidesValue(patternCardField, die.getEyes(), true)) {
                int eyes = die.getEyes();
                switch (eyes) {
                    case 1:
                        die.setEyes(6);
                        gameDieDao.updateDieEyes(player.getGame(), die);
                        die.setPatternCardField(patternCardField);
                        patternCardField.setDie(die);
                        playerFrameFieldDao.addDieToField(die, patternCardField, player);
                        return patternCard;
                    case 2:
                        die.setEyes(5);
                        gameDieDao.updateDieEyes(player.getGame(), die);
                        die.setPatternCardField(patternCardField);
                        patternCardField.setDie(die);
                        playerFrameFieldDao.addDieToField(die, patternCardField, player);
                        return patternCard;
                    case 3:
                        die.setEyes(4);
                        gameDieDao.updateDieEyes(player.getGame(), die);
                        die.setPatternCardField(patternCardField);
                        patternCardField.setDie(die);
                        playerFrameFieldDao.addDieToField(die, patternCardField, player);
                        return patternCard;
                    case 4:
                        die.setEyes(3);
                        gameDieDao.updateDieEyes(player.getGame(), die);
                        die.setPatternCardField(patternCardField);
                        patternCardField.setDie(die);
                        playerFrameFieldDao.addDieToField(die, patternCardField, player);
                        return patternCard;
                    case 5:
                        die.setEyes(2);
                        gameDieDao.updateDieEyes(player.getGame(), die);
                        die.setPatternCardField(patternCardField);
                        patternCardField.setDie(die);
                        playerFrameFieldDao.addDieToField(die, patternCardField, player);
                        return patternCard;
                    case 6:
                        die.setEyes(1);
                        gameDieDao.updateDieEyes(player.getGame(), die);
                        die.setPatternCardField(patternCardField);
                        patternCardField.setDie(die);
                        playerFrameFieldDao.addDieToField(die, patternCardField, player);
                        return patternCard;
                    default:
                        return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
