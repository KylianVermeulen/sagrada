package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.model.Game;

public class AllGamesTask extends Task<ArrayList<Game>> {
    
    /**
     * Constructor for the task to get all games
     */
    public AllGamesTask() {
        
    }

    @Override
    protected ArrayList<Game> call() throws Exception {
        ArrayList<Game> allGames = new GameDao().getAllGames();
        return allGames;
    }

}
