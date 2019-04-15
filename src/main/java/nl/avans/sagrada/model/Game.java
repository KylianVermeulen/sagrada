package nl.avans.sagrada.model;

public class Game {
    private int id;
    private int turnIdPlayer;

    private Player turnPlayer;
    private Player[] players;
    private FavorToken[] favorTokens;
    private GameDie[] gameDie;
    private PublicObjectiveCard[] publicObjectiveCards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTurnIdPlayer() {
        return turnIdPlayer;
    }

    public void setTurnIdPlayer(int turnIdPlayer) {
        this.turnIdPlayer = turnIdPlayer;
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public FavorToken[] getFavorTokens() {
        return favorTokens;
    }

    public void setFavorTokens(FavorToken[] favorTokens) {
        this.favorTokens = favorTokens;
    }

    public GameDie[] getGameDie() {
        return gameDie;
    }

    public void setGameDie(GameDie[] gameDie) {
        this.gameDie = gameDie;
    }

    public PublicObjectiveCard[] getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setPublicObjectiveCards(PublicObjectiveCard[] publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
    }
}
