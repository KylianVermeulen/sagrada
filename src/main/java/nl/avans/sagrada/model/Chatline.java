package nl.avans.sagrada.model;

import java.sql.Timestamp;

public class Chatline {
    private Player player;
    private String message;
    private Timestamp timestamp;
    
    /**
     * Constructor
     * @param player Player
     * @param message String
     */
    public Chatline(Player player, String message) {
    	this.player = player;
    	this.message = message;
    }
    
    /**
     * Empty Constructor
     */
    public Chatline() {
    	
    }

    /**
     * get player from chatline
     * @return
     */
	public Player getPlayer() {
		return player;
	}

	/**
	 * set player for the chatline
	 * @param player Player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * get message from chatline
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * set message for chatline
	 * @param message String
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * get timestamp from message
	 * @return
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * set timestamp for message
	 * @param timestamp Timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}






