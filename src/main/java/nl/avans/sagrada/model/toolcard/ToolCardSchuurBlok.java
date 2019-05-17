package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.PatternCardFieldView;

public class ToolCardSchuurBlok extends ToolCard {
    private PlayerController playerController;

    public ToolCardSchuurBlok(int id, String name, int seqnr, String description,
            PlayerController playerController) {
        super(id, name, seqnr, description);
        this.playerController = playerController;
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

            patternCardField = patternCard.getPatternCardField(patternCardField.getxPos(),
                    patternCardField.getyPos());

            int newEyes = 0;
            int eyes = die.getEyes();
            switch (eyes) {
                case 1:
                    gameDieDao.updateDieEyes(6, playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    newEyes = gameDieDao.getDieEyes(playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    die.setEyes(newEyes);
                case 2:
                    gameDieDao.updateDieEyes(5, playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    newEyes = gameDieDao.getDieEyes(playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    die.setEyes(newEyes);
                case 3:
                    gameDieDao.updateDieEyes(4, playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    newEyes = gameDieDao.getDieEyes(playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    die.setEyes(newEyes);
                case 4:
                    gameDieDao.updateDieEyes(3, playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    newEyes = gameDieDao.getDieEyes(playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    die.setEyes(newEyes);
                case 5:
                    gameDieDao.updateDieEyes(2, playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    newEyes = gameDieDao.getDieEyes(playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    die.setEyes(newEyes);
                case 6:
                    gameDieDao.updateDieEyes(1, playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    newEyes = gameDieDao.getDieEyes(playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    die.setEyes(newEyes);
                default:
                    gameDieDao.updateDieEyes(3, playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    newEyes = gameDieDao.getDieEyes(playerController.getPlayer().getGame(),
                            die.getRound(), die);
                    die.setEyes(newEyes);
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
