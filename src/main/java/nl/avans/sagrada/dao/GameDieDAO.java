package nl.avans.sagrada.dao;

import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.model.GameDie;

public class GameDieDAO {
    private DBConnection dbConnection;

    public GameDieDAO() {
        dbConnection = new DBConnection();
    }

    public ArrayList<GameDie> getAllNotPlacesDies() {
        return null;
    }

    public void updateDie(GameDie gameDie) {

    }
}
