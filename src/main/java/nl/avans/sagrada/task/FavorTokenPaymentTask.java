package nl.avans.sagrada.task;

import java.util.ArrayList;

import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.view.ToolCardView;

public class FavorTokenPaymentTask implements Runnable {
    private ArrayList<FavorToken> favorTokens;
    private ToolCard toolCard;
    private Game game;
    private int numberOfPayments;
    private ToolCardView toolCardView;
    
    /**
     * Constructor for the toolcard payment
     * @param favorTokens
     * @param toolCard
     * @param game
     * @param numberOfPayments
     */
    public FavorTokenPaymentTask(ArrayList<FavorToken> favorTokens, ToolCard toolCard, Game game, ToolCardView toolCardView, int numberOfPayments) {
        this.favorTokens = favorTokens;
        this.toolCard = toolCard;
        this.game = game;
        this.numberOfPayments = numberOfPayments;
        this.toolCardView = toolCardView;
        Thread.currentThread().setName("ToolCard Payment");
    }

    @Override
    public void run() {
        int index  = 0;
        while (index < numberOfPayments) {
            FavorTokenDao favorTokenDao = new FavorTokenDao();
            favorTokenDao.setFavortokensForToolCard(favorTokens.get(index), toolCard, game);
            favorTokens.remove(index);
            index++;
        }
        toolCardView.setFavorTokens(favorTokens, game);
        toolCardView.render();
    }
}
