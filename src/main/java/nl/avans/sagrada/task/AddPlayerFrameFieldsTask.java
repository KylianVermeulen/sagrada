package nl.avans.sagrada.task;

import java.util.ArrayList;
import nl.avans.sagrada.dao.PatternCardFieldDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;

public class AddPlayerFrameFieldsTask implements Runnable {
    private PatternCard patternCard;
    private Player player;

    /**
     * Constructor for the AddPlayerFrameFields task.
     * 
     * @param patternCardFields ArrayList
     * @param player Player
     */
    public AddPlayerFrameFieldsTask(PatternCard patternCard, Player player) {
        this.patternCard = patternCard;
        this.player = player;
    }

    @Override
    public void run() {
        ArrayList<PatternCardField> patternCardFields = new PatternCardFieldDao().getPatternCardFieldsOfPatterncard(patternCard);
        new PlayerFrameFieldDao().addPlayerFrameFields(patternCardFields, player);
    }
}
