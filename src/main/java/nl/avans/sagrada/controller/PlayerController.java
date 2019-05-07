package nl.avans.sagrada.controller;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.PublicObjectiveCardDao;
import nl.avans.sagrada.dao.ToolcardDao;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.*;
import nl.avans.sagrada.view.popups.Alert;
import nl.avans.sagrada.view.popups.AlertType;

public class PlayerController {
    private MyScene myScene;
    private Player player;

    public PlayerController(MyScene myScene) {
        this.myScene = myScene;
    }

    /**
     * Handles the logic behind a toolcard payment. The method first checks if a player has already
     * paid for a toolcard before or not, and if the player has sufficient funds.
     * <p>
     * If the toolcard has not received payment before, the player will hand over one favortoken as
     * payment for the toolcard. This toolcard's status will then be set to "has already been paid
     * for before". </br>
     * If the toolcard has received payment before, then the player will hand over two favortokens
     * as payment for the toolcard.
     * </p>
     * <p>
     * If the player has insufficient funds, a message will appear on screen informing the player
     * about their lack of funds, and the player will not be able to use this toolcard.
     * </p>
     * 
     * @param game Game
     * @param toolcard Toolcard
     */
    public void actionPayForToolcard(Toolcard toolcard) {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        ToolcardDao toolcardDao = new ToolcardDao();
        toolcardDao.toolcardHasPayment(toolcard, player.getGame());

        if (player.getFavorTokens().size() > 0) {
            if (!toolcard.hasBeenPaidForBefore()) {
                ArrayList<FavorToken> newFavorTokens = player.getFavorTokens();
                favorTokenDao.setFavortokensForToolcard(newFavorTokens.get(0), toolcard,
                        player.getGame());
                newFavorTokens.remove(0);
                player.setFavorTokens(newFavorTokens);
                toolcard.setHasBeenPaidForBefore(true);
            } else {
                if (player.getFavorTokens().size() > 1) {
                    ArrayList<FavorToken> newFavorTokens = player.getFavorTokens();
                    favorTokenDao.setFavortokensForToolcard(newFavorTokens.get(0), toolcard,
                            player.getGame());
                    newFavorTokens.remove(0);
                    favorTokenDao.setFavortokensForToolcard(newFavorTokens.get(0), toolcard,
                            player.getGame());
                    newFavorTokens.remove(0);
                    player.setFavorTokens(newFavorTokens);
                } else {
                    Alert alert = new Alert("Te weinig betaalstenen",
                            "Je hebt niet genoeg betaalstenen om deze kaart te kopen!", AlertType.ERROR);
                    myScene.addAlertPane(alert);
                    return;
                }
            }
        } else {
            Alert alert = new Alert("Te weinig betaalstenen",
                    "Je hebt niet genoeg betaalstenen om deze kaart te kopen!", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
    }

    public void actionJoinGame(Account account, Game game) {
        player = new PlayerDao().getPlayerByAccountAndGame(account, game);
        player.setGame(game);
        if (player.getPatternCard() == null) {
            viewOptionalPatternCards();
        }
    }

    public void viewOptionalPatternCards() {
        Pane pane = new Pane();
        ArrayList<PatternCard> patternCards =
                new PatternCardDao().getOptionalPatternCardsOfPlayer(player);
        PatternCardSelectionView patternCardSelectionView = new PatternCardSelectionView(this);
        patternCardSelectionView.setOptionalPatternCards(patternCards);
        patternCardSelectionView.render();
        pane.getChildren().add(patternCardSelectionView);
        myScene.setContentPane(pane);
    }

    public void actionSelectPatternCard(PatternCard patternCard) {
        PlayerDao playerDao = new PlayerDao();
        player.setPatternCard(patternCard);
        playerDao.updateSelectedPatternCard(player, patternCard);
        player.generateFavorTokens();
        viewPatternCardOfPlayer(player);
    }

    /**
     * Example code
     *
     * @param game the game to view the three Toolcards of.
     */
    public void viewToolcards(Game game) {
        ToolcardDao toolcardDao = new ToolcardDao();
        BorderPane pane = new BorderPane();
        ToolCardView[] toolcardViews = new ToolCardView[3];
        Toolcard[] toolcards = toolcardDao.getToolcardsOfGame(game).toArray(new Toolcard[3]);
        for (int index = 0; index < toolcardViews.length; index++) {
            toolcardViews[index] = new ToolCardView(this);
            toolcardViews[index].setToolCard(toolcards[index]);
            toolcardViews[index].render();
        }

        BorderPane.setMargin(toolcardViews[0], new Insets(0, 5, 0, 0));
        BorderPane.setMargin(toolcardViews[1], new Insets(0, 5, 0, 5));
        BorderPane.setMargin(toolcardViews[2], new Insets(0, 0, 0, 5));
        pane.setLeft(toolcardViews[0]);
        pane.setCenter(toolcardViews[1]);
        pane.setRight(toolcardViews[2]);
        myScene.setContentPane(pane);
    }

    /**
     * Example code
     *
     * @param game the game to view Toolcard of.
     * @param selection the selected Toolcard to view.
     */
    public void viewToolcard(Game game, int selection) {
        ToolcardDao toolcardDao = new ToolcardDao();

        Pane pane = new Pane();
        ToolCardView toolCardView = new ToolCardView(this);
        toolCardView.setToolCard(toolcardDao.getToolcardsOfGame(game).get(selection));
        toolCardView.render();

        pane.getChildren().add(toolCardView);
        myScene.setContentPane(pane);
    }

    /**
     * Example code
     *
     * @param game Game
     */
    public void viewPublicObjectiveCards(Game game) {
        BorderPane pane = new BorderPane();
        PublicObjectiveCardDao publicObjectiveCardDao = new PublicObjectiveCardDao();
        PublicObjectiveCardView[] publicObjectiveCardViews = new PublicObjectiveCardView[3];
        PublicObjectiveCard[] publicObjectiveCards = publicObjectiveCardDao
                .getAllPublicObjectiveCardsOfGame(game).toArray(new PublicObjectiveCard[3]);
        for (int index = 0; index < publicObjectiveCardViews.length; index++) {
            publicObjectiveCardViews[index] = new PublicObjectiveCardView(this);
            publicObjectiveCardViews[index].setPublicObjectiveCard(publicObjectiveCards[index]);
            publicObjectiveCardViews[index].render();
        }

        BorderPane.setMargin(publicObjectiveCardViews[0], new Insets(0, 5, 0, 0));
        BorderPane.setMargin(publicObjectiveCardViews[1], new Insets(0, 5, 0, 5));
        BorderPane.setMargin(publicObjectiveCardViews[2], new Insets(0, 0, 0, 5));
        pane.setLeft(publicObjectiveCardViews[0]);
        pane.setCenter(publicObjectiveCardViews[1]);
        pane.setRight(publicObjectiveCardViews[2]);
        myScene.setContentPane(pane);
    }

    /**
     * Test function for roundTrack
     */
    public void viewRoundTrack() {
        GameDie gameDie1 = new GameDie(1, "geel", 1);
        GameDie gameDie2 = new GameDie(2, "blauw", 3);
        GameDie gameDie3 = new GameDie(3, "rood", 5);

        RoundTrack roundTrack = new RoundTrack();
        roundTrack.addGameDie(gameDie1, 1);
        roundTrack.addGameDie(gameDie2, 1);
        roundTrack.addGameDie(gameDie3, 1);

        roundTrack.addGameDie(gameDie1, 2);
        roundTrack.addGameDie(gameDie3, 2);

        roundTrack.addGameDie(gameDie1, 3);
        roundTrack.addGameDie(gameDie2, 3);

        roundTrack.addGameDie(gameDie2, 4);
        roundTrack.addGameDie(gameDie3, 4);

        roundTrack.addGameDie(gameDie1, 5);
        roundTrack.addGameDie(gameDie3, 5);

        RoundTrackView roundTrackView = new RoundTrackView(roundTrack);
        roundTrackView.render();
        myScene.setContentPane(roundTrackView);
    }

    /**
     * Example code
     */
    public void viewPrivateObjectiveCard() {
        Pane pane = new Pane();
        PrivateObjectiveCardView privateObjectiveCardView = new PrivateObjectiveCardView();
        privateObjectiveCardView.setPlayer(this.player);
        privateObjectiveCardView.render();

        pane.getChildren().add(privateObjectiveCardView);
        myScene.setContentPane(pane);
    }

    /**
     * Example code
     *
     * @param player the player to view the PatternCard of.
     */
    public void viewPatternCardOfPlayer(Player player) {
        Pane pane = new Pane();
        PatternCard patternCard = player.getPatternCard();
        PatternCardView patternCardView = new PatternCardView(this);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();
        pane.getChildren().add(patternCardView);
        myScene.setContentPane(pane);
    }

    /**
     * Example code
     */
    public void makeDie() {
        GameDie gameDie = new GameDie(1, "geel", 6);
        DieView dieView = new DieView();
        dieView.setGameDie(gameDie);
        dieView.render();
        myScene.setContentPane(dieView);
    }

    /**
     * Method that adds a message to the view and database
     *
     * @param text String
     */
    public void actionSendMessage(String text) {
        ChatlineDao chatlineDao = new ChatlineDao();
        Chatline chatline = new Chatline(player, text);
        chatlineDao.getTime(chatline);

        if (!text.matches("")) {
            if (chatlineDao.timeExistsOfPlayer(chatline) == false) {
                chatlineDao.addChatline(chatline);
                ChatLineView chatview = new ChatLineView(this);
                chatview.addExistingMessages(player.getChatlines());
                chatview.addMessage(chatline);
                player.addChatline(chatline);
                myScene.setContentPane(chatview);
            } else {
                Alert alert = new Alert("Waarschuwing",
                        "Je mag maar 1 keer per seconde een bericht versturen!", AlertType.ERROR);
                myScene.addAlertPane(alert);
            }
        } else {
            Alert alert =
                    new Alert("Waarschuwing", "Je bericht moet tekst bevatten", AlertType.ERROR);
            myScene.addAlertPane(alert);
        }
    }

    /**
     * Method to view the chat
     */
    public void viewChat() {
        ChatLineView chatlineview = new ChatLineView(this);
        myScene.setContentPane(chatlineview);
    }
}
