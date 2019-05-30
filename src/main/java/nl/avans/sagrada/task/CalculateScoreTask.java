package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.model.Player;

public class CalculateScoreTask extends Task<Integer> {
    private Player player;
    private boolean withPrivateScore;
    
    /**
     * Constructor for the calculate score task
     * @param player
     */
    public CalculateScoreTask(Player player) {
        withPrivateScore = false;
        this.player = player;
    }
    
    /**
     * If you want to calculate the score with the private score
     * You need to set it to true
     * @param withPrivateScore
     */
    public void setWithPrivateScore(boolean withPrivateScore) {
        this.withPrivateScore = withPrivateScore;
    }

    @Override
    protected Integer call() throws Exception {
        int score = player.calculateScore(withPrivateScore, false);
        return new Integer(score);
    }
}
