package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.GameDAO;

import java.util.ArrayList;

public class Player {
    private int id;
    private String username;
    private int idGame;
    private String playerStatus;
    private int seqnr;
    private boolean currentPlayer;
    private String privateObjectivecardColor;
    private String idPatterncard;
    private int score;

    private Account account;
    private Game game;
    private PatternCard patterncard;
    private PatternCard[] optionalPatterncards;
    private ArrayList<FavorToken> favorTokens;
    private boolean cheatmode;

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

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
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

    public String getIdPatterncard() {
        return idPatterncard;
    }

    public void setIdPatterncard(String idPatterncard) {
        this.idPatterncard = idPatterncard;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGame() {
        GameDAO gameDAO = new GameDAO();
        this.game = gameDAO.getGameById(this.idGame);
    }

    public PatternCard getPatterncard() {
        return patterncard;
    }

    public void setPatterncard(PatternCard patterncard) {
        this.patterncard = patterncard;
    }

    public PatternCard[] getOptionalPatterncards() {
        return optionalPatterncards;
    }

    public void setOptionalPatterncards(PatternCard[] optionalPatterncards) {
        this.optionalPatterncards = optionalPatterncards;
    }

    public ArrayList<FavorToken> getFavorTokens() {
        return favorTokens;
    }

    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    public boolean isCheatmode() {
        return cheatmode;
    }

    public void setCheatmode(boolean cheatmode) {
        this.cheatmode = cheatmode;
    }
}
