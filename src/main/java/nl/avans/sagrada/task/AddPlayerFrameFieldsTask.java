package nl.avans.sagrada.task;

import java.util.ArrayList;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;

public class AddPlayerFrameFieldsTask implements Runnable {
    private ArrayList<PatternCardField> patternCardFields;
    private Player player;

    /**
     * Constructor for the AddPlayerFrameFields task.
     * 
     * @param patternCardFields ArrayList
     * @param player Player
     */
    public AddPlayerFrameFieldsTask(ArrayList<PatternCardField> patternCardFields, Player player) {
        this.patternCardFields = patternCardFields;
        this.player = player;
    }
    
    @Override
    public void run() {
        new PlayerFrameFieldDao().addPlayerFrameFields(patternCardFields, player);
    }
}
