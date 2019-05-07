package nl.avans.sagrada.controller;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
     * Sets the player for the controller
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    

    public void viewGame() {
        Game game = player.getGame();
        Pane pane = new Pane();
        GameView gameView = new GameView(this, game, player);
        gameView.render();
        pane.getChildren().add(gameView);
        myScene.setContentPane(pane);
    }

    public void actionJoinGame(Account account, Game game) {
        player = new PlayerDao().getPlayerByAccountAndGame(account, game);
        player.setGame(game);
        if (player.getPatternCard() == null) {
            viewOptionalPatternCards();
        }
        else {
            if (!game.everyoneSelectedPatternCard()) {
                // We don't allow anyone to the game view until everyone has a patterncard
                Alert alert = new Alert("Nog even wachten", "Nog niet alle spelers hebben een patroonkaart gekozen!", AlertType.INFO);
                myScene.addAlertPane(alert);
            }
            else {
                viewGame(); 
            }
            
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
        Game game = player.getGame();
        if (!game.everyoneSelectedPatternCard()) {
            // We don't allow anyone to the game view until everyone has a patterncard
            Alert alert = new Alert("Nog even wachten", "Nog niet alle spelers hebben een patroonkaart gekozen!", AlertType.INFO);
            myScene.addAlertPane(alert);
            myScene.getAccountController().viewLobby();
        }
        else {
            viewGame(); 
        }
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
        ToolCard[] toolcards = toolcardDao.getToolcardsOfGame(game).toArray(new ToolCard[3]);
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
        PrivateObjectiveCardView privateObjectiveCardView = new PrivateObjectiveCardView(player);
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
     * Player is passing for a round
     */
    public void actionPass() {
        
    }

    /**
     * Players wants to go back to the lobby
     */
    public void exit() {
        myScene.getAccountController().viewLobby();
    }
    /**
     * Method that adds a message to the view and database
     *
     * @param text String
     */
    public void actionSendMessage(String text, ChatLineView chatlineView) {
        ChatlineDao chatlineDao = new ChatlineDao();
        Chatline chatline = new Chatline(player, text);
        Game game = player.getGame();
        chatlineDao.getTime(chatline);

        if (!text.matches("")) {
            if (chatlineDao.timeExistsOfPlayer(chatline) == false) {
                chatlineDao.addChatline(chatline);
                ArrayList<Chatline> chatlines = chatlineDao.getChatlinesOfGame(game);
                chatlineView.setChatLines(chatlines);
                chatlineView.render();
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
}
