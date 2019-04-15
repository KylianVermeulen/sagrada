package nl.avans.sagrada.model;

public class FavorToken {
    private int id;
    private int idGame;
    private int idPlayer;
    private int idToolcard;

    private Player player;
    private Toolcard toolcard;

    public FavorToken(int id, int idGame, int idPlayer, int idToolcard) {
        this.id = id;
        this.idGame = idGame;
        this.idPlayer = idPlayer;
        this.idToolcard = idToolcard;
    }

    public void save() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdToolcard() {
        return idToolcard;
    }

    public void setIdToolcard(int idToolcard) {
        this.idToolcard = idToolcard;
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
