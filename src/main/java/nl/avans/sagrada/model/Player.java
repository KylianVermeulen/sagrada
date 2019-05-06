package nl.avans.sagrada.model;

import java.util.ArrayList;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;

public class Player {
    public static final String STATUS_ABORT = "aborted";
    private int id;
    private Account account;
    private Game game;
    private String playerStatus;
    private int seqnr;
    private boolean isCurrentPlayer;
    private String privateObjectivecardColor;
    private PatternCard patternCard;
    private ArrayList<PatternCard> optionalPatternCards;
    private ArrayList<FavorToken> favorTokens;
    private int score;
    private boolean cheatmode = false;

    public Player() {}

    /**
     * The id is a unique identifier for each player in the database.
     *
     * @return The id of this player.
     */
    public int getId() {
        return id;
    }

    /**
     * The id is a unique identifier for each player in the database.
     *
     * @param id Must be unique in the database.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The player status of a player.
     *
     * @return The status of this player.
     */
    public String getPlayerStatus() {
        return playerStatus;
    }

    /**
     * The player status of a player.
     *
     * @param playerStatus Must be an existing status in the database.
     */
    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }

    /**
     * The seqnr is the number of which turn the player has to act.
     *
     * @return The seqnr of this player.
     */
    public int getSeqnr() {
        return seqnr;
    }

    /**
     * The seqnr is the number of wich turn the player has to act.
     *
     * @param seqnr Must be a valid turn and unique in a game.
     */
    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }

    /**
     * The current player is the player that has to act in a game.
     *
     * @param isCurrentPlayer There can only be one current player in game.
     */
    public void setIsCurrentPlayer(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }

    /**
     * The current player is the player that has to act in a game.
     *
     * @return True when current player.
     */
    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    /**
     * The private objective card color is the color the player gets point for.
     *
     * @return The private objective card color of this player.
     */
    public String getPrivateObjectivecardColor() {
        return privateObjectivecardColor;
    }

    /**
     * The private objective card color is the color the player gets point for.
     *
     * @param privateObjectivecardColor Must be unique in a game.
     */
    public void setPrivateObjectivecardColor(String privateObjectivecardColor) {
        this.privateObjectivecardColor = privateObjectivecardColor;
    }

    /**
     * The score is the count of points a player has, starts at (-20 + favor tokens)
     *
     * @return The score of this player.
     */
    public int getScore() {
        return score;
    }

    /**
     * The score is the count of points a player has, starts at (-20 + favor tokens)
     *
     * @param score Must be greater or equal than -20.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * The account is the actor of the player.
     *
     * @return The account of this player.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * The account is the actor of the player.
     *
     * @param account Must be a existing account in the database.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * The game of this player.
     *
     * @return The game of this player.
     */
    public Game getGame() {
        return game;
    }

    /**
     * The game of this player.
     *
     * @param game Must be a existing game in the database.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return The patterncard of this player.
     */
    public PatternCard getPatternCard() {
        PatternCardDao PatternCardDao = new PatternCardDao();
        patternCard = PatternCardDao.getSelectedPatterncardOfPlayer(this);
        return patternCard;
    }

    /**
     * @param patterncard The patterncard of this player.
     */
    public void setPatternCard(PatternCard patterncard) {
        this.patternCard = patterncard;
    }

    /**
     * @return The optional pattern cards of this player from the database.
     */
    public ArrayList<PatternCard> getOptionalPatternCards() {
        PatternCardDao patternCardDao = new PatternCardDao();
        this.optionalPatternCards = patternCardDao.getOptionalPatternCardsOfPlayer(this);
        return optionalPatternCards;
    }

    /**
     * @param optionalPatterncards The optional pattern cards of this player to the database.
     */
    public void setOptionalPatternCards(ArrayList<PatternCard> optionalPatterncards) {
        this.optionalPatternCards = optionalPatterncards;
        PatternCardDao patternCardDao = new PatternCardDao();
        patternCardDao.saveOptionalPatternCardsOfPlayer(optionalPatterncards, this);
    }

    public void generateFavorTokens() {
        ArrayList<FavorToken> favorTokens = new ArrayList<>();
        for (int i = 0; i < patternCard.getDifficulty(); i++) {
            FavorTokenDao favorTokenDao = new FavorTokenDao();
            int favorTokenId = favorTokenDao.getNextFavorTokenId();
            FavorToken favorToken = new FavorToken(favorTokenId, this);
            favorTokenDao.addFavorToken(favorToken);
            favorTokens.add(favorToken);
        }
        this.favorTokens = favorTokens;
    }

    /**
     * @return The favor tokens of this player.
     */
    public ArrayList<FavorToken> getFavorTokens() {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        this.favorTokens = favorTokenDao.getFavortokensOfPlayer(this);
        return favorTokens;
    }

    /**
     * @param favorTokens The favor tokens of this player.
     */
    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    /**
     * @return True when cheatmode is enabled.
     */
    public boolean isCheatmode() {
        return cheatmode;
    }

    /**
     * @param cheatmode Enable or disable cheatmode.
     */
    public void setCheatmode(boolean cheatmode) {
        this.cheatmode = cheatmode;
    }

    /**
     * returns the immage's of the private-objectivecard.
     */
    public String getImageUrl() {
        PlayerDao playerDao = new PlayerDao();
        String color = playerDao.getPlayerById(1).privateObjectivecardColor;
        String url;
        switch (color) {
            case "blauw":
                url = "/images/privateObjectiveCardColors/blue.png";
                break;
            case "groen":
                url = "/images/privateObjectiveCardColors/green.png";
                break;
            case "paars":
                url = "/images/privateObjectiveCardColors/purple.png";
                break;
            case "rood":
                url = "/images/privateObjectiveCardColors/red.png";
                break;
            case "geel":
                url = "/images/privateObjectiveCardColors/yellow.png";
                break;
            default:
                url = "er is iets mis gegaan";
        }
        return url;
    }
}
