package nl.avans.sagrada.model;

import java.util.ArrayList;

public class Player {
	private int id;
	private String playerStatus;
	private int seqnr;
	private boolean currentPlayer;
	private String privateObjectivecardColor;
	private Patterncard patterncard;
	private Patterncard[] optionalPatterncards;
	private Game game;
	private ArrayList<GameFavorToken> favorTokens;
	private boolean cheatmode;
}
