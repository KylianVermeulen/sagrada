package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Toolcard;

public class ToolcardDAO {
    private DBConnection dbConnection;

    public ToolcardDAO() {
        dbConnection = new DBConnection();
    }

    public ArrayList<Toolcard> getToolcardsOfGame(Game game) {
        ArrayList<Toolcard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT toolcard.* FROM toolcard INNER JOIN gametoolcard g on toolcard.idtoolcard = g.idtoolcard WHERE g.idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            while (rs.next()) {
                Toolcard toolcard = new Toolcard(rs.getInt("idtoolcard"), rs.getInt("seqnr"),
                        rs.getString("description"));
                list.add(toolcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Toolcard> getAllToolcards() {
        ArrayList<Toolcard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM toolcard", "query"));
            while (rs.next()) {
                Toolcard toolcard = new Toolcard(rs.getInt("idtoolcard"), rs.getInt("seqnr"),
                        rs.getString("description"));
                list.add(toolcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
