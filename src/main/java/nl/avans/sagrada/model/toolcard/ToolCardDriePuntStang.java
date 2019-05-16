package nl.avans.sagrada.model.toolcard;

import javafx.scene.input.MouseEvent;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.DieOfferView;
import nl.avans.sagrada.view.DieView;

public class ToolCardDriePuntStang extends ToolCard {
    
    public ToolCardDriePuntStang(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public void handleDieChange(MouseEvent event, Game game, GameDie gameDie) {
        try {
            PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
            DieView dieView = (DieView) event.getTarget();

            GameDie die1 = dieView.getGameDie();
            DieOfferView dieOfferView = new DieOfferView();
            for(int i = 0; i < )




        } catch (Exception e){

        }
        return null;
    }

}
