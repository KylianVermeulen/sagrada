package nl.avans.sagrada.model;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;

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
    private ArrayList<Chatline> chatlines;
    private int score;
    private boolean cheatmode = false;
    private Color playerColor;

    public Player() {
        chatlines = new ArrayList<>();
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
     * @return The patterncard of this player.
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

    public void generateFavorTokens() {
        ArrayList<FavorToken> favorTokens = new ArrayList<>();
        for (int i = 0; i < patternCard.getDifficulty(); i++) {
            FavorTokenDao favorTokenDao = new FavorTokenDao();
            int favorTokenId = favorTokenDao.getNextFavorTokenId();

            FavorToken favorToken = new FavorToken(favorTokenId, this);
            favorTokenDao.addFavorToken(favorToken);
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
     * @return True when cheatmode is enabled.
     */
    public boolean isCheatmode() {
        return cheatmode;
    }

    /**
     * @param cheatmode Enable or disable cheatmode.
     */
    public void setCheatmode(boolean cheatmode) {
        this.cheatmode = cheatmode;
    }

    /**
     * addChatline to player
     */
    public void addChatline(Chatline chatline) {
        chatlines.add(chatline);
    }

    /**
     * get chatlines from player
     */
    public ArrayList<Chatline> getChatlines() {
        return chatlines;
    }

    /**
     * set chatlines for player
     */
    public void setChatlines(ArrayList<Chatline> chatlines) {
        this.chatlines = chatlines;
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
     * Calculate the score for this player. Gets -1 score for each empty pattern card field. Gets +1
     * score for each favor token. Gets rewardScore for each public objective card.
     */
    public int calculateScore(boolean privateObjectiveCard) {
        int score = 0;

        if (privateObjectiveCard) {
            PatternCardField[][] patternCardFields = patternCard.getPatternCardFields();
            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH;
                    x++) { // Basic calculations for pattern card fields
                for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
                    if (!patternCardFields[x][y]
                            .hasDie()) { // for each empty pattern card field -1 score
                        score -= 1;
                    } else { // pattern card field has die
                        if (patternCardFields[x][y].getDie().getColor()
                                .equals(privateObjectivecardColor)) { // for each die that has private objective card color +1 score
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
        return score;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(int i){
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
            newSeqnr = newSeqnr + (gameSize * 2 - 1);
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
                newSeqnr = 2;
            } else if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 4;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 6;
            }
        } else if (newSeqnr == 4) { // SEQNR: 4
            if (gameSize == 2) { //GAMESIZE: 2
                newSeqnr = 1;
            } else if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 3;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 5;
            }
        } else if (newSeqnr == 5) { // SEQNR: 5
            if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 2;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 4;
            }
        } else if (newSeqnr == 6) { // SEQNR: 6
            if (gameSize == 3) { // GAMESIZE: 3
                newSeqnr = 1;
            } else if (gameSize == 4) { // GAMESIZE: 4
                newSeqnr = 3;
            }
        } else if (newSeqnr == 7) {
            newSeqnr = 2;
        } else if (newSeqnr == 8) {
            newSeqnr = 1;
        }
        setSeqnr(newSeqnr);
        new PlayerDao().updatePlayer(this);
    }
}
