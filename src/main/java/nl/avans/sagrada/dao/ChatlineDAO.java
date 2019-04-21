package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Chatline;

public class ChatlineDAO {
    private DBConnection dbConnection;

    public void addChatline(Chatline chatline) {
    	try {
			ResultSet rs = dbConnection.executeQuery(
					new Query("INSERT INTO chatline(player_idplayer, time, message) VALUES (?, ?, ?)", "update"),
					new QueryParameter(QueryParameter.INT, chatline.getPlayer().getId()),
					new QueryParameter(QueryParameter.TIMESTAMP, chatline.getTimestamp()),
					new QueryParameter(QueryParameter.STRING, chatline.getMessage())
					);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
    
    
}
