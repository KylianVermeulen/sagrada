package nl.avans.sagrada.model.toolcard;

import javafx.event.Event;
import nl.avans.sagrada.model.ToolCard;

public class ToolCardFolieAandrukker extends ToolCard {
    
    public ToolCardFolieAandrukker(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public boolean handleDrag(Event event) {
        // TODO Auto-generated method stub
        return false;
    }

}
