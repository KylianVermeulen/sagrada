package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;

public class PatterncardDAO {
    private DBConnection dbConnection;

    public PatternCard getPatterncardOfPlayer(Player player) {
        dbConnection = new DBConnection();
        try  {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM patterncard INNER JOIN player p on patterncard.idpatterncard = p.patterncard_idpatterncard WHERE p.username=?", "query"),
                    new QueryParameter(QueryParameter.STRING, player.getUsername())
            );
            if (rs.next()) {
                PatternCard patternCard = new PatternCard(rs.getInt("idpatterncard"), rs.getInt("difficulty"), rs.getBoolean("standard"));
                return patternCard;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PatternCard> getOptionalPatterncardOfPlayer(Player player) {
        return null;
    }

    public void savePatterncardOfPlayer(PatternCard patterncard, Player player) {
    }

    public void saveOptionalPatterncardOfPlayer(ArrayList<PatternCard> optionalPatterncards, Player player) {
    }

    public void createPatterncard(PatternCard patterncard) {
    }

    public ArrayList<PatternCard> getAllPatterncards() {
        return null;
    }

}
