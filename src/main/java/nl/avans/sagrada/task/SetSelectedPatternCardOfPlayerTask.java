package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;

public class SetSelectedPatternCardOfPlayerTask extends Task<Void> {
    private Player player;
    private PatternCard patternCard;
    
    public SetSelectedPatternCardOfPlayerTask(Player player, PatternCard patternCard) {
        this.player = player;
        this.patternCard = patternCard;
    }

    @Override
    protected Void call() throws Exception {
        PlayerDao playerDao = new PlayerDao();
        playerDao.updateSelectedPatternCard(player, patternCard);
        return null;
    }

}
