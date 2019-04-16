package nl.avans.sagrada.model;

public class FavorToken {
    private int id;
    private Game game;
    private Player player;
    private Toolcard toolcard;

    /**
     * Partial constructor for getFavortokensOfPlayer
     *
     * @param id int
     * @param player Player
     */
    public FavorToken(int id, Player player) {
        this.id = id;
        this.player = player;
        this.game = player.getGame();
    }

    public void save() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Toolcard getToolcard() {
        return toolcard;
    }

    public void setToolcard(Toolcard toolcard) {
        this.toolcard = toolcard;
    }
}
