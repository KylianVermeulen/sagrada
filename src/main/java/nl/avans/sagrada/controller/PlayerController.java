package nl.avans.sagrada.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.dao.ToolCardDao;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.enumerations.AccountStatus;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.model.toolcard.ToolCardDriePuntStang;
import nl.avans.sagrada.model.toolcard.ToolCardFluxBorstel;
import nl.avans.sagrada.model.toolcard.ToolCardFluxVerwijderaar;
import nl.avans.sagrada.task.CheatmodeTask;
import nl.avans.sagrada.task.GetPatternCardOfPlayerTask;
import nl.avans.sagrada.task.SetSelectedPatternCardOfPlayerTask;
import nl.avans.sagrada.task.UpdateDieTask;
import nl.avans.sagrada.task.UpdatePlayerFrameFieldTask;
import nl.avans.sagrada.view.ChatLineView;
import nl.avans.sagrada.view.EndgameView;
import nl.avans.sagrada.view.GameView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardFieldView;
import nl.avans.sagrada.view.PatternCardSelectionView;
import nl.avans.sagrada.view.ToolCardView;
import nl.avans.sagrada.view.popups.Alert;
import nl.avans.sagrada.view.popups.AlertType;
import nl.avans.sagrada.view.popups.DriePuntStang;
import nl.avans.sagrada.view.popups.Fluxborstel;
import nl.avans.sagrada.view.popups.Fluxverwijderaar;

public class PlayerController {
    private MyScene myScene;
    private Player player;
    private ToolCard activeToolCard;
    private GameView gameView;
    private HashMap<HashMap<Integer, String>, TreeMap<Integer, PatternCardField>> treeMapHashMap;
    private CheatmodeTask cheatmodeTask;

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
     * Sets the active toolcard to null
     */
    public void setActiveToolCardNull() {
        activeToolCard = null;
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
                            viewGame(false);
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

                                UpdatePlayerFrameFieldTask upfft = new UpdatePlayerFrameFieldTask(gameDie, patternCardField, playerEvent);
                                upfft.setOnSucceeded(e -> {
                                    player.calculateScore(false, false);
                                    UpdateDieTask udt = new UpdateDieTask(player.getGame(), gameDie);
                                    Thread updateGameTread = new Thread(udt);
                                    updateGameTread.setDaemon(true);
                                    updateGameTread.setName("Update gamedie thread");
                                    updateGameTread.start(); 
                                });
                                Thread thread = new Thread(upfft);
                                thread.setDaemon(true);
                                thread.start();
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

    /**
     * Gets the active toolcard
     *
     * @return ToolCard
     */
    public ToolCard getActiveToolCard() {
        return this.activeToolCard;
    }

    /**
     * Sets the active toolcard if there can be paid for
     *
     * @param toolCard ToolCard
     */
    public void setActiveToolCard(ToolCard toolCard) {
        activeToolCard = toolCard;
        Alert alert = new Alert("Active toolcard",
                "De toolcard, " + activeToolCard.getName() + " is nu actief", AlertType.INFO);
        myScene.addAlertPane(alert);

        if (activeToolCard instanceof ToolCardDriePuntStang) {
            DriePuntStang driePuntStang = new DriePuntStang(myScene, this, player.getGame(),
                    activeToolCard);
            myScene.addPopupPane(driePuntStang);
        }
        if (toolCard instanceof ToolCardFluxVerwijderaar) {
            Fluxverwijderaar fluxverwijderaar = new Fluxverwijderaar(myScene,
                    getPlayer().getGame(), this, activeToolCard);
            myScene.addPopupPane(fluxverwijderaar);
        }
        if (toolCard instanceof ToolCardFluxBorstel) {
            Fluxborstel fluxborstelPopup = new Fluxborstel(myScene,
                    getPlayer().getGame(),
                    this, activeToolCard);
            myScene.addPopupPane(fluxborstelPopup);
        }
    }

    /**
     * Views the game
     * If this method is called by the checksum class we need to add true
     * To the db
     * @param cameHereByRefresh
     */
    public void viewGame(boolean cameHereByRefresh) {
        // Refresh game & player object
        int gameId = player.getGame().getId();
        if (cameHereByRefresh) {
            player = new PlayerDao().getPlayerById(player.getId());
            Game game = new GameDao().getGameById(gameId);
            player.setGame(game);
        }
        Game game = player.getGame();
        player.getAccount().setAccountStatus(AccountStatus.GAME);


        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).setPlayerColor(i);
        }

        if (player.isCurrentPlayer() && cameHereByRefresh) {
            game.setTurnPlayer(player);
            Alert alert = new Alert("Speel je beurt", "Je bent nu aan de beurt!", AlertType.SUCCES);
            myScene.addAlertPane(alert);
        }
        if (game.getRound() == 11) {
            game.finishGame();
            viewEndgame();
        } else {
            if (cheatmodeTask == null) {
                cheatmodeTask = new CheatmodeTask(this);
                Thread cheatmodeTaskThread = new Thread(cheatmodeTask);
                cheatmodeTaskThread.setName("Cheatmode placement options thread");
                cheatmodeTaskThread.setDaemon(true);
                cheatmodeTaskThread.start();
            }
            
            Pane pane = new Pane();
            gameView = new GameView(this, game, player);
            gameView.render();
            pane.getChildren().add(gameView);
            myScene.setContentPane(pane);
        }
    }

    public void actionJoinGame(Account account, Game game) {
        player = new PlayerDao().getPlayerByAccountAndGame(account, game);
        player.setGame(game);
        
        GetPatternCardOfPlayerTask gpcopt = new GetPatternCardOfPlayerTask(player);
        gpcopt.setOnSucceeded(e -> {
            PatternCard patternCard = gpcopt.getValue();
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
                    viewGame(false);
                }
            }
        });
        Thread thread = new Thread(gpcopt);
        thread.setDaemon(true);
        thread.start();
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
        player.setPatternCard(patternCard);
        SetSelectedPatternCardOfPlayerTask sspcopt = new SetSelectedPatternCardOfPlayerTask(player, patternCard);
        sspcopt.exceptionProperty().addListener((observable, oldValue, newValue) ->  {
            if(newValue != null) {
              Exception ex = (Exception) newValue;
              ex.printStackTrace();
            }
          });
        sspcopt.setOnSucceeded(e -> {
            Game game = player.getGame();
            if (!game.everyoneSelectedPatternCard()) {
                // We don't allow anyone to the game view until everyone has a patterncard
                Alert alert = new Alert("Nog even wachten",
                        "Nog niet alle spelers hebben een patroonkaart gekozen!", AlertType.INFO);
                myScene.addAlertPane(alert);
                myScene.getAccountController().viewLobby();
            } else {
                viewGame(false);
            } 
        });
        Thread thread = new Thread(sspcopt);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Player is passing for a turn
     */
    public void actionPass() {
        if (player.isCurrentPlayer()) {
            player.calculateScore(false, true);
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

    public void actionToggleCheatmode() {
        player.setCheatmode(!player.isCheatmode());
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
     * Controlls the amount of favor tokens that needs to be paid
     *
     * @param toolCard The tool card.
     */
    public void actionPayForToolCard(ToolCard toolCard, ToolCardView toolCardView) {
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
        if (player.hasUsedToolcardInCurrentTurn()) {
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
                            player.getGame(), player);
                    newFavorTokens.remove(0);
                    player.setFavorTokens(newFavorTokens);
                    toolCard.setHasBeenPaidForBefore(true);
                    toolCardView.addFavorToken(player.getPlayerColor());
                    setActiveToolCard(toolCard);
                } else {
                    if (newFavorTokens.size() > 1) {
                        for (int i = 1; i <= 2; i++) {
                            favorTokenDao.setFavortokensForToolCard(newFavorTokens.get(0), toolCard,
                                    player.getGame(), player);
                            newFavorTokens.remove(0);
                            toolCardView.addFavorToken(player.getPlayerColor());
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
     * Hightlight the best placement for a gamedie. Only highlights when the treeMap has been set by
     * the cheatmode task started in viewGame.
     *
     * @param gameDie The GameDie.
     */
    public void actionHighlightBestPlacementForGameDie(GameDie gameDie) {
        PatternCardFieldView[][] patternCardFieldViews = gameView.getPlayerPatternCardView()
                .getPatternCardFieldViews();
        if (treeMapHashMap != null) {
            HashMap<Integer, String> hashMap = new HashMap<>();
            hashMap.put(gameDie.getNumber(), gameDie.getColor());
            TreeMap<Integer, PatternCardField> treeMap = treeMapHashMap.get(hashMap);

            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) {
                for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
                    patternCardFieldViews[x][y].removeHighlight();

                    if (treeMap.isEmpty()) {
                        Alert alert = new Alert("Helaas", "Deze die kan je niet plaatsen!",
                                AlertType.INFO);
                        myScene.addAlertPane(alert);
                        return;
                    }
                    if (treeMap.lastEntry().getValue().getxPos() == x
                            && treeMap.lastEntry().getValue().getyPos() == y) {
                        patternCardFieldViews[x][y].addBestHighlight();
                    }
                }
            }
        } else {
            Alert alert = new Alert("Nog even wachten", "Cheatmode is nog aan het berekenen!",
                    AlertType.INFO);
            myScene.addAlertPane(alert);
        }
    }

    /**
     * Displays the view after a game is finished. The user can see their scores and then go or back
     * to the lobbyscreen or view the statistics.
     */
    public void viewEndgame() {
        Game game = player.getGame();
        Player winPlayer = game.getPlayerWithBestScore();
        Pane pane = new Pane();
        EndgameView endgameView = new EndgameView(game, this, winPlayer);
        endgameView.render();
        pane.getChildren().add(endgameView);
        myScene.setContentPane(pane);
    }

    public void actionBackToLobby() {
        myScene.getAccountController().viewLobby();
    }

    public HashMap<HashMap<Integer, String>, TreeMap<Integer, PatternCardField>> getTreeMapHashMap() {
        return treeMapHashMap;
    }

    public void setTreeMapHashMap(
            HashMap<HashMap<Integer, String>, TreeMap<Integer, PatternCardField>> treeMapHashMap) {
        this.treeMapHashMap = treeMapHashMap;
    }

    public void removePopupPane() {
        myScene.removePopupPane();
    }
}
