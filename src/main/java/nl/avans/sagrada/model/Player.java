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
    
    public Player() {}
    
    /**
     * Loads in a player of this object by the playerId
     * @param playerId
     */
    public Player(int playerId) {
        PlayerDAO playerDAO = new PlayerDAO();
        Player player = playerDAO.getPlayerById(playerId);
        id = player.getId();
        playerStatus = player.getPlayerStatus();
        seqnr = player.getSeqnr();
        isCurrentPlayer = player.getIsCurrentPlayer();
        account = player.getAccount();
    }
    
    /**
     * Sets the id of the object
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Sets the status of the player
     * @param playerStatus
     */
    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }
    
    /**
     * Set the seqnr of the player
     * @param seqnr
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
     * Set the account of a player
     * @param account
     */
    public void setAccount(Account account) {
        this.account = account;
    }
    
    /**
     * Get a id of the player
     * @return int
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the status of a player
     * @return String
     */
    public String getPlayerStatus() {
        return playerStatus;
    }
    
    /**
     * Get the sequentie nummrt
     * @return int
     */
    public int getSeqnr() {
        return(seqnr);
    }
    
    /**
     * Returns if the currentPlayer is the current player 
     * who's turn it is
     * @return
     */
    public boolean getIsCurrentPlayer() {
        return isCurrentPlayer;
    }
    
    /**
     * Get the account of the current player
     * @return
     */
    public Account getAccount() {
        return(account);
    }
}
