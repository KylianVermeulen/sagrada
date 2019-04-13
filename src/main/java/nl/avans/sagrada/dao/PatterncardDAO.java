package nl.avans.sagrada.dao;

import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;

public class PatterncardDAO {
	private DBConnection dbConnection;
	
	public PatternCard getPatterncardOfPlayer(Player player) {
		return(null);
	}
	
	public ArrayList<PatternCard> getOptionalPatterncardOfPlayer(Player player) {
		return null;
	}
	public void savePatterncardOfPlayer(PatternCard patterncard, Player player) {
		
	}
	
	public void createPatterncard(PatternCard patterncard, Player player, Game game) {
		
	}
	
	public ArrayList<PatternCard> getAllPatterncards() {
		return null;
	}
	
}
