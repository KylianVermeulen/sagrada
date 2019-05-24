package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import javax.swing.text.html.ImageView;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.view.PatternCardFieldView;

/**
 * Nadat je een dobbelsteen kiest, mag je hem terug in de zak stoppen en een nieuwe steen uit de zak
 * trekken. Kies een waarde en plaats de nieuwe steen, of leg hem in het Aanbod.
 */
public class ToolCardFluxVerwijderaar extends ToolCard {

    private PlayerController playerController;

    public ToolCardFluxVerwijderaar(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        if (die.isPopUpDie()) {
            if (event.getTarget() instanceof PatternCardFieldView) {
                PatternCardFieldView patternCardFieldView = (PatternCardFieldView) event
                        .getTarget();
                PatternCardField targetField = patternCardFieldView.getPatternCardField();

                int num = die.getNumber();
                String color = die.getColor();
                int round = die.getRound();
                int eyes = die.getEyes();

                if (targetField.placeDie(die)) {
                    playerController.removePopupPane();

                    die.setPatternCardField(targetField);
                    return die.getPatternCardField().getPatternCard();
                }
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean hasRequirementsToRun(PlayerController playerController) {
        this.playerController = playerController;
        return true;
    }
}
