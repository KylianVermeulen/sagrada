package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.PublicObjectiveCardDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.PublicObjectiveCard;

public class GetPublicObjectiveCardTask extends Task<PublicObjectiveCard[]> {
    private Game game;
    
    public GetPublicObjectiveCardTask(Game game) {
        this.game = game;
    }

    @Override
    protected PublicObjectiveCard[] call() throws Exception {
        PublicObjectiveCardDao publicObjectiveCardDao = new PublicObjectiveCardDao();
        PublicObjectiveCard[] publicObjectiveCards = publicObjectiveCardDao.getAllPublicObjectiveCardsOfGame(game)
                .toArray(new PublicObjectiveCard[3]);
        return publicObjectiveCards;
    }

}
