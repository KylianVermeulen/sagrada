package nl.avans.sagrada.model;

import java.util.ArrayList;

import nl.avans.sagrada.dao.PlayerDAO;

public class Player {
    private int id;
    private String playerStatus;
    private int seqnr;
    private boolean isCurrentPlayer;
    private String privateObjectivecardColor;
    private PatternCard patternCard;
    private PatternCard[] optionalPatterncards;
    private Game game;
    private ArrayList<FavorToken> favorTokens;
    private boolean cheatmode = false;
    private Account account;
    
    public Player() {
        
    }
    
    public Player(int playerId) {
        PlayerDAO playerDAO = new PlayerDAO();
        Player player = playerDAO.getPlayerById(playerId);
        id = player.getId();
        playerStatus = player.getPlayerStatus();
        seqnr = player.getSeqnr();
        isCurrentPlayer = player.getIsCurrentPlayer();
        account = player.getAccount();
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }
    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }
    public void setIsCurrentPlayer(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public int getId() {
        return id;
    }
    public String getPlayerStatus() {
        return playerStatus;
    }
    public int getSeqnr() {
        return(seqnr);
    }
    public boolean getIsCurrentPlayer() {
        return isCurrentPlayer;
    }
    public Account getAccount() {
        return(account);
    }
}
