package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

/**
 * Nadat je een dobbelsteen kiest, mag je hem terug in de zak stoppen en een nieuwe steen uit de zak
 * trekken. Kies een waarde en plaats de nieuwe steen, of leg hem in het Aanbod.
 */
public class ToolCardFluxVerwijderaar extends ToolCard {

    public ToolCardFluxVerwijderaar(int id, String name, int seqnr, String description) {
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
