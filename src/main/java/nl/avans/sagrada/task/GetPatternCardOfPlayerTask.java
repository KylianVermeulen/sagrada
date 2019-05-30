package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;

public class GetPatternCardOfPlayerTask extends Task<PatternCard> {
    private Player player;
    
    /**
     * Constructor for the task to get the favortokens of a player
     * @param player
     */
    public GetPatternCardOfPlayerTask(Player player) {
        this.player = player;
    }

    @Override
    protected PatternCard call() throws Exception {
        PatternCardDao PatternCardDao = new PatternCardDao();
        PatternCard patternCard = PatternCardDao.getSelectedPatterncardOfPlayer(player);
        return patternCard;
    }

}
