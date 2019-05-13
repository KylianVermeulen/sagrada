package nl.avans.sagrada.controller;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.GameDao;
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

    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player for the controller
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void viewGame() {
        // Refresh game & player object
        int gameId = player.getGame().getId();
        player = new PlayerDao().getPlayerById(player.getId());
        Game game = new GameDao().getGameById(gameId);
        player.setGame(game);

        if (player.isCurrentPlayer()) {
            game.setTurnPlayer(player);
            Alert alert = new Alert("Speel je beurt", "Je bent nu aan de beurt!", AlertType.SUCCES);
            myScene.addAlertPane(alert);
        }

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
        } else {
            if (!game.everyoneSelectedPatternCard()) {
                // We don't allow anyone to the game view until everyone has a patterncard
                Alert alert = new Alert("Nog even wachten",
                        "Nog niet alle spelers hebben een patroonkaart gekozen!", AlertType.INFO);
                myScene.addAlertPane(alert);
            } else {
                viewGame();
            }

        }
    }

    public void viewEmptyPatterncard() {
        PatternCard patternCard = new PatternCard(1);
        GameDie gameDie = new GameDie(1, "rood", 5);
        patternCard.placeDie(1, 1, gameDie);
        PatternCardView patternCardView = new PatternCardView(this);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();
        myScene.setContentPane(patternCardView);
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
            Alert alert = new Alert("Nog even wachten",
                    "Nog niet alle spelers hebben een patroonkaart gekozen!", AlertType.INFO);
            myScene.addAlertPane(alert);
            myScene.getAccountController().viewLobby();
        } else {
            viewGame();
        }
    }

    /**
     * Player is passing for a round
     */
    public void actionPass() {
        if (player.isCurrentPlayer()) {
            player.getGame().setNextPlayer();
        } else {
            Alert alert = new Alert("Nog even wachten",
                    "Je bent nog niet aan de beurt.", AlertType.INFO);
            myScene.addAlertPane(alert);
        }
    }

    public void viewClickPlacement() {
        HBox mainPane = new HBox();
        VBox secondPane = new VBox();

        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();

        PatternCard patternCard = new PatternCard(1, 0, false);
        PatternCardView patternCardView = new PatternCardView(this);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();

        GameDie gameDie1 = new GameDie(1, "geel", 1);
        DieView dieView1 = new DieView();
        dieView1.setGameDie(gameDie1);
        dieView1.render();

        GameDie gameDie2 = new GameDie(2, "paars", 3);
        DieView dieView2 = new DieView();
        dieView2.setGameDie(gameDie2);
        dieView2.render();

        GameDie gameDie3 = new GameDie(3, "rood", 5);
        DieView dieView3 = new DieView();
        dieView3.setGameDie(gameDie3);
        dieView3.render();

        pane1.setPadding(new Insets(5));
        pane2.setPadding(new Insets(5));
        pane3.setPadding(new Insets(5));

        pane1.getChildren().add(dieView1);
        pane2.getChildren().add(dieView2);
        pane3.getChildren().add(dieView3);

        secondPane.setPadding(new Insets(5));
        secondPane.getChildren().addAll(pane1, pane2, pane3);
        mainPane.getChildren().addAll(patternCardView, secondPane);
        myScene.setContentPane(mainPane);
    }

    /**
     * Players wants to go back to the lobby
     */
    public void makeDie(int number, int eyes, String color) {
        Pane pane = new Pane();
        GameDie gameDie = new GameDie(number, color, eyes);
        DieView dieView = new DieView();
        dieView.setGameDie(gameDie);
        dieView.render();
        pane.getChildren().add(dieView);
        myScene.setContentPane(pane);
    }


    public void emptyPatternCard() {
        Pane pane = new Pane();
        PatternCard patternCard = new PatternCard(1);
        GameDie gameDie = new GameDie(1, "rood", 5);
        patternCard.placeDie(1, 1, gameDie);
        PatternCardView patternCardView = new PatternCardView(this);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();

        pane.getChildren().add(patternCardView);
        myScene.setContentPane(pane);
    }

    public void placeDie(GameDie die, PatternCardField patterncardField) {
        patterncardField.placeDie(die);
    }

    public void actionExit() {
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

    /**
     * Handles the logic behind a tool card payment. The method first checks if a player has already
     * paid for a tool card before or not, and if the player has sufficient funds.
     * <p>
     * If the tool card has not received payment before, the player will hand over one favor token
     * as payment for the toolcard. This tool cards status will then be set to "has already been
     * paid for before". </br> If the tool card has received payment before, then the player will
     * hand over two favor tokens as payment for the tool card.
     * <p>
     * If the player has insufficient funds, a message will appear on screen informing the player
     * about their lack of funds, and the player will not be able to use this tool card.
     *
     * @param toolCard The tool card.
     */
    public void actionPayForToolCard(ToolCard toolCard) {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        ToolCardDao toolCardDao = new ToolCardDao();
        toolCardDao.toolCardHasPayment(toolCard, player.getGame());

        ArrayList<FavorToken> newFavorTokens = player.getFavorTokens();
        if (newFavorTokens.size() > 0) {
            if (!toolCard.hasBeenPaidForBefore()) {
                favorTokenDao.setFavortokensForToolCard(newFavorTokens.get(0), toolCard,
                        player.getGame());
                newFavorTokens.remove(0);
                player.setFavorTokens(newFavorTokens);
                toolCard.setHasBeenPaidForBefore(true);
            } else {
                if (newFavorTokens.size() > 1) {
                    for (int i = 1; i <= 2; i++) {
                        favorTokenDao.setFavortokensForToolCard(newFavorTokens.get(0), toolCard,
                                player.getGame());
                        newFavorTokens.remove(0);
                    }
                    player.setFavorTokens(newFavorTokens);
                } else {
                    Alert alert = new Alert("Te weinig betaalstenen",
                            "Je hebt niet genoeg betaalstenen om deze kaart te kopen!",
                            AlertType.ERROR);
                    myScene.addAlertPane(alert);
                }
            }
        } else {
            Alert alert = new Alert("Te weinig betaalstenen",
                    "Je hebt niet genoeg betaalstenen om deze kaart te kopen!", AlertType.ERROR);
            myScene.addAlertPane(alert);
        }
    }
}
