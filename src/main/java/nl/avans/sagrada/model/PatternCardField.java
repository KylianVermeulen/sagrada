package nl.avans.sagrada.model;

public class PatternCardField {
	private GameDie die;
	private int positionX;
	private int positionY;
	private String color;
	private int value;
	private PatternCard patternCard;
	
	public boolean canPlaceDieOnField(GameDie die) {
		return(false);
	}
}
