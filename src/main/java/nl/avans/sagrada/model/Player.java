package nl.avans.sagrada.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Player {
    private int id;
    private Account account;
    private Game game;
    private String playerStatus;
    private int seqnr;
    private boolean currentPlayer;
    private String privateObjectivecardColor;
    private PatternCard patterncard;
    private int score;

    private PatternCard[] optionalPatterncards;
    private ArrayList<FavorToken> favorTokens;
    private boolean cheatmode;

    /**
     * Constructor
     *
     * @param rs ResultSet from DAO
     * @param account Account from DAO param
     */
    public Player(ResultSet rs, Account account) throws SQLException {
        setId(rs.getInt("idplayer"));
        setAccount(account);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public PatternCard getPatterncard() {
        return patterncard;
    }

    public void setPatterncard(PatternCard patterncard) {
        this.patterncard = patterncard;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
