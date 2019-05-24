package nl.avans.sagrada.controller;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.PlayerFrameFieldDao;
import nl.avans.sagrada.dao.ToolCardDao;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.view.ChatLineView;
import nl.avans.sagrada.view.EndgameView;
import nl.avans.sagrada.view.GameView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardSelectionView;
import nl.avans.sagrada.view.ToolCardView;
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
     * Sets the player for the controller
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Handels the placement of a die on the patterncard Also handels the toolcard drag handle
     */
    public void actionPlaceDie(PatternCard patternCard, PatternCardField patternCardField,
            GameDie gameDie, MouseEvent event) {
        PlayerDao playerDao = new PlayerDao();
        Player playerEvent = patternCard.getPlayer();

        if (playerEvent.getId() == player.getId()) {
            if (player.isCurrentPlayer()) {
                if (activeToolCard != null || playerDao.getCountPlacedDieInTurnRound(player) < 1) {
                    if (activeToolCard != null) {
                        PatternCard toolCardUseResult = activeToolCard
                                .handleDrag(event, gameDie);
                        if (toolCardUseResult != null) {
                            new GameDieDao().updateDie(player.getGame(), gameDie);
                            if (activeToolCard.getIsDone()) {
                                gameDie.setInFirstTurn(player.isFirstTurn());
                                activeToolCard = null;
                                Alert alert = new Alert("ToolCard",
                                        "Je hebt je toolcard gebruikt!",
                                        AlertType.INFO
                                );
                                myScene.addAlertPane(alert);
                            }
                            player.setPatternCard(toolCardUseResult);
                            viewGame();
                        } else {
                            Alert alert = new Alert("Helaas",
                                    "Dit kan niet wat je probeert met de toolcard",
                                    AlertType.ERROR);
                            myScene.addAlertPane(alert);
                        }
                    } else {
                        if (gameDie.getPatternCardField() == null) {
                            if (patternCardField.canPlaceDie(gameDie)) {
                                gameDie.setInFirstTurn(player.isFirstTurn());
                                gameDie.setPatternCardField(patternCardField);
                                patternCardField.setDie(gameDie);

                                PlayerFrameFieldDao playerFrameFieldDao = new PlayerFrameFieldDao();
                                playerFrameFieldDao
                                        .addDieToField(gameDie, patternCardField, player);

                                new GameDieDao().updateDie(player.getGame(), gameDie);
                            }
                        }
                    }
                } else {
                    Alert alert = new Alert("Helaas", "Je hebt al een dobbelsteen geplaatst.",
                            AlertType.INFO);
                    myScene.addAlertPane(alert);
                }
            } else {
                Alert alert = new Alert("Nog even wachten",
                        "Je bent nog niet aan de beurt.", AlertType.INFO);
                myScene.addAlertPane(alert);
            }
        } else {
            Alert alert = new Alert("Helaas", "Dit is niet jouw patroonkaart.", AlertType.ERROR);
            myScene.addAlertPane(alert);
        }
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
        if (game.getRound() == 11) {
            game.finishGame();
            viewEndgame();
        } else {
            Pane pane = new Pane();
            GameView gameView = new GameView(this, game, player);
            gameView.render();
            pane.getChildren().add(gameView);
            myScene.setContentPane(pane);
        }
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
     * Player is passing for a turn
     */
    public void actionPass() {
        if (player.isCurrentPlayer()) {
            player.getGame().setNextPlayer();
            activeToolCard = null;
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
     */
    public void setActiveToolCard(ToolCard toolCard) {
        activeToolCard = toolCard;
        Alert alert = new Alert("Active toolcard",
                "De toolcard, " + activeToolCard.getName() + " is nu actief", AlertType.INFO);
        myScene.addAlertPane(alert);
    }

    /**
     * Controlls the amount of favor tokens that needs to be paid
     *
     * @param toolCard The tool card.
     */
    public void actionPayForToolCard(ToolCard toolCard, ToolCardView toolcardview) {
        if (!player.isCurrentPlayer()) {
            Alert alert = new Alert("Active speler",
                    "Je bent nu niet de active speler, even geduld!",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        if (!toolCard.hasRequirementsToRun(this)) {
            Alert alert = new Alert("ToolCard",
                    "Je hebt niet de minimale benodigdheden voor deze toolcard",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        if (player.hasUsedToolcardInCurrentRound()) {
            Alert alert = new Alert("ToolCard",
                    "Je hebt al een toolcard gebruikt!",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        if (activeToolCard == null) {
            FavorTokenDao favorTokenDao = new FavorTokenDao();
            ToolCardDao toolCardDao = new ToolCardDao();
            toolCardDao.toolCardHasPayment(toolCard, player.getGame());

            ArrayList<FavorToken> newFavorTokens = player.getFavorTokens();
            for (int i = 0; i < player.getGame().getPlayers().size(); i++) {
                if (player.getId() == player.getGame().getPlayers().get(i).getId()) {
                    player.setPlayerColor(i);
                }
            }
            if (newFavorTokens.size() > 0) {
                if (!toolCard.hasBeenPaidForBefore()) {
                    favorTokenDao.setFavortokensForToolCard(newFavorTokens.get(0), toolCard,
                            player.getGame());
                    newFavorTokens.remove(0);
                    player.setFavorTokens(newFavorTokens);
                    toolCard.setHasBeenPaidForBefore(true);
                    toolcardview.addFavorToken(player.getPlayerColor());
                    setActiveToolCard(toolCard);
                } else {
                    if (newFavorTokens.size() > 1) {
                        for (int i = 1; i <= 2; i++) {
                            favorTokenDao.setFavortokensForToolCard(newFavorTokens.get(0), toolCard,
                                    player.getGame());
                            newFavorTokens.remove(0);
                            toolcardview.addFavorToken(player.getPlayerColor());
                            // Here is the favor token added
                            setActiveToolCard(toolCard);
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
                        "Je hebt niet genoeg betaalstenen om deze kaart te kopen!",
                        AlertType.ERROR);
                myScene.addAlertPane(alert);
            }
        } else {
            Alert alert = new Alert("Active toolcard",
                    "Je hebt al een actieve toolcard: " + activeToolCard.getName(),
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
        }

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
