package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.PublicObjectiveCard;

public class PublicObjectiveCardDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public PublicObjectiveCardDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Returns all public objective cards from the database.
     *
     * @return all public objective cards as an ArrayList
     */
    public ArrayList<PublicObjectiveCard> getAllPublicObjectiveCards() {
        ArrayList<PublicObjectiveCard> list = new ArrayList<PublicObjectiveCard>();
        try {
            ResultSet rs = dbConnection
                    .executeQuery(new Query("SELECT * FROM public_objectivecard", "query"));
            while (rs.next()) {
                PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(
                        rs.getInt("idpublic_objectivecard"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("points")
                );
                list.add(publicObjectiveCard);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns all public objective cards from the database, belonging to a certain game.
     *
     * @param game Game
     * @return all public objective cards belonging to game as an ArrayList
     */
    public ArrayList<PublicObjectiveCard> getAllPublicObjectiveCardsOfGame(Game game) {
        ArrayList<PublicObjectiveCard> list = new ArrayList<PublicObjectiveCard>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                    "SELECT public_objectivecard.* FROM public_objectivecard INNER JOIN sharedpublic_objectivecard s on public_objectivecard.idpublic_objectivecard = s.idpublic_objectivecard WHERE s.idgame=?",
                    "query"), new QueryParameter(QueryParameter.INT, game.getId()));
            while (rs.next()) {
                PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard(
                        rs.getInt("idpublic_objectivecard"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("points")
                );
                list.add(publicObjectiveCard);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * returns the id from a public objectivecard.
     */
    public PublicObjectiveCard getPublicObjectiveCardById(int id) {
        PublicObjectiveCard publicObjectiveCard = new PublicObjectiveCard();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM public_objectivecard WHERE idpublic_objectivecard=?",
                            "query", new QueryParameter(QueryParameter.INT, id)));
            if (rs.next()) {
                publicObjectiveCard.setId(rs.getInt("idpublic_objectivecard"));
                publicObjectiveCard.setName(rs.getString("name"));
                publicObjectiveCard.setDescription(rs.getString("description"));
                publicObjectiveCard.setPoints(rs.getInt("points"));
            }
            rs.close();
        } catch (Exception e) {
            publicObjectiveCard = null;
            e.printStackTrace();
        }
        return publicObjectiveCard;
    }

    /**
     * Adds a toolcard to a game. Both the tool card and the game that are subject to this method
     * are given as parameters.
     *
     * @param publicObjectiveCard The public objective card.
     * @param game The game.
     */
    public void addPublicObjectiveCardToGame(PublicObjectiveCard publicObjectiveCard, Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                            "INSERT INTO sharedpublic_objectivecard (idgame, idpublic_objectivecard) VALUES (?, ?)",
                            "update"), new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, publicObjectiveCard.getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
