package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

public class ToolCardSchuurBlok extends ToolCard {
    private PlayerController playerController;
    
    public ToolCardSchuurBlok(int id, String name, int seqnr, String description, PlayerController playerController) {
        super(id, name, seqnr, description);
        this.playerController = playerController;
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        GameDieDao gameDieDao = new GameDieDao();
        
        int eyes = die.getEyes();
        switch (eyes) {
            case 1:
                gameDieDao.updateDieEyes(6, playerController.getPlayer().getGame(), die.getRound(), die);
            case 2:
                gameDieDao.updateDieEyes(5, playerController.getPlayer().getGame(), die.getRound(), die);
            case 3:
                gameDieDao.updateDieEyes(4, playerController.getPlayer().getGame(), die.getRound(), die);
            case 4:
                gameDieDao.updateDieEyes(3, playerController.getPlayer().getGame(), die.getRound(), die);
            case 5:
                gameDieDao.updateDieEyes(2, playerController.getPlayer().getGame(), die.getRound(), die);
            case 6:
                gameDieDao.updateDieEyes(1, playerController.getPlayer().getGame(), die.getRound(), die);
            default:
                gameDieDao.updateDieEyes(3, playerController.getPlayer().getGame(), die.getRound(), die);
        }
        return null;
    }
}
