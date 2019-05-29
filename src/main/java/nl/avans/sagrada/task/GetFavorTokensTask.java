package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.concurrent.Task;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Player;

public class GetFavorTokensTask extends Task<ArrayList<FavorToken>> {
    private Player player;
    
    public GetFavorTokensTask(Player player) {
        this.player = player;
    }

    @Override
    protected ArrayList<FavorToken> call() throws Exception {
        return player.getFavorTokens();
    }

}
