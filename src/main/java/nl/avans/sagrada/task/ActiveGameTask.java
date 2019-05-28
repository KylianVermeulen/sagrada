package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.concurrent.Task;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;

public class ActiveGameTask extends Task<ArrayList<Game>> {
    private Account account;
    
    /**
     * Constructor for the task to get all active games
     * @param account
     */
    public ActiveGameTask(Account account) {
        this.account = account;
    }

    @Override
    protected ArrayList<Game> call() throws Exception {
        ArrayList<Game> activeGames = account.getActiveGames();
        return activeGames;
    }

}
