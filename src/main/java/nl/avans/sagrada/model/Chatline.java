package nl.avans.sagrada.model;

import java.sql.Timestamp;
import nl.avans.sagrada.dao.ChatlineDAO;

public class Chatline {
    private Player player;
    private String message;
    private Timestamp timestamp;
    
    public Chatline(Player player, String message) {
    	this.player = player;
    	this.message = message;
    }

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}






