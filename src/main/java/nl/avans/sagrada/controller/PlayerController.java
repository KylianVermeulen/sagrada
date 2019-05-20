package nl.avans.sagrada.controller;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.model.toolcard.ToolCardGlasBreekTang;
import nl.avans.sagrada.view.ChatLineView;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.EndgameView;
import nl.avans.sagrada.view.GameView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardSelectionView;
import nl.avans.sagrada.view.PatternCardView;
import nl.avans.sagrada.view.popups.Alert;
import nl.avans.sagrada.view.popups.AlertType;

public class PlayerController {
    private MyScene myScene;
    private Player player;
    private ToolCard activeToolCard;

    public PlayerController(MyScene myScene) {
        this.myScene = myScene;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Handels the placement of a die on the patterncard Also handels the toolcard drag handle
     */
    public void actionPlaceDie(PatternCard patternCard, PatternCardField patternCardField,
            GameDie gameDie, MouseEvent event) {
        Player playerEvent = patternCard.getPlayer();
        
        if (playerEvent.getId() == player.getId()) {
            // Check if the player from the               
            if (activeToolCard != null) {
                PatternCard toolcardUseResult = activeToolCard.handleDrag(event, gameDie);
                if (toolcardUseResult != null) {
                    if (activeToolCard.getIsDone()) {
                        activeToolCard = null;
                        actionPayForToolCard(activeToolCard);
                        activeToolCard = null;
                    }
                    player.setPatternCard(toolcardUseResult);
                    viewGame();
                } else {
                    Alert alert = new Alert("Helaas", "Dit kan niet wat je probeert met de toolcard",
                            AlertType.ERROR);
                    myScene.addAlertPane(alert);
                }
            } else {
                if (gameDie.getPatternCardField() == null) {
                    if (patternCardField.canPlaceDie(gameDie)) {
                        gameDie.setPatternCardField(patternCardField);
                        patternCardField.setDie(gameDie);
                        
                        PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
                        playerFrameFieldDao.addDieToField(gameDie, patternCardField, player);
                    }
                }
            }
        }
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

        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).setPlayerColor(i);
        }

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
        PatternCardDao PatternCardDao = new PatternCardDao();
        PatternCard patternCard = PatternCardDao.getSelectedPatterncardOfPlayer(player);
        player.setPatternCard(patternCard);

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
            Alert alert = new Alert("Nog even wachten", "Je bent nog niet aan de beurt.",
                    AlertType.INFO);
            myScene.addAlertPane(alert);
        }
    }

    /**
     * Players wants to go back to the lobby
     */
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
        chatlineDao.getTime(chatline);

        if (!text.matches("")) {
            if (chatlineDao.timeExistsOfPlayer(chatline) == false) {
                chatlineDao.addChatline(chatline);
                chatlineView.addChatline(chatline);
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
     * Sets the active toolcard if there can be paid for
     * @param toolCard
     */
    public void setActiveToolCard(ToolCard toolCard) {
        if (activeToolCard != null) {
            if (toolCard.getId() == activeToolCard.getId()) {
                activeToolCard = null;
                Alert alert = new Alert("Active toolcard",
                        "Je hebt nu geen active toolcard meer",
                        AlertType.INFO);
                myScene.addAlertPane(alert);
            }
        }
        else if ((toolCard.hasBeenPaidForBefore() && player.getFavorTokens().size() >= 2) || 
                !toolCard.hasBeenPaidForBefore() && player.getFavorTokens().size() >= 1) {
            if (toolCard.hasRequirementsToRun(this)) {
                activeToolCard = toolCard;
                Alert alert = new Alert("Active toolcard",
                        "Je hebt een actieve toolcard: " + activeToolCard.getName(),
                        AlertType.INFO);
                Alert alertInfo = new Alert("ToolCard info", 
                        "wanneer je de toolcard succesvol hebt gebruikt, zal er pas betaald worden",
                        AlertType.INFO
                    );
                myScene.addAlertPane(alert);
                myScene.addAlertPane(alertInfo);
            }
            else {
                Alert alert = new Alert("ToolCard",
                        "Je voldoet niet aan de eisen!",
                        AlertType.ERROR);
                myScene.addAlertPane(alert);
            }
        }
        else {
            Alert alert = new Alert("Te weinig betaalstenen",
                    "Je hebt niet genoeg betaalstenen om deze kaart te kopen!",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
        }
    }
    
    /**
     * Controlls the amount of favor tokens that needs to be paid
     * @param toolCard The tool card.
     */
    public void actionPayForToolCard(ToolCard toolCard) {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        ArrayList<FavorToken> newFavorTokens = player.getFavorTokens();
        if ((toolCard.hasBeenPaidForBefore() && player.getFavorTokens().size() >= 2)) {
            favorTokenDao.setFavortokensForToolCard(newFavorTokens.get(0), toolCard,
                    player.getGame());
            favorTokenDao.setFavortokensForToolCard(newFavorTokens.get(1), toolCard,
                    player.getGame());
            newFavorTokens.remove(0);
            newFavorTokens.remove(1);
        }
        else if (!toolCard.hasBeenPaidForBefore() && player.getFavorTokens().size() >= 1) {
            favorTokenDao.setFavortokensForToolCard(newFavorTokens.get(0), toolCard,
                    player.getGame());
            newFavorTokens.remove(0);
        }
    }

    /**
     * Example code
     */
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
     * Displays the view after a game is finished. The user can see their scores and then go or back
     * to the lobbyscreen or view the statistics.
     */    
    public void viewEndgame() {
        GameDao gameDao = new GameDao();
        Game game = player.getGame();
        Player winPlayer = gameDao.bestFinalScore(game);
        Pane pane = new Pane();
        EndgameView endgameView = new EndgameView(game, this, winPlayer);
        endgameView.render();
        pane.getChildren().add(endgameView);
        myScene.setContentPane(pane);
    }
    
    public void actionBackToLobby() {
        myScene.getAccountController().viewLobby();
    }
}
