package nl.avans.sagrada.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.PublicObjectiveCardDao;
import nl.avans.sagrada.dao.ToolCardDao;
import nl.avans.sagrada.model.toolcard.ToolCard;

public class Game {
    public static final String GAMEMODE_NORMAL = "normal";
    public static final String GAMEMODE_GENERATED = "generate";
    private final String[] privateObjectiveCardColors = {"blauw", "geel", "groen", "paars", "rood"};
    private final String[] playerColors = {"geel", "blauw", "rood", "groen"};
    private int id;
    private Player turnPlayer;
    private String gamemode;
    private ArrayList<Player> players;
    private Player startPlayer;
    private FavorToken[] favorTokens;
    private GameDie[] gameDie;
    private PublicObjectiveCard[] publicObjectiveCards;
    private ArrayList<ToolCard> toolCards;
    private Timestamp creationDate;

    public Game(int id) {
        this.id = id;
        GameDao gameDao = new GameDao();
        ToolCardDao toolCardDao = new ToolCardDao();

        players = gameDao.getPlayersOfGame(this);
        toolCards = toolCardDao.getToolCardsOfGame(this);
    }

    public Game() {
        players = new ArrayList<>();
        gamemode = GAMEMODE_NORMAL;
    }

    /**
     * Get id from Game
     *
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
     *
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

    /**
     * Checks if a game is active
     *
     * @return boolean
     */
    public boolean isActive() {
        for (Player player : players) {
            String playerStatus = player.getPlayerStatus();
            if (playerStatus.equals("afgebroken") || playerStatus.equals("uitgespeeld")
                    || playerStatus.equals("uitgedaagde")) {
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
        PublicObjectiveCardDao publicObjectiveCardDao = new PublicObjectiveCardDao();
        publicObjectiveCards = publicObjectiveCardDao.getAllPublicObjectiveCardsOfGame(this)
                .toArray(new PublicObjectiveCard[3]);
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
     * Gets the start player of a game
     */
    public Player getStartPlayer() {
        return startPlayer;
    }

    /**
     * Sets the start player of a game
     */
    public void setStartPlayer(Player player) {
        this.startPlayer = player;
    }

    /**
     * Gets a private color that is not in use by a player
     *
     * @return String
     */
    public String getRandomAvailablePrivateColor() {
        boolean hasNotChooseRandomCard = false;
        Random random = new Random();
        int amountOfColors = privateObjectiveCardColors.length;
        while (!hasNotChooseRandomCard) {
            int randomArrayPostition = random.nextInt(amountOfColors);
            String privateColor = privateObjectiveCardColors[randomArrayPostition];
            for (Player player : players) {
                if (player.getPrivateObjectivecardColor().equals(privateColor)) {
                    continue;
                }
            }
            return privateColor;
        }
        return "";
    }

    /**
     * Get all the chatlines of a game trough the chatline dao And returns them
     *
     * @return ArrayList<Chatline>
     */
    public ArrayList<Chatline> getChatlines() {
        ArrayList<Chatline> chatlines = new ChatlineDao().getChatlinesOfGame(this);
        return chatlines;
    }

    /**
     * Gets the gamemode of a game
     *
     * @return String
     */
    public String getGamemode() {
        return gamemode;
    }

    /**
     * Sets the gamemode of the game
     */
    public void setGamemode(String gamemode) {
        if (gamemode.equals(GAMEMODE_NORMAL) || gamemode.equals(GAMEMODE_GENERATED)) {
            this.gamemode = gamemode;
        } else {
            System.out.println("Wrong gamemode");
        }
    }

    /**
     * Sets for all the players of the game there optional patternCards
     */
    public void setOptionPatternCardsForPlayers() {
        ArrayList<PatternCard> optionalPatternCards;
        Random random = new Random();
        if (gamemode.equals(GAMEMODE_NORMAL)) {
            optionalPatternCards = new PatternCardDao().getAllStandardPatterncards();
        } else {
            optionalPatternCards = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                PatternCardDao patternCardDao = new PatternCardDao();
                int patternCardId = patternCardDao.getNextPatternCardId();
                PatternCard patternCard = new PatternCard(patternCardId, false);
                patternCardDao.addPatterncard(patternCard);
                patternCard.saveNewPatternCardFields();
                optionalPatternCards.add(patternCard);
            }
        }
        for (Player player : players) {
            ArrayList<PatternCard> optionalPatternCardsPlayer = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int randomInt = random.nextInt(optionalPatternCards.size());
                optionalPatternCardsPlayer.add(optionalPatternCards.get(randomInt));
                optionalPatternCards.remove(randomInt);
            }
            player.setOptionalPatternCards(optionalPatternCardsPlayer);
        }
    }

    /**
     * Cancels a game by updating all player status to abort
     */
    public void cancel() {
        ArrayList<Player> players = getPlayers();
        PlayerDao playerDao = new PlayerDao();
        for (Player player : players) {
            player.setPlayerStatus(Player.STATUS_ABORT);
            playerDao.updatePlayer(player);
        }
    }

    /**
     * Returns the toolcards of the current game
     *
     * @return ArrayList<ToolCard>
     */
    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    /**
     * Set the toolcards of the current game
     */
    public void setToolCards(ArrayList<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }

    /**
     * Assigns three random toolcards to the current game (given as parameter).
     * <p>
     * Firstly, the method makes a total of three random (double) numbers, which in turn are
     * converted into integers after rounding the doubles. </br> Then, the method checks whether the
     * toolcardID from the array is the first entry of the array or not, and, if not, checks if the
     * current id is the same as the previous array entry id. If these two are the same, the current
     * id gets an increase of one in order to make sure the two ids are not the same. </br> If the
     * new value of the current toolcard id is higher than 12, the id gets decreased by 2.
     * </br>
     * If the current toolcard id is the third array entry, the method ensures that this new value
     * (as described above) is not the same as the first AND second array entries.
     * </p>
     * <p>
     * If, before the scenario as pictured above takes place, the current toolcard id is the third
     * entry, and the current toolcard id is the same as the first array entry, the same action as
     * above takes place, except now another increase in id happens. </br> If, again, this value is
     * higher than 12, the value gets a decrease of two.
     * </p>
     */
    public void assignRandomToolCards() {
        ToolCardDao toolCardDao = new ToolCardDao();

        Random random = new Random();

        int min = 1;
        int max = 12;

        int randomNumber1 = random.nextInt((max - min) + 1) + min;
        int randomNumber2 = random.nextInt((max - min) + 1) + min;
        int randomNumber3 = random.nextInt((max - min) + 1) + min;

        boolean foundThreeValues = false;

        while (!foundThreeValues) {
            randomNumber1 = random.nextInt((max - min) + 1) + min;
            randomNumber2 = random.nextInt((max - min) + 1) + min;
            randomNumber3 = random.nextInt((max - min) + 1) + min;
            if (randomNumber1 != randomNumber2 && randomNumber1 != randomNumber3
                    && randomNumber2 != randomNumber3) {
                foundThreeValues = true;
            }
        }
        toolCardDao.addToolCardToGame(toolCardDao.getToolCardById(randomNumber1), this);
        toolCardDao.addToolCardToGame(toolCardDao.getToolCardById(randomNumber2), this);
        toolCardDao.addToolCardToGame(toolCardDao.getToolCardById(randomNumber3), this);
    }

    /**
     * \ assign three random public objectivecards to a game. first the method makes three random
     * numbers between 1 and 10. while some numbers are the same than make new number until all
     * numbers are different. Then add the public objectivecards to the game. \ assign three random
     * public objectivecards to a game. first the method makes three random numbers between 1 and
     * 10. while some numbers are the same than make new number until all numbers are different.
     * Then add the public objectivecards to the game.
     */
    public void assignRandomPublicObjectiveCards() {
        PublicObjectiveCardDao publicObjectiveCardDao = new PublicObjectiveCardDao();

        Random random = new Random();

        int min = 1;
        int max = 10;

        int randomNumber1 = random.nextInt((max - min) + 1) + min;
        int randomNumber2 = random.nextInt((max - min) + 1) + min;
        int randomNumber3 = random.nextInt((max - min) + 1) + min;

        boolean foundThreeValues = false;

        while (!foundThreeValues) {
            randomNumber1 = random.nextInt((max - min) + 1) + min;
            randomNumber2 = random.nextInt((max - min) + 1) + min;
            randomNumber3 = random.nextInt((max - min) + 1) + min;
            if (randomNumber1 != randomNumber2 && randomNumber1 != randomNumber3
                    && randomNumber2 != randomNumber3) {
                foundThreeValues = true;
            }
        }
        publicObjectiveCardDao.addPublicObjectiveCardToGame(
                publicObjectiveCardDao.getPublicObjectiveCardById(randomNumber1), this);
        publicObjectiveCardDao.addPublicObjectiveCardToGame(
                publicObjectiveCardDao.getPublicObjectiveCardById(randomNumber2), this);
        publicObjectiveCardDao.addPublicObjectiveCardToGame(
                publicObjectiveCardDao.getPublicObjectiveCardById(randomNumber3), this);
    }

    /**
     * Checks if every player has selected a patterncard If one player has not selected a
     * patterncard we return false
     *
     * @return boolean
     */
    public boolean everyoneSelectedPatternCard() {
        ArrayList<Player> players = getPlayers();
        for (Player player : players) {
            PatternCardDao PatternCardDao = new PatternCardDao();
            PatternCard patternCard = PatternCardDao.getSelectedPatterncardOfPlayer(player);
            player.setPatternCard(patternCard);
            if (player.getPatternCard() == null) {
                return false;
            }
        }
        return true;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Sets the next player as current player in the game. Updates the player and game tables in the
     * database.
     */
    public void setNextPlayer() {
        Player currentPlayer = turnPlayer;
        int oldSeqnr = currentPlayer.getSeqnr();
        currentPlayer.setNextSeqnr();

        for (int i = 0; i < players.size(); i++) {
            Player playerNextTurn = players.get(i);
            if (oldSeqnr != (players.size() * 2)) {
                if (playerNextTurn.getSeqnr() == oldSeqnr + 1) {
                    if (currentPlayer != playerNextTurn) {
                        currentPlayer.setIsCurrentPlayer(false);
                        new PlayerDao().updatePlayer(currentPlayer);

                        setTurnPlayer(playerNextTurn);
                        new GameDao().updateGame(this);

                        playerNextTurn.setIsCurrentPlayer(true);
                        new PlayerDao().updatePlayer(playerNextTurn);
                    }
                }
            } else {
                if (playerNextTurn.getSeqnr() == 1) {
                    currentPlayer.setIsCurrentPlayer(false);
                    new PlayerDao().updatePlayer(currentPlayer);

                    setTurnPlayer(playerNextTurn);
                    new GameDao().updateGame(this);

                    playerNextTurn.setIsCurrentPlayer(true);
                    new PlayerDao().updatePlayer(playerNextTurn);
                }
            }
        }
    }
}
