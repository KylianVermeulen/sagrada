package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;

public class PatternCardDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public PatternCardDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Get the selected patterncard of a player.
     *
     * @param player The player.
     * @return The patterncard.
     */
    public PatternCard getSelectedPatterncardOfPlayer(Player player) {
        PatternCard patternCard = null;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT patterncard.* FROM patterncard INNER JOIN player p on patterncard.idpatterncard = p.patterncard_idpatterncard WHERE p.idplayer=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, player.getId())
            );
            if (rs.next()) {
                patternCard = new PatternCard(rs.getInt("idpatterncard"),
                        rs.getInt("difficulty"), rs.getBoolean("standard"));
                return patternCard;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patternCard;
    }

    /**
     * Add a patterncard to the database.
     * @param patterncard The patterncard.
     */
    public void addPatterncard(PatternCard patterncard) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO patterncard (idpatterncard, difficulty, standard) VALUES (?, ?, ?)",
                            "update"),
                    new QueryParameter(QueryParameter.INT, patterncard.getId()),
                    new QueryParameter(QueryParameter.INT, patterncard.getDifficulty()),
                    new QueryParameter(QueryParameter.BOOLEAN, patterncard.isStandard())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the optional patterncards of a player.
     *
     * @param player The player.
     * @return The patterncards.
     */
    public ArrayList<PatternCard> getOptionalPatternCardsOfPlayer(Player player) {
        ArrayList<PatternCard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT * FROM patterncard INNER JOIN patterncardoption p on patterncard.idpatterncard = p.patterncard_idpatterncard WHERE player_idplayer=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, player.getId())
            );
            while (rs.next()) {
                PatternCard patternCard = new PatternCard(rs.getInt("idpatterncard"),
                        rs.getInt("difficulty"), rs.getBoolean("standard"));
                list.add(patternCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Save the optional patterncards of a player.
     *
     * @param optionalPatterncards The patterncards.
     * @param player The player.
     */
    public void saveOptionalPatternCardsOfPlayer(ArrayList<PatternCard> optionalPatterncards,
            Player player) {
        for (PatternCard patternCard : optionalPatterncards) {
            System.out.println("" + patternCard.getId());
            try {
                ResultSet rs = dbConnection.executeQuery(
                        new Query(
                                "INSERT INTO patterncardoption (patterncard_idpatterncard, player_idplayer) VALUES (?, ?)",
                                "update"),
                        new QueryParameter(QueryParameter.INT, patternCard.getId()),
                        new QueryParameter(QueryParameter.INT, player.getId())
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get all standard patterncards.
     *
     * @return The patterncards.
     */
    public ArrayList<PatternCard> getAllStandardPatterncards() {
        ArrayList<PatternCard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM patterncard WHERE standard=?", "query"),
                    new QueryParameter(QueryParameter.BOOLEAN, true)
            );
            while (rs.next()) {
                PatternCard patternCard = new PatternCard(rs.getInt("idpatterncard"),
                        rs.getInt("difficulty"), rs.getBoolean("standard"));
                list.add(patternCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Gets the next patterncard id of a new patterncard.
     *
     * @return patternCardId.
     */
    public int getNextPatternCardId() {
        int patternCardId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(idpatterncard) AS highestPatternCardId FROM patterncard",
                            "query")
            );
            if (rs.next()) {
                patternCardId = rs.getInt("highestPatternCardId") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patternCardId;
    }
}
