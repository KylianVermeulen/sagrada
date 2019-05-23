package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

/**
 * Nadat je een dobbelsteen kiest, mag je de waarde ervan met 1 verhogen of verlagen. 1 mag geen 6
 * worden, 6 geen 1.
 */
public class ToolCardDriePuntStang extends ToolCard {

    public ToolCardDriePuntStang(int id, String name, int seqnr, String description) {
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
