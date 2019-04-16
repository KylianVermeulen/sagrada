package nl.avans.sagrada.dao;

import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.PublicObjectiveCard;

public class PublicObjectiveCardDAO {
    private DBConnection dbConnection;
    
    public PublicObjectiveCardDAO() {
        dbConnection = new DBConnection();
    }

    public ArrayList<PublicObjectiveCard> getAllPublicObjectiveCards() {
        return null;
    }

    public ArrayList<PublicObjectiveCard> getAllPublicObjectiveCardsOfGame(Game game) {
        return null;
    }
}
