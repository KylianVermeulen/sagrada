package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;

/**
 * Nadat je een dobbelsteen kiest, mag je de gekozen dobbeslteen wisselen met een dobbelsteen op het
 * Rondespoor.
 */
public class ToolCardRondSnijder extends ToolCard {

    public ToolCardRondSnijder(int id, String name, int seqnr, String description) {
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
