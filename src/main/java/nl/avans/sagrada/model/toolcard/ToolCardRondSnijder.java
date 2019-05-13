package nl.avans.sagrada.model.toolcard;

import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.ToolCard;

public class ToolCardRondSnijder extends ToolCard {
    
    public ToolCardRondSnijder(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        // TODO Auto-generated method stub
        return null;
    }

}
