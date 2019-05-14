package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.model.toolcard.ToolCardDriePuntStang;
import nl.avans.sagrada.model.toolcard.ToolCardEglomiseBorstel;
import nl.avans.sagrada.model.toolcard.ToolCardFluxBorstel;
import nl.avans.sagrada.model.toolcard.ToolCardFluxVerwijderaar;
import nl.avans.sagrada.model.toolcard.ToolCardFolieAandrukker;
import nl.avans.sagrada.model.toolcard.ToolCardGlasBreekTang;
import nl.avans.sagrada.model.toolcard.ToolCardLoodHamer;
import nl.avans.sagrada.model.toolcard.ToolCardLoodOpenHaler;
import nl.avans.sagrada.model.toolcard.ToolCardOlieGlasSnijder;
import nl.avans.sagrada.model.toolcard.ToolCardRondSnijder;
import nl.avans.sagrada.model.toolcard.ToolCardSchuurBlok;
import nl.avans.sagrada.model.toolcard.ToolCardSnijLiniaal;

public class ToolCardDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public ToolCardDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Returns all tool cards that are stored in the database as entries, belonging to a certain
     * game.
     *
     * @param game The game to which the tool cards belong
     * @return An ArrayList of tool cards that belong to this game
     */
    public ArrayList<ToolCard> getToolCardsOfGame(Game game) {
        ArrayList<ToolCard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                    "SELECT toolcard.* FROM toolcard INNER JOIN gametoolcard g on toolcard.idtoolcard = g.idtoolcard WHERE g.idgame=?",
                    "query"), new QueryParameter(QueryParameter.INT, game.getId()));
            while (rs.next()) {
                ToolCard toolCard = buildToolCard(
                        rs.getInt("idtoolcard"), 
                        rs.getString("name"), 
                        rs.getInt("seqnr"), 
                        rs.getString("description")
                    );
                list.add(toolCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns all tool cards that are stored in the database as entries.
     *
     * @return An ArrayList containing all Toolcard entries from the database
     */
    public ArrayList<ToolCard> getAllToolCards() {
        ArrayList<ToolCard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM toolcard", "query"));
            while (rs.next()) {
                ToolCard toolCard = buildToolCard(
                        rs.getInt("idtoolcard"), 
                        rs.getString("name"), 
                        rs.getInt("seqnr"), 
                        rs.getString("description")
                    );
                list.add(toolCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns the tool card that belongs to the given id.
     *
     * @param id The to be returned tool cards id
     * @return The tool card that belongs to the id entered as parameter
     */
    public ToolCard getToolCardById(int id) {
        ToolCard toolCard = null;
        try {
            ResultSet rs =
                    dbConnection.executeQuery(new Query("SELECT * FROM toolcard WHERE idtoolcard=?",
                            "query", new QueryParameter(QueryParameter.INT, id)));
            if (rs.next()) {
                toolCard = buildToolCard(
                        rs.getInt("idtoolcard"), 
                        rs.getString("name"), 
                        rs.getInt("seqnr"), 
                        rs.getString("description")
                    );
            }
        } catch (Exception e) {
            toolCard = null;
            e.printStackTrace();
        }
        return toolCard;
    }

    /**
     * Adds a tool card to a game. Both the toolcard and the game that are subject to this method
     * are given as parameters.
     *
     * @param toolCard The tool card that should be added to a game
     * @param game The game to which the toolcard should be added
     */
    public void addToolCardToGame(ToolCard toolCard, Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                            "INSERT INTO gametoolcard (gametoolcard, idtoolcard, idgame) VALUES (?, ?, ?)",
                            "update"), new QueryParameter(QueryParameter.INT, getNextGameToolCardId()),
                    new QueryParameter(QueryParameter.INT, toolCard.getId()),
                    new QueryParameter(QueryParameter.INT, game.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that returns the next gametoolcard integer that is available in the database table
     * 'gametoolcard'. This allows a new row to be added to the table, as this method ensures that a
     * possible new entry is added to an empty space in the table.
     *
     * @return The next gametoolcard int, which has not yet been used by any existing database
     * entries
     */
    public int getNextGameToolCardId() {
        int gameToolCardId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(gametoolcard) AS highestGameToolcardId FROM gametoolcard",
                            "query"));
            if (rs.next()) {
                gameToolCardId = rs.getInt("highestGameToolcardId") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameToolCardId;
    }

    /**
     * Returns the gametoolcard number that is linked to a toolcard and a game, the ids of which
     * have been given as parameters.
     *
     * @param toolcardId int
     * @param gameId int
     * @return The gametoolcard number belonging to a specific game and toolcard
     */
    public int getGameToolCardForToolCardId(int toolcardId, int gameId) {
        int gameToolCardId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gametoolcard WHERE idtoolcard=? AND idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, toolcardId),
                    new QueryParameter(QueryParameter.INT, gameId));
            if (rs.next()) {
                gameToolCardId = rs.getInt("gametoolcard");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameToolCardId;
    }

    /**
     * Checks if a toolcard has already received payment before. If the toolcard has received
     * payment before, the method will set a flag in the toolcard, notifying the game that this
     * toolcard has already recieved payment before. Otherwise it will set this flag to false.
     *
     * @param toolCard Toolcard
     * @param game Game
     */
    public void toolCardHasPayment(ToolCard toolCard, Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamefavortoken WHERE gametoolcard=? AND idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT,
                            getGameToolCardForToolCardId(toolCard.getId(), game.getId())),
                    new QueryParameter(QueryParameter.INT, game.getId()));
            if (rs.next()) {
                if (rs.getInt("gametoolcard") == 0) {
                    toolCard.setHasBeenPaidForBefore(false);
                } else {
                    toolCard.setHasBeenPaidForBefore(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Builds to toolcard based on the id
     * This is so the toolcard will contains the correct handleDrag method
     * @param id
     * @param name
     * @param seqnr
     * @param description
     * @return ToolCard
     */
    private ToolCard buildToolCard(int id, String name, int seqnr, String description) {
        switch (id) {
        case 1:
            ToolCardDriePuntStang toolCardDirPunt = new ToolCardDriePuntStang(id, name, seqnr, description);
            return toolCardDirPunt;
        case 2:
            ToolCardEglomiseBorstel toolCardEglo = new ToolCardEglomiseBorstel(id, name, seqnr, description);
            return toolCardEglo;
        case 3:
            ToolCardFolieAandrukker toolCardFolie = new ToolCardFolieAandrukker(id, name, seqnr, description);
            return toolCardFolie;
        case 4:
            ToolCardLoodOpenHaler toolCardLood = new ToolCardLoodOpenHaler(id, name, seqnr, description);
            return toolCardLood;
        case 5:
            ToolCardRondSnijder toolCardSnij = new ToolCardRondSnijder(id, name, seqnr, description);
            return toolCardSnij;
        case 6:
            ToolCardFluxBorstel toolCardFlux = new ToolCardFluxBorstel(id, name, seqnr, description);
            return toolCardFlux;
        case 7:
            ToolCardLoodHamer toolCardLoodHamer = new ToolCardLoodHamer(id, name, seqnr, description);
            return toolCardLoodHamer;
        case 8:
            ToolCardGlasBreekTang toolCardGlas = new ToolCardGlasBreekTang(id, name, seqnr, description);
            return toolCardGlas;
        case 9:
            ToolCardSnijLiniaal toolCardLini = new ToolCardSnijLiniaal(id, name, seqnr, description);
            return toolCardLini;
        case 10:
            ToolCardSchuurBlok toolCardSchuur = new ToolCardSchuurBlok(id, name, seqnr, description);
            return toolCardSchuur;
        case 11:
            ToolCardFluxVerwijderaar toolCardFluxVerwijderaar = new ToolCardFluxVerwijderaar(id, name, seqnr, description);
            return toolCardFluxVerwijderaar;
        case 12:
            ToolCardOlieGlasSnijder toolCardOlieGlasSnijder = new ToolCardOlieGlasSnijder(id, name, seqnr, description);
            return toolCardOlieGlasSnijder;
        default:
            return null;
        }
    }
}
