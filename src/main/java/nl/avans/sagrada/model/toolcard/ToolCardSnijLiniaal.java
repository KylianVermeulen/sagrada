package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.ToolCard;
import nl.avans.sagrada.view.PatternCardFieldView;

public class ToolCardSnijLiniaal extends ToolCard {
    
    public ToolCardSnijLiniaal(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        // TODO Auto-generated method stub
        PatternCardFieldView patternCardView = (PatternCardFieldView) event.getTarget();
        PatternCardFieldView sourcePatternCardView = (PatternCardFieldView) event.getSource();
        
        PatternCardField patternCardFieldSource = sourcePatternCardView.getPatternCardField();
        PatternCardField patternCardField =  patternCardView.getPatternCardField();
        PatternCard patternCard = patternCardField.getPatternCard();
        
        patternCard.placeDie(patternCardField, die); 
        PatternCardField field = patternCard.getPatternCardField(patternCardFieldSource.getxPos(), patternCardFieldSource.getyPos());
        field.setDie(null);
        return patternCard;
    }

}
