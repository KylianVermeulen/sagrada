package nl.avans.sagrada.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.dao.DieDao;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.PublicObjectiveCardDao;
import nl.avans.sagrada.dao.ToolCardDao;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.task.GetPublicObjectiveCardTask;
import nl.avans.sagrada.task.GetRoundTrackDiceTask;

public class Game {
    public static final String GAMEMODE_NORMAL = "normal";
    public static final String GAMEMODE_GENERATED = "generate";
    private final String[] privateObjectiveCardColors = {"blauw", "geel", "groen", "paars", "rood"};
    private final String[] playerColors = {"geel", "blauw", "rood", "groen"};
    private int id;
    private Player turnPlayer;
    private int round = 1;
    private String gamemode;
    private ArrayList<Player> players;
    private Player startPlayer;
    private FavorToken[] favorTokens;
    private ArrayList<GameDie> gameDice;
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
        gameDice = new ArrayList<>();
        round = 1;
        gamemode = GAMEMODE_NORMAL;
    }

    /**
     * Adds the dice to the database and the array in game
     */
    public void addDice() {
        DieDao dieDao = new DieDao();
        GameDieDao gameDieDao = new GameDieDao();
        for (Die die : dieDao.getDice()) {
            GameDie gameDie = new GameDie(die, new Random().nextInt(6) + 1);
            gameDice.add(gameDie);
            gameDieDao.addDie(this, gameDie);
        }
    }

    /**
     * Returns the gamedice for this game.
     *
     * @return ArrayList GameDie
     */
    public ArrayList<GameDie> getGameDice() {
        return gameDice;
    }

    /**
     * Set gameDice to Game
     *
     * @param gameDice GameDie[]
     */
    public void setGameDice(ArrayList<GameDie> gameDice) {
        this.gameDice = gameDice;
    }

    /**
     * Adds random rounds to the gameDice
     */
    public void addRandomRoundsToGameDice() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < (getPlayers().size() * 2 + 1); j++) {
                boolean hasDie = false;
                while (!hasDie) {
                    GameDie randomGameDie = gameDice.get(new Random().nextInt(gameDice.size()));
                    GameDie checkDie = new GameDieDao().getDie(this, randomGameDie);
                    if (checkDie.getRound() == 0) {
                        randomGameDie.setRound(i);
                        new GameDieDao().updateDie(this, randomGameDie);
                        hasDie = true;
                    }
                }
            }
        }
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
    public ArrayList<GameDie> gameDice() {
        return gameDice;
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
    
    public GetPublicObjectiveCardTask getPublicObjectiveCardTask() {
        GetPublicObjectiveCardTask getPublicObjectiveCardTask = new GetPublicObjectiveCardTask(this);
        return getPublicObjectiveCardTask;
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

        int randomNumber1 = 0;
        int randomNumber2 = 0;
        int randomNumber3 = 0;

        boolean foundThreeValues = false;

        while (!foundThreeValues) {
            randomNumber1 = 11;
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
     * assign three random public objectivecards to a game. first the method makes three random
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

        int randomNumber1 = 0;
        int randomNumber2 = 0;
        int randomNumber3 = 0;

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
        if (turnPlayer == null) {
            GameDao gameDao = new GameDao();
            Game game = gameDao.getGameById(getId());
            turnPlayer = game.getTurnPlayer();
        }
        Player currentPlayer = turnPlayer;
        int oldSeqnr = currentPlayer.getSeqnr();
        currentPlayer.setNextSeqnr();

        for (int i = 0; i < players.size(); i++) {
            Player playerNextTurn = players.get(i);
            if (oldSeqnr < (players.size() * 2)) {
                if (playerNextTurn.getSeqnr() == oldSeqnr + 1) {
                    if (currentPlayer.getId() != playerNextTurn.getId()) {
                        updatePlayer(currentPlayer, playerNextTurn);
                    }
                }
            } else {
                if (currentPlayer.getId() != playerNextTurn.getId()) {
                    updatePlayer(playerNextTurn, currentPlayer);
                    // The player next turn contains seqnr 2
                    // So we switch those 2

                    nextRound();
                }
            }
        }
    }

    /**
     * Updates player to currentPlayer
     *
     * @param currentPlayer Player
     * @param playerNextTurn Player
     */
    private void updatePlayer(Player currentPlayer, Player playerNextTurn) {
        PlayerDao playerDao = new PlayerDao();
        currentPlayer.setIsCurrentPlayer(false);
        playerDao.updatePlayer(currentPlayer);

        setTurnPlayer(playerNextTurn);
        new GameDao().updateGame(this);

        playerNextTurn.setIsCurrentPlayer(true);
        playerDao.updatePlayer(playerNextTurn);
    }

    /**
     * Gets the dice from a round
     *
     * @return ArrayList<GameDie>
     */
    public ArrayList<GameDie> getRoundDice() {
        return new GameDieDao().getAvailableDiceOfRound(this);
    }

    /**
     * Gets the current round the game is on
     *
     * @return int
     */
    public int getRound() {
        GameDao gameDao = new GameDao();
        return gameDao.getCurrentRound(this);
    }

    /**
     * Sets the current round of a game
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Put the game in the second round by calling the placeDiceOfOfferTableOnRoundTrack
     */
    public void nextRound() {
        GameDao gameDao = new GameDao();
        placeDiceOfOfferTableOnRoundTrack();
        round = gameDao.getCurrentRound(this);
    }

    /**
     * Places all dices that are left for offer on the roundTrack
     */
    private void placeDiceOfOfferTableOnRoundTrack() {
        GameDieDao gameDieDao = new GameDieDao();
        ArrayList<GameDie> dice = getRoundDice();

        for (GameDie die : dice) {
            die.setOnRoundTrack(true);
            die.setRound(round);
            gameDieDao.updateDie(this, die);
        }
    }

    /**
     * Gets all dices that are on the roundTrack
     *
     * @return ArrayList<GameDie>
     */
    public GetRoundTrackDiceTask getTrackDiceTask() {
        return new GetRoundTrackDiceTask(this);
    }

    /**
     * Rerolls the dice eyes from the dice in the dice offer for the current turn.
     */
    public void rerollRoundDice() {
        GameDieDao gameDieDao = new GameDieDao();
        for (GameDie gameDie : gameDieDao.getAvailableDiceOfRound(this)) {
            gameDie.setEyes(new Random().nextInt(6) + 1);
            gameDieDao.updateDieEyes(this, gameDie);
        }
    }

    /**
     * Finishes a game by changing the status of all players
     */
    public void finishGame() {
        Player startPlayer = getPlayers().get(0);
        ArrayList<Player> players = getPlayers();
        PlayerDao playerDao = new PlayerDao();
        for (Player player : players) {
            player.setPlayerStatus("uitgespeeld");
            player.setScore(player.calculateScore(true, true));
            playerDao.updatePlayer(player);
        }
    }

    /**
     * The method will return the player with the best score by calculating each players score with
     * private objective card.
     *
     * @return The player.
     */
    public Player getPlayerWithBestScore() {
        Player player = null;
        int playerScore = -21;
        for (Player playerLoop : getPlayers()) {
            int loopScore = playerLoop.calculateScore(true, false);
            if (player == null) {
                player = playerLoop;
                playerScore = loopScore;
            } else {
                if (loopScore > playerScore) {
                    player = playerLoop;
                    playerScore = loopScore;
                } else if (loopScore == playerScore) {
                    if (player.getId() < playerLoop.getId()) {
                        player = playerLoop;
                        playerScore = loopScore;
                    }
                }
            }
        }
        return player;
    }

    /**
     * Assign 24 favor tokens to a game in the database.
     */
    public void assignFavorTokens() {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        for (int i = 0; i < 24; i++) {
            FavorToken favorToken = new FavorToken();
            favorToken.setId(favorTokenDao.getNextFavorTokenId());
            favorToken.setGame(this);
            favorTokenDao.addFavorToken(favorToken);
        }
    }
}
