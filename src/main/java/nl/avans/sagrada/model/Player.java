package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.*;

import java.util.ArrayList;

public class Player {
    private int id;
    private Account account;
    private Game game;
    private String playerStatus;
    private int seqnr;
    private boolean currentPlayer;
    private String privateObjectivecardColor;
    private int idPatternCard;
    private PatternCard patternCard;
    private int score;

    private ArrayList<PatternCard> optionalPatternCards;
    private ArrayList<FavorToken> favorTokens;
    private boolean cheatmode;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }

    public int getSeqnr() {
        return seqnr;
    }

    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getPrivateObjectivecardColor() {
        return privateObjectivecardColor;
    }

    public void setPrivateObjectivecardColor(String privateObjectivecardColor) {
        this.privateObjectivecardColor = privateObjectivecardColor;
    }

    public int getScore() {
        return score;
    }

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
     * Get idPatternCard from Player
     *
     * @return int
     */
    public int getIdPatternCard() {
        return idPatternCard;
    }

    /**
     * Set idPatternCard to Player
     *
     * @param idPatternCard int
     */
    public void setIdPatternCard(int idPatternCard) {
        this.idPatternCard = idPatternCard;
    }

    /**
     * Get PatternCard from Player
     *
     * @return PatternCard
     */
    public PatternCard getPatternCard() {
        return patternCard;
    }

    /**
     * Set PatternCard to Player
     *
     * @param patterncard PatternCard
     */
    public void setPatternCard(PatternCard patterncard) {
        this.patternCard = patterncard;
        this.idPatternCard = this.patternCard.getId();
    }

    /**
     * Get PatternCard from database using this and set to Player
     */
    public void setPatternCard() {
        PatterncardDAO patterncardDAO = new PatterncardDAO();
        this.patternCard = patterncardDAO.getPatterncardOfPlayer(this);
        this.idPatternCard = this.patternCard.getId();
    }

    public ArrayList<PatternCard> getOptionalPatternCards() {
        return optionalPatternCards;
    }

    public void setOptionalPatternCards(ArrayList<PatternCard> optionalPatterncards) {
        this.optionalPatternCards = optionalPatterncards;
    }

    public void setOptionalPatternCards() {
        PatterncardDAO patterncardDAO = new PatterncardDAO();
        this.optionalPatternCards = patterncardDAO.getOptionalPatterncardOfPlayer(this);
    }

    public ArrayList<FavorToken> getFavorTokens() {
        return favorTokens;
    }

    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    public void setFavorToken() {
        FavorTokenDAO favorTokenDAO = new FavorTokenDAO();
        this.favorTokens = favorTokenDAO.getFavortokensOfPlayer(this);
    }

    public boolean isCheatmode() {
        return cheatmode;
    }

    public void setCheatmode(boolean cheatmode) {
        this.cheatmode = cheatmode;
    }
}
