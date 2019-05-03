package nl.avans.sagrada.model;

public class FavorToken {
    private int id;
    private Game game;
    private Player player;
    private Toolcard toolcard;

    public FavorToken(int id, Player player) {
        this.id = id;
        this.player = player;
        this.game = player.getGame();
    }

    /**
     * The id is a unique identifier for each favor token in the database.
     *
     * @return The id of this favor token.
     */
    public int getId() {
        return id;
    }

    /**
     * The id is a unique identifier for each favor token in the database.
     *
     * @param id Must be unique in de database
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The game in which this favor token is located.
     *
     * @return The game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * The game is which this favor token is located.
     *
     * @param game Must be an existing game.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * The player of which this favor token is.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The player of which this favor token is.
     *
     * @param player Must be an existing player in the game of this favor token.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * The toolcard of which this favor token is used for.
     *
     * @return The toolcard.
     */
    public Toolcard getToolcard() {
        return toolcard;
    }

    /**
     * The toolcard of which this favor token is used for.
     *
     * @param toolcard Must be an existing toolcard in the game of this favor token.
     */
    public void setToolcard(Toolcard toolcard) {
        this.toolcard = toolcard;
    }
}
