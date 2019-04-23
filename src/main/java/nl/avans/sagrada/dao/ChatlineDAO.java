package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mysql.cj.protocol.Resultset;

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

    /**
     * Method to add chatline to database
     * @param chatline Chatline
     */
    public void addChatline(Chatline chatline) {
        Player player = chatline.getPlayer();
        Timestamp timeStamp = chatline.getTimestamp();
        String message = chatline.getMessage();
        
        	 if(message.matches("")) {
             	
             } else {
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
    
	public void setTime(Chatline chatline) {
		try {
			ResultSet rs = dbConnection.executeQuery(
					new Query("SELECT NOW()", "query"));
			
			while (rs.next()) {
				chatline.setTimestamp(rs.getTimestamp("NOW()"));
			}
		
		} catch (SQLException e) {
			
		}
	}
	
	public boolean timeExists(Chatline chatline) {
		try {
			ResultSet rs = dbConnection.executeQuery(
					new Query("SELECT count(*) FROM chatline WHERE time =? AND player_idplayer =?", "query"),
							new QueryParameter(QueryParameter.TIMESTAMP, chatline.getTimestamp()),
							new QueryParameter(QueryParameter.INT, chatline.getPlayer().getId())
							);
			if(rs.next()) {
				int count = rs.getInt("count(*)");
				if(count > 0) {
					return true;
				} else {
					return false;
					
				}
			}
							
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
}
