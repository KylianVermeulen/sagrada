package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.Player;

public class ChatlineDAO {
    private DBConnection dbConnection;
    
    public ChatlineDAO() {
        dbConnection = new DBConnection();
    }

    public void addChatline(Chatline chatline) {
        Player player = chatline.getPlayer();
        Timestamp timeStamp = chatline.getTimestamp();
        String message = chatline.getMessage();
    	try {
			ResultSet rs = dbConnection.executeQuery(
					new Query("INSERT INTO chatline(player_idplayer, time, message) VALUES (?, ?, ?)", "update"),
					new QueryParameter(QueryParameter.INT, player.getId()),
					new QueryParameter(QueryParameter.TIMESTAMP, timeStamp),
					new QueryParameter(QueryParameter.STRING, message)
				);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    
    
}
