package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;

public class ToolCardFluxBorstel extends ToolCard {
    
    public ToolCardFluxBorstel(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameDie handleClick(MouseEvent event, Game game, PlayerController playerController, Pane pane) {
        return null;
    }
}
