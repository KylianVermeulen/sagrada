package nl.avans.sagrada.model;

import java.util.ArrayList;

public class Account {
	private String username;
	private String password;
	private ArrayList<Player> players;
	
	private String hashPassword(String password) {
		return(password);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return(password);
	}
	
	public String getUsername() {
		return(username);
	}
}
