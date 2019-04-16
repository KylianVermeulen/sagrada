package nl.avans.sagrada.model;

public class Game {
    private int id;
    private Player turnPlayer;

    private Player[] players;
    private FavorToken[] favorTokens;
    private GameDie[] gameDie;
    private PublicObjectiveCard[] publicObjectiveCards;

    /**
     * Partial constructor
     *
     * @param id int
     */
    public Game(int id) {
        this.id = id;
    }

    /**
     * Get id from Game
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Set id to Game
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get turnPlayer from Game
     *
     * @return Player
     */
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    /**
     * Set turnPlayer to Game
     * @param turnPlayer Player
     */
    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    /**
     * Get players from Game
     *
     * @return Player[]
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Set players to Game
     *
     * @param players Player[]
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Get favorTokens from Game
     *
     * @return FavorToken[]
     */
    public FavorToken[] getFavorTokens() {
        return favorTokens;
    }

    /**
     * Set favorTokens to Game
     *
     * @param favorTokens FavorToken[]
     */
    public void setFavorTokens(FavorToken[] favorTokens) {
        this.favorTokens = favorTokens;
    }

    /**
     * Get gameDice from Game
     *
     * @return GameDie[]
     */
    public GameDie[] getGameDie() {
        return gameDie;
    }

    /**
     * Set gameDice to Game
     *
     * @param gameDie GameDie[]
     */
    public void setGameDie(GameDie[] gameDie) {
        this.gameDie = gameDie;
    }

    /**
     * Get publicObjectiveCards from Game
     *
     * @return PublicObjectiveCard[]
     */
    public PublicObjectiveCard[] getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    /**
     * Set publicObjectiveCards to Game
     *
     * @param publicObjectiveCards PublicObjectiveCard[]
     */
    public void setPublicObjectiveCards(PublicObjectiveCard[] publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
    }
}
