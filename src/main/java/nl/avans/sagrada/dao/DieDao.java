package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.model.Die;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DieDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public DieDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Gets dice out of the database
     *
     * @return ArrayList<GameDie>
     */
    public ArrayList<Die> getDice() {
        ArrayList<Die> dice = new ArrayList<Die>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM die", "query")
            );
            while (rs.next()) {
                Die die = new Die(rs.getInt("number"), rs.getString("color"));
                dice.add(die);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dice;
    }
}
