package nl.avans.sagrada.model;

import java.util.ArrayList;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.FavorTokenDAO;
import nl.avans.sagrada.dao.GameDAO;
import nl.avans.sagrada.dao.PatternCardDAO;
import nl.avans.sagrada.dao.PlayerDAO;

public class Player {
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
    

    /**
     * Empty constructor
     */
    public Player() {
    }

    /**
     * Add object to databasee
     */
    public void add() {
        PlayerDAO playerDAO = new PlayerDAO();
        playerDAO.addPlayer(this);
    }

    /**
     * Update object in database
     */
    public void save() {
        PlayerDAO playerDAO = new PlayerDAO();
        playerDAO.updatePlayer(this);
    }

    /**
     * Get id from Player
     *
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Set id to Player
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }
    

    /**
     * Get playerstatus from Player
     *
     * @return String
     */
    public String getPlayerStatus() {
        return playerStatus;
    }

    /**
     * Set playerstatus to Player
     *
     * @param playerStatus String
     */
    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }

    /**
     * Get seqnr from Player
     *
     * @return int
     */
    public int getSeqnr() {
        return seqnr;
    }

    /**
     * Set seqnr of Player
     *
     * @param seqnr int
     */
    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }
    
    /**
     * Set if the player is the currentPlayer
     * @param isCurrentPlayer
     */
    public void setIsCurrentPlayer(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }
    
    
    /**
     * Get current player from Player
     *
     * @return boolean true when current player
     */
    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    /**
     * Set current player to Player
     *
     * @param currentPlayer boolean
     */
    public void setCurrentPlayer(boolean currentPlayer) {
        isCurrentPlayer = currentPlayer;
    }

    /**
     * Get private objective card color from Player
     *
     * @return String
     */
    public String getPrivateObjectivecardColor() {
        return privateObjectivecardColor;
    }

    /**
     * Set private objective card color to Player
     *
     * @param privateObjectivecardColor String
     */
    public void setPrivateObjectivecardColor(String privateObjectivecardColor) {
        this.privateObjectivecardColor = privateObjectivecardColor;
    }

    /**
     * Get score from Player
     *
     * @return int
     */
    public int getScore() {
        return score;
    }

    /**
     * Set score to Player
     *
     * @param score int
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get Account from Player
     *
     * @return Account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Set Account to Player
     *
     * @param account Account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Get Account from database using username and set to Player
     */
    public void setAccount() {
        AccountDAO accountDAO = new AccountDAO();
        this.account = accountDAO.getAccountByUsername(this.account.getUsername());
    }

    /**
     * Get Game from Player
     *
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set Game to Player
     *
     * @param game Game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Get Game from database using idgame and set to Player
     */
    public void setGame() {
        GameDAO gameDAO = new GameDAO();
        this.game = gameDAO.getGameById(this.game.getId());
    }

    /**
     * Get PatternCard from Player
     *
     * @return PatternCard
     */
    public PatternCard getPatternCard() {
        PatternCardDAO PatternCardDAO = new PatternCardDAO();
        patternCard = PatternCardDAO.getSelectedPatterncardOfPlayer(this);
        return patternCard;
    }

    /**
     * Set PatternCard to Player
     *
     * @param patterncard PatternCard
     */
    public void setPatternCard(PatternCard patterncard) {
        this.patternCard = patterncard;
    }

    /**
     * Get optional PatternCards from Player
     *
     * @return ArrayList<PatternCard>
     */
    public ArrayList<PatternCard> getOptionalPatternCards() {
        return optionalPatternCards;
    }

    /**
     * Set optional PatternCards to Player
     *
     * @param optionalPatterncards ArrayList<PatternCard>
     */
    public void setOptionalPatternCards(ArrayList<PatternCard> optionalPatterncards) {
        this.optionalPatternCards = optionalPatterncards;
    }

    /**
     * Set optional PatterCards to Player from database
     */
    public void setOptionalPatternCards() {
        PatternCardDAO PatternCardDAO = new PatternCardDAO();
        this.optionalPatternCards = PatternCardDAO.getOptionalPatterncardOfPlayer(this);
    }

    /**
     * Get FavorTokens from Player
     *
     * @return ArrayList<FavorToken>
     */
    public ArrayList<FavorToken> getFavorTokens() {
        return favorTokens;
    }

    /**
     * Set FavorTokens to Player
     *
     * @param favorTokens ArrayList<FavorToken>
     */
    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    /**
     * Set FavorTokens to Player from database
     */
    public void setFavorToken() {
        FavorTokenDAO favorTokenDAO = new FavorTokenDAO();
        this.favorTokens = favorTokenDAO.getFavortokensOfPlayer(this);
    }

    /**
     * Get cheatmode
     *
     * @return boolean true when enabled
     */
    public boolean isCheatmode() {
        return cheatmode;
    }

    /**
     * Set cheatmode
     *
     * @param cheatmode boolean
     */
    public void setCheatmode(boolean cheatmode) {
        this.cheatmode = cheatmode;
    }
    
    public String getImageUrl() {
        String color = getPrivateObjectivecardColor();
        String url;
        switch (color) {
        case "blue":
            url = "/images/privateObjectiveCardColors/blue.png";
            break;
        case "green":
            url = "/images/privateObjectiveCardColors/green.png";
            break;
        case "purple":
            url = "/images/privateObjectiveCardColors/purple.png";
            break;
        case "red":
            url = "/images/privateObjectiveCardColors/red.png";
            break;
        case "yellow":
            url = "/images/privateObjectiveCardColors/yellow.png";
            break;
        default: url = "Er is iets mis gegaan.";
                break;
        }
        return url;
    }
}
