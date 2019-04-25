package nl.avans.sagrada.model;

import java.util.ArrayList;
import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.FavorTokenDAO;
import nl.avans.sagrada.dao.GameDAO;
import nl.avans.sagrada.dao.PatternCardDAO;

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

    public Player() {
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

    public void setIsCurrentPlayer(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }

    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        isCurrentPlayer = currentPlayer;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAccount() {
        AccountDAO accountDAO = new AccountDAO();
        this.account = accountDAO.getAccountByUsername(this.account.getUsername());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGame() {
        GameDAO gameDAO = new GameDAO();
        this.game = gameDAO.getGameById(this.game.getId());
    }

    public PatternCard getPatternCard() {
        PatternCardDAO PatternCardDAO = new PatternCardDAO();
        patternCard = PatternCardDAO.getSelectedPatterncardOfPlayer(this);
        return patternCard;
    }

    public void setPatternCard(PatternCard patterncard) {
        this.patternCard = patterncard;
    }

    public ArrayList<PatternCard> getOptionalPatternCards() {
        return optionalPatternCards;
    }

    public void setOptionalPatternCards(ArrayList<PatternCard> optionalPatterncards) {
        this.optionalPatternCards = optionalPatterncards;
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
