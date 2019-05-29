package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.model.Player;

public class CalculateScoreTask extends Task<Integer> {
    private Player player;
    private boolean withPrivateScore;
    
    public CalculateScoreTask(Player player) {
        withPrivateScore = false;
        this.player = player;
    }
    
    public void setWithPrivateScore(boolean withPrivateScore) {
        this.withPrivateScore = withPrivateScore;
    }

    @Override
    protected Integer call() throws Exception {
        int score = player.calculateScore(withPrivateScore);
        System.out.println(score);
        return new Integer(score);
    }

}
