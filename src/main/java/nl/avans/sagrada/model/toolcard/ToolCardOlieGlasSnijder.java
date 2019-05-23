package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

/**
 * Verplaats tot 2 dobbelstenen van dezelfde kleur die overeenkomen met een steen op het Rondespoor.
 * Je moet alle andere voorwaarden nog steeds repsecteren.
 */
public class ToolCardOlieGlasSnijder extends ToolCard {

    public ToolCardOlieGlasSnijder(int id, String name, int seqnr, String description) {
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
