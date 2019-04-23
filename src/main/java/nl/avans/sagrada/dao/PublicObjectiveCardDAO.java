package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.PublicObjectiveCard;

public class PublicObjectiveCardDAO {
    private DBConnection dbConnection;
    
    public PublicObjectiveCardDAO() {
        dbConnection = new DBConnection();
    }

    public ArrayList<PublicObjectiveCard> getAllPublicObjectiveCards() {
        ArrayList<PublicObjectiveCard> list = new ArrayList<PublicObjectiveCard>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM public_objectivecard", "query"));
            while (rs.next()) {
                PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(rs.getInt("idpublic_objectivecard"), rs.getInt("seqnr"), rs.getString("description"));
                list.add(publicObjectiveCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<PublicObjectiveCard> getAllPublicObjectiveCardsOfGame(Game game) {
        ArrayList<PublicObjectiveCard> list = new ArrayList<PublicObjectiveCard>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT public_objectivecard.* FROM public_objectivecard INNER JOIN sharedpublic_objectivecard s on public_objectivecard.idpublic_objectivecard = s.idpublic_objectivecard WHERE s.idgame=?" , "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            while (rs.next()) {
                PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(rs.getInt("idpublic_objectivecard"), rs.getInt("seqnr"), rs.getString("description"));
                list.add(publicObjectiveCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
