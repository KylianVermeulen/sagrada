package nl.avans.sagrada.model;

import java.util.ArrayList;
import java.util.Random;

import nl.avans.sagrada.dao.GameDAO;
import nl.avans.sagrada.dao.PlayerDAO;

public class Game {
    private int id;
    private Player turnPlayer;

    private String gamemode;
    private ArrayList<Player> players;
    private Player startPlayer;
    private FavorToken[] favorTokens;
    private GameDie[] gameDie;
    private PublicObjectiveCard[] publicObjectiveCards;
    private final String[] privateObjectiveCardColors = {"blauw", "geel", "groen", "paars", "rood"};
    
    public static final String GAMEMODE_NORMAL = "normal";
    public static final String GAMEMODE_GENERATED = "generate";

    /**
     * Partial constructor
     *
     * @param id int
     */
    public Game(int id) {
        this.id = id;
        gamemode = GAMEMODE_NORMAL;
        GameDAO gameDao = new GameDAO();
        players = gameDao.getPlayersOfGame(this);
    }
    
    public Game() {
        players = new ArrayList<>();
        gamemode = GAMEMODE_NORMAL;
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
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Set players to Game
     *
     * @param players Player[]
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    
    public boolean isActive() {
        for (Player player: players) {
            String playerStatus = player.getPlayerStatus();
            if (playerStatus.equals("aborted") || playerStatus.equals("finished")) {
                return false;
            }
        }
        return true;
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
    
    /**
     * Sets the start player of a game
     * @param player
     */
    public void setStartPlayer(Player player) {
        this.startPlayer = player;
    }
    
    /**
     * Gets the start player of a game
     * @return
     */
    public Player getStartPlayer() {
        return startPlayer;
    }
    
    /**
     * Gets a private color that is not in use by a player
     * @return String
     */
    public String getRandomAvailablePrivateColor() {
        boolean hasNotChooseRandomCard = false;
        Random random = new Random();
        int amountOfColors = privateObjectiveCardColors.length;
        while (!hasNotChooseRandomCard) {
            int randomArrayPostition = random.nextInt(amountOfColors);
            String privateColor = privateObjectiveCardColors[randomArrayPostition];
            for (Player player: players) {
                if (player.getPrivateObjectivecardColor().equals(privateColor)) {
                    continue;
                }
            }
            return privateColor;
        }
        return "";
    }
    
    /**
     * Sets the gamemode of the game
     * @param gamemode
     */
    public void setGamemode(String gamemode) {
        if (gamemode.equals(GAMEMODE_NORMAL) || gamemode.equals(GAMEMODE_GENERATED)) {
            this.gamemode = gamemode;
        }
        else {
            System.out.println("Wrong gamemode");
        }
    }
    
    /**
     * Gets the gamemode of a game
     * @return String
     */
    public String getGamemode() {
        return gamemode;
    }
    
    /**
     * Sets for all the players of the game there optional patternCards
     */
    public void setOptionPatternCardsForPlayers() {
        //  Set the patterncard for all players
    }
    
    public void cancel() {
        ArrayList<Player> players = getPlayers();
        PlayerDAO playerDao = new PlayerDAO();
        for (Player player: players) {
            player.setPlayerStatus(Player.STATUS_ABORT);
            playerDao.updatePlayer(player);
        }
    }
}
