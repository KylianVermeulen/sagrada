package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

public class ToolCardLoodHamer extends ToolCard {
    private PlayerController playerController;
    
    public ToolCardLoodHamer(int id, String name, int seqnr, String description, PlayerController playerController) {
        super(id, name, seqnr, description);
        this.playerController = playerController;
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        GameDao gameDao = new GameDao();
        if () {
            
        }
        return null;
    }

}
