package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

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
    public boolean hasRequirementsToRun(PlayerController playerController) {
        // TODO Auto-generated method stub
        return true;
    }
}
