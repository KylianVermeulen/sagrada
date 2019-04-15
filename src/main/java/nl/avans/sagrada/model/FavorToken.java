package nl.avans.sagrada.model;

public class FavorToken {
    private int id;
    private int idToolcard;

    private Game game;
    private Player player;
    private Toolcard toolcard;

    /**
     * Full constructor for getFavortokensOfPlayer
     *
     * @param id int
     * @param idToolcard int
     * @param player Player
     */
    public FavorToken(int id, int idToolcard, Player player) {
        this.id = id;
        this.idToolcard = idToolcard;
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

    public int getIdToolcard() {
        return idToolcard;
    }

    public void setIdToolcard(int idToolcard) {
        this.idToolcard = idToolcard;
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
