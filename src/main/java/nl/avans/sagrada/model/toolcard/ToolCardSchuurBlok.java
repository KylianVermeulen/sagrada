package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;

public class ToolCardSchuurBlok extends ToolCard {
    
    public ToolCardSchuurBlok(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameDie increaseEyes(MouseEvent event, Game game, Player player, PlayerController playerController) {
        return null;
    }

    @Override
    public GameDie decreaseEyes(MouseEvent event, Game game, Player player, PlayerController playerController) {
        return null;
    }
}
