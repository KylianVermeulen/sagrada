package nl.avans.sagrada.task;

import java.util.ArrayList;

import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.toolcard.ToolCard;

public class FavorTokenPaymentTask implements Runnable {
    private ArrayList<FavorToken> favorTokens;
    private ToolCard toolCard;
    private Game game;
    private int numberOfPayments;
    
    /**
     * Constructor for the toolcard payment
     * @param favorTokens
     * @param toolCard
     * @param game
     * @param numberOfPayments
     */
    public FavorTokenPaymentTask(ArrayList<FavorToken> favorTokens, ToolCard toolCard, Game game, int numberOfPayments) {
        this.favorTokens = favorTokens;
        this.toolCard = toolCard;
        this.game = game;
        this.numberOfPayments = numberOfPayments;
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

    }

}
