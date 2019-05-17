package nl.avans.sagrada.model.toolcard;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            int eyes = die.getEyes();
            switch (eyes) {
                case 1:
                    die.setEyes(6);
                    gameDieDao.updateDie(player.getGame(), die, die.getRound());
                case 2:
                    die.setEyes(5);
                    gameDieDao.updateDie(player.getGame(), die, die.getRound());
                case 3:
                    die.setEyes(4);
                    gameDieDao.updateDie(player.getGame(), die, die.getRound());
                case 4:
                    die.setEyes(3);
                    gameDieDao.updateDie(player.getGame(), die, die.getRound());
                case 5:
                    die.setEyes(2);
                    gameDieDao.updateDie(player.getGame(), die, die.getRound());
                case 6:
                    die.setEyes(1);
                    gameDieDao.updateDie(player.getGame(), die, die.getRound());
                default:
                    Logger logger = Logger.getLogger(ToolCardSchuurBlok.class.getName());
                    FileHandler fileHandler = new FileHandler("schuurblok-java/log");
                    logger.log(Level.WARNING, "Could not receive correct amount of die eyes.");
                    logger.setLevel(Level.WARNING);
                    logger.addHandler(fileHandler);
            }
            die.setPatternCardField(patternCardField);
            patternCardField.setDie(die);
            playerFrameFieldDao.addDieToField(die, patternCardField, player);

            return patternCard;
        } catch (Exception e) {
        }
        return null;
    }
}
