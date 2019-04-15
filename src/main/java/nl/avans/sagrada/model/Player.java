package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.*;

import java.util.ArrayList;

public class Player {
    private int id;
    private String username;
    private int idgame;
    private String playerStatus;
    private int seqnr;
    private boolean currentPlayer;
    private String privateObjectivecardColor;
    private int idPatterncard;
    private int score;

    private Account account;
    private Game game;
    private PatternCard patterncard;
    private ArrayList<PatternCard> optionalPatterncards;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdgame() {
        return idgame;
    }

    public void setIdgame(int idGame) {
        this.idgame = idGame;
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

    public int getIdPatterncard() {
        return idPatterncard;
    }

    public void setIdPatterncard(int idPatterncard) {
        this.idPatterncard = idPatterncard;
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
        this.username = this.account.getUsername();
    }

    /**
     * Get Account from database using username and set to Player
     */
    public void setAccount() {
        AccountDAO accountDAO = new AccountDAO();
        this.account = accountDAO.getAccountByUsername(this.username);
        this.username = this.account.getUsername();
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
        this.idgame = this.game.getId();
    }

    /**
     * Get Game from database using idgame and set to Player
     */
    public void setGame() {
        GameDAO gameDAO = new GameDAO();
        this.game = gameDAO.getGameById(this.idgame);
        this.idgame = this.game.getId();
    }

    /**
     * Get PatternCard from Player
     *
     * @return PatternCard
     */
    public PatternCard getPatterncard() {
        return patterncard;
    }

    /**
     * Set PatternCard to Player
     *
     * @param patterncard PatternCard
     */
    public void setPatterncard(PatternCard patterncard) {
        this.patterncard = patterncard;
        this.idPatterncard = this.patterncard.getId();
    }

    /**
     * Get PatternCard from database using this and set to Player
     */
    public void setPatterncard() {
        PatterncardDAO patterncardDAO = new PatterncardDAO();
        this.patterncard = patterncardDAO.getPatterncardOfPlayer(this);
        this.idPatterncard = this.patterncard.getId();
    }

    public ArrayList<PatternCard> getOptionalPatterncards() {
        return optionalPatterncards;
    }

    public void setOptionalPatterncards(ArrayList<PatternCard> optionalPatterncards) {
        this.optionalPatterncards = optionalPatterncards;
    }

    public void setOptionalPatterncards() {
        PatterncardDAO patterncardDAO = new PatterncardDAO();
        this.optionalPatterncards = patterncardDAO.getOptionalPatterncardOfPlayer(this);
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
