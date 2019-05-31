package nl.avans.sagrada.task;

import nl.avans.sagrada.model.Game;

public class CancelGameTask implements Runnable {
    private Game game;
    
    /**
     * Constructor for cancel game task
     * @param game
     */
    public CancelGameTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.cancel();
    }
}
