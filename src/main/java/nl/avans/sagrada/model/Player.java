package nl.avans.sagrada.model;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.ToolCardDao;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.model.toolcard.ToolCardGlasBreekTang;
import nl.avans.sagrada.task.CalculateScoreTask;
import nl.avans.sagrada.task.GetPatternCardOfPlayerTask;
import nl.avans.sagrada.task.UpdateScoreTask;

public class Player {
    public static final String STATUS_ABORT = "afgebroken";
    private int id;
    private Account account;
    private Game game;
    private String playerStatus;
    private int seqnr;
    private boolean isCurrentPlayer;
    private String privateObjectivecardColor;
    private PatternCard patternCard;
    private ArrayList<PatternCard> optionalPatternCards;
    private ArrayList<FavorToken> favorTokens;
    private int score;
    private Color playerColor;
    private boolean placedDie;

    /**
     * Empty player constructor
     */
    public Player() {
    }

    /**
     * The id is a unique identifier for each player in the database.
     *
     * @return The id of this player.
     */
    public int getId() {
        return id;
    }

    /**
     * The id is a unique identifier for each player in the database.
     *
     * @param id Must be unique in the database.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The player status of a player.
     *
     * @return The status of this player.
     */
    public String getPlayerStatus() {
        return playerStatus;
    }

    /**
     * The player status of a player.
     *
     * @param playerStatus Must be an existing status in the database.
     */
    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }

    /**
     * The seqnr is the number of which turn the player has to act.
     *
     * @return The seqnr of this player.
     */
    public int getSeqnr() {
        return seqnr;
    }

    /**
     * The seqnr is the number of wich turn the player has to act.
     *
     * @param seqnr Must be a valid turn and unique in a game.
     */
    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }

    /**
     * The current player is the player that has to act in a game.
     *
     * @param isCurrentPlayer There can only be one current player in game.
     */
    public void setIsCurrentPlayer(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }

    /**
     * The current player is the player that has to act in a game.
     *
     * @return True when current player.
     */
    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    /**
     * The private objective card color is the color the player gets point for.
     *
     * @return The private objective card color of this player.
     */
    public String getPrivateObjectivecardColor() {
        return privateObjectivecardColor;
    }

    /**
     * The private objective card color is the color the player gets point for.
     *
     * @param privateObjectivecardColor Must be unique in a game.
     */
    public void setPrivateObjectivecardColor(String privateObjectivecardColor) {
        this.privateObjectivecardColor = privateObjectivecardColor;
    }

    /**
     * The score is the count of points a player has, starts at (-20 + favor tokens)
     *
     * @return The score of this player.
     */
    public int getScore() {
        return score;
    }

    /**
     * The score is the count of points a player has, starts at (-20 + favor tokens)
     *
     * @param score Must be greater or equal than -20.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * The account is the actor of the player.
     *
     * @return The account of this player.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * The account is the actor of the player.
     *
     * @param account Must be a existing account in the database.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * The game of this player.
     *
     * @return The game of this player.
     */
    public Game getGame() {
        return game;
    }

    /**
     * The game of this player.
     *
     * @param game Must be a existing game in the database.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Get the selected patterncard of the player
     *
     * @return PatternCard
     */
    public PatternCard getPatternCard() {
        PatternCardDao PatternCardDao = new PatternCardDao();
        patternCard = PatternCardDao.getSelectedPatterncardOfPlayer(this);
        return patternCard;
    }

    /**
     * @param patterncard The patterncard of this player.
     */
    public void setPatternCard(PatternCard patterncard) {
        this.patternCard = patterncard;
    }

    public GetPatternCardOfPlayerTask getPatternCardTask() {
        GetPatternCardOfPlayerTask gpcopt = new GetPatternCardOfPlayerTask(this);
        return gpcopt;
    }

    /**
     * @return The optional pattern cards of this player from the database.
     */
    public ArrayList<PatternCard> getOptionalPatternCards() {
        PatternCardDao patternCardDao = new PatternCardDao();
        this.optionalPatternCards = patternCardDao.getOptionalPatternCardsOfPlayer(this);
        return optionalPatternCards;
    }

    /**
     * @param optionalPatterncards The optional pattern cards of this player to the database.
     */
    public void setOptionalPatternCards(ArrayList<PatternCard> optionalPatterncards) {
        this.optionalPatternCards = optionalPatterncards;
        PatternCardDao patternCardDao = new PatternCardDao();
        patternCardDao.saveOptionalPatternCardsOfPlayer(optionalPatterncards, this);
    }

    /**
     * Assign favor tokens of a game to the player.
     */
    public void assignFavorTokens() {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        ArrayList<FavorToken> favorTokens = new ArrayList<>();
        ArrayList<FavorToken> allUnusedGameFavorTokens = favorTokenDao
                .getUnusedFavorTokensOfGame(game);
        for (int i = 0; i < patternCard.getDifficulty(); i++) {
            FavorToken favorToken = allUnusedGameFavorTokens.get(0);
            allUnusedGameFavorTokens.remove(0);
            favorTokenDao.setFavortokenForPlayer(favorToken, this);
            favorTokens.add(favorToken);
        }
        this.favorTokens = favorTokens;
    }

    /**
     * @return The favor tokens of this player.
     */
    public ArrayList<FavorToken> getFavorTokens() {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        this.favorTokens = favorTokenDao.getFavortokensOfPlayer(this);
        return favorTokens;
    }

    /**
     * @param favorTokens The favor tokens of this player.
     */
    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    /**
     * Returns true when player has already placed a die this turn.
     *
     * @return Boolean
     */
    public boolean hasPlacedDie() {
        return placedDie;
    }

    /**
     * Sets true when player has already placed a die this turn.
     *
     * @param placedDie Boolean
     */
    public void setPlacedDie(boolean placedDie) {
        this.placedDie = placedDie;
    }

    /**
     * Checks if it is the first turn of the player
     *
     * @return boolean
     */
    public boolean isFirstTurn() {
        int numberOfPlayers = game.getPlayers().size();
        if (seqnr <= numberOfPlayers) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * returns the immage's of the private-objectivecard.
     */
    public String getImagePath() {
        String imagePath;
        switch (privateObjectivecardColor) {
            case "blauw":
                imagePath = "/images/privateObjectiveCardColors/blue.png";
                break;
            case "groen":
                imagePath = "/images/privateObjectiveCardColors/green.png";
                break;
            case "paars":
                imagePath = "/images/privateObjectiveCardColors/purple.png";
                break;
            case "rood":
                imagePath = "/images/privateObjectiveCardColors/red.png";
                break;
            case "geel":
                imagePath = "/images/privateObjectiveCardColors/yellow.png";
                break;
            default:
                imagePath = "er is iets mis gegaan";
        }
        return imagePath;
    }

    /**
     * Returns the task to calculate the score of a player
     *
     * @return CalculateScoreTask
     */
    public CalculateScoreTask calculateScoreTask() {
        CalculateScoreTask cst = new CalculateScoreTask(this);
        return cst;
    }

    /**
     * Calculate the score for this player. Gets -1 score for each empty pattern
     * card field. Gets +1 score for each favor token. Gets rewardScore for each public objective
     * card.
     *
     * @return int score
     */
    public int calculateScore(boolean privateObjectiveCard, boolean updateInDatabase) {
        if (patternCard == null) {
            patternCard = getPatternCard();
        }
        int score = 0;
        PatternCardField[][] patternCardFields = patternCard.getPatternCardFields(this);
        for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH;
                x++) { // Basic calculations for pattern card fields
            for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
                if (!patternCardFields[x][y]
                        .hasDie()) { // for each empty pattern card field -1 score
                    score -= 1;
                } else { // pattern card field has die
                    if (patternCardFields[x][y].getDie().getColor()
                            .equals(privateObjectivecardColor)) { // for each die that has private objective card color +1 score
                        if (privateObjectiveCard) {
                            score += 1;
                        }
                    }
                }
            }
        }

        for (FavorToken favorToken : getFavorTokens()) { // for each favor token +1 score
            score += 1;
        }

        for (PublicObjectiveCard publicObjectiveCard : game.getPublicObjectiveCards()) {
            score += publicObjectiveCard.calculateScore(patternCard);
        }
        this.score = score;

        if (updateInDatabase) {
            UpdateScoreTask ust = new UpdateScoreTask(this);
            Thread thread = new Thread(ust);
            thread.setName("Update player score");
            thread.start();
        }
        return score;
    }

    /**
     * Cheatmode Task: Calculate the score for this player. Gets -1 score for each empty pattern
     * card field. Gets +1 score for each favor token. Gets rewardScore for each public objective
     * card.
     *
     * @return int score
     */
    public int calculateScore(boolean privateObjectiveCard, boolean updateInDatabase, PatternCardField[][] patternCardFields) {
        int score = 0;
        for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH;
                x++) { // Basic calculations for pattern card fields
            for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
                if (!patternCardFields[x][y]
                        .hasDie()) { // for each empty pattern card field -1 score
                    score -= 1;
                } else { // pattern card field has die
                    if (patternCardFields[x][y].getDie().getColor()
                            .equals(privateObjectivecardColor)) { // for each die that has private objective card color +1 score
                        if (privateObjectiveCard) {
                            score += 1;
                        }
                    }
                }
            }
        }

        for (FavorToken favorToken : getFavorTokens()) { // for each favor token +1 score
            score += 1;
        }

        for (PublicObjectiveCard publicObjectiveCard : game.getPublicObjectiveCards()) {
            score += publicObjectiveCard.calculateScore(patternCard);
        }
        this.score = score;

        if (updateInDatabase) {
            UpdateScoreTask ust = new UpdateScoreTask(this);
            Thread thread = new Thread(ust);
            thread.setName("Update player score");
            thread.start();
        }
        return score;
    }

    /**
     * Method to get the player his color
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(int i) {
        switch (i) {
            case 0:
                playerColor = Color.YELLOW;
                break;
            case 1:
                playerColor = Color.BLUE;
                break;
            case 2:
                playerColor = Color.RED;
                break;
            case 3:
                playerColor = Color.GREEN;
                break;
        }
    }

    /**
     * Sets the next seqnr for this player bases on the current seqnr and the size of the game.
     */
    public void setNextSeqnr() {
        int gameSize = getGame().getPlayers().size();
        int newSeqnr = seqnr;
        if (newSeqnr == 1) { // SEQNR: 1
            if (gameSize == 2) { // GAMESIZE: 2
                newSeqnr = 4;
            } else if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 6;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 8;
            }
        } else if (newSeqnr == 2) { // SEQNR: 2
            if (gameSize == 2) { // GAMESIZE: 2
                newSeqnr = 3;
            } else if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 5;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 7;
            }
        } else if (newSeqnr == 3) { // SEQNR: 3
            if (gameSize == 2) { //GAMESIZE: 2
                newSeqnr = 1;
            } else if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 4;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 6;
            }
        } else if (newSeqnr == 4) { // SEQNR: 4
            if (gameSize == 2) { //GAMESIZE: 2
                newSeqnr = 2;
            } else if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 2;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 5;
            }
        } else if (newSeqnr == 5) { // SEQNR: 5
            if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 1;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 3;
            }
        } else if (newSeqnr == 6) { // SEQNR: 6
            if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 3;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 2;
            }
        } else if (newSeqnr == 7) {
            newSeqnr = 1;
        } else if (newSeqnr == 8) {
            newSeqnr = 4;
        }
        setSeqnr(newSeqnr);
        new PlayerDao().updatePlayer(this);
    }

    /**
     * This method will return a boolean of when a player has used a toolcard that needs a turn
     * skip.
     *
     * @return Boolean
     */
    public boolean usedToolCardThatNeedsSkipNextTurn() {
        ToolCardDao toolCardDao = new ToolCardDao();
        ToolCard toolCard = toolCardDao.getUsedToolCardOfPlayerOfRound(this);

        if (toolCard != null) {
            return (toolCard instanceof ToolCardGlasBreekTang);
        }
        return false;
    }

    /**
     * Checks if a players has already used a toolcard In the turn
     *
     * @return boolean
     */
    public boolean hasUsedToolcardInCurrentTurn() {
        PlayerDao playerDao = new PlayerDao();
        if (playerDao.hasUsedToolCardInTurn(this)) {
            return true;
        } else {
            return false;
        }
    }
}
