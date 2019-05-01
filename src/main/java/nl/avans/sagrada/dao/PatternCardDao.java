package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public PatternCard getSelectedPatterncardOfPlayer(Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT patterncard.* FROM patterncard INNER JOIN player p on patterncard.idpatterncard = p.patterncard_idpatterncard WHERE p.username=?",
                            "query"),
                    new QueryParameter(QueryParameter.STRING, player.getAccount().getUsername())
            );
            if (rs.next()) {
                PatternCard patternCard = new PatternCard(rs.getInt("idpatterncard"),
                        rs.getInt("difficulty"), rs.getBoolean("standard"));
                return patternCard;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
