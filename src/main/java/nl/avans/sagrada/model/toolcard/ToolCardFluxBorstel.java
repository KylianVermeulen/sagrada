package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

/**
 * Nadat je een dobbelsteen kiest, mag je hem opnieuw werpen. Als je hem niet kunt plaatsen, leg hem
 * dan terug in het Aanbod.
 */
public class ToolCardFluxBorstel extends ToolCard {

    public ToolCardFluxBorstel(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        return null;
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        return true;
    }
}
