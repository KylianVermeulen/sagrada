package nl.avans.sagrada.task;

import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.model.Player;

public class UpdateScoreTask implements Runnable {
    private Player player;
    
    public UpdateScoreTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        PlayerDao playerDao = new PlayerDao();
        playerDao.updateScore(player);
        
    }

}
