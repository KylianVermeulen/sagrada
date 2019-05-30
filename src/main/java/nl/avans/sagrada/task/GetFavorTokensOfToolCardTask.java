package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.toolcard.ToolCard;

public class GetFavorTokensOfToolCardTask extends Task<ArrayList<FavorToken>> {
    private ToolCard toolCard;
    private Game game;
    
    /**
     * Constructor for the task to get all favortokens of a toolcard by game
     * @param toolCard
     * @param game
     */
    public GetFavorTokensOfToolCardTask(ToolCard toolCard, Game game) {
        this.toolCard = toolCard;
        this.game = game;
    }

    @Override
    protected ArrayList<FavorToken> call() throws Exception {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        ArrayList<FavorToken> favorTokens = favorTokenDao.getToolCardTokens(toolCard, game);
        return favorTokens;
    }
}
