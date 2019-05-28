package nl.avans.sagrada.task;

import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;

public class UpdateDieTask implements Runnable {
    private Game game;
    private GameDie gameDie;
    
    /**
     * Constructor for updateDieTask
     * @param game
     * @param gameDie
     */
    public UpdateDieTask(Game game, GameDie gameDie) {
        this.game = game;
        this.gameDie = gameDie;
    }

    @Override
    public void run() {
        new GameDieDao().updateDie(game, gameDie);
    }
}
