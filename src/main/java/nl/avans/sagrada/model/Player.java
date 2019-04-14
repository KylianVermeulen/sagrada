package nl.avans.sagrada.model;

import java.util.ArrayList;

public class Player {
    private int id;
    private String playerStatus;
    private int seqnr;
    private boolean currentPlayer;
    private String privateObjectivecardColor;
    private PatternCard patterncard;
    private PatternCard[] optionalPatterncards;
    private Game game;
    private ArrayList<FavorToken> favorTokens;
    private boolean cheatmode;
}
