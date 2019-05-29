package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;

public class GetRoundTrackDiceTask extends Task<ArrayList<GameDie>> {
    private Game game;
    
    public GetRoundTrackDiceTask(Game game) {
        this.game = game;
    }

    @Override
    protected ArrayList<GameDie> call() throws Exception {
        ArrayList<GameDie> dice = new GameDieDao().getDiceOnRoundTrackFromGame(game);
        return dice;
    }

}
