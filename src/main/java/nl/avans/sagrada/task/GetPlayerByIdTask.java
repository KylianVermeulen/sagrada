package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.model.Player;

public class GetPlayerByIdTask extends Task<Player> {
    private int playerId;
    
    /**
     * Constructor to get a player by id task
     * @param playerId
     */
    public GetPlayerByIdTask(int playerId) {
        this.playerId = playerId;
    }

    @Override
    protected Player call() throws Exception {
        return new PlayerDao().getPlayerById(playerId);
    }

}
