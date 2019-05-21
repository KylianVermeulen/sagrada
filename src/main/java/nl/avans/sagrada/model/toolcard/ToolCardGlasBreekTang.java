package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

/**
 * Na je eerste beurt mag je meteen een tweede dobbelsteen kiezen. Sla deze ronde je 2e beurt over.
 */
public class ToolCardGlasBreekTang extends ToolCard {

    public ToolCardGlasBreekTang(int id, String name, int seqnr, String description) {
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
