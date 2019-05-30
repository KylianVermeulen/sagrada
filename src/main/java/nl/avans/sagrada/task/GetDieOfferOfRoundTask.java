package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;

public class GetDieOfferOfRoundTask extends Task<ArrayList<GameDie>> {
    private Game game;
    
    /**
     * Constructor for the task to get all dice
     * That are on the round track
     * @param game
     */
    public GetDieOfferOfRoundTask(Game game) {
        this.game = game;
    }

    @Override
    protected ArrayList<GameDie> call() throws Exception {
        return new GameDieDao().getAvailableDiceOfRound(game);
    }

}
