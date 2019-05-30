package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;

public class UpdatePlayerFrameFieldTask extends Task<Void> {
    private GameDie gameDie;
    private PatternCardField patternCardField;
    private Player player;
    
    /**
     * Constuctor for the task to update a player frame field
     * @param gameDie
     * @param patternCardField
     * @param player
     */
    public UpdatePlayerFrameFieldTask(GameDie gameDie, PatternCardField patternCardField, Player player) {
        this.gameDie = gameDie;
        this.patternCardField = patternCardField;
        this.player = player;
    }

    @Override
    protected Void call() throws Exception {
        PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
        playerFrameFieldDao.addDieToField(gameDie, patternCardField, player);
        return null;
    }

}
