package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;

public class SetSelectedPatternCardOfPlayerTask extends Task<Void> {
    private Player player;
    
    public SetSelectedPatternCardOfPlayerTask(Player player) {
        this.player = player;
    }

    @Override
    protected Void call() throws Exception {
        PlayerDao playerDao = new PlayerDao();
        playerDao.updateSelectedPatternCard(player, player.getPatternCard());
        return null;
    }

}
