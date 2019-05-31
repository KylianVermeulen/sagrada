package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.database.ChecksumDatabase;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.PublicObjectiveCard;
import nl.avans.sagrada.model.RoundTrack;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.task.GetFavorTokensOfToolCardTask;
import nl.avans.sagrada.task.GetFavorTokensTask;
import nl.avans.sagrada.task.GetPatternCardOfPlayerTask;
import nl.avans.sagrada.task.GetPublicObjectiveCardTask;
import nl.avans.sagrada.task.GetRoundTrackDiceTask;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameView extends VBox implements ViewInterface {
    private final int SPACING_BETWEEN_CHILDS = 5;
    private Game game;
    private Player player;
    private PlayerController playerController;
    private HBox otherPlayerPatternCardViews;
    private HBox actionButtons;
    private Label balance;
    private PatternCardView playerPatternCardView;
    private ScoreBoardView scoreBoard;
    private RoundTrackView roundTrackView;
    private ChatLineView chatLineView;
    private PrivateObjectiveCardView privateObjectiveCardView;
    private DieOfferView dieOfferView;
    private HBox toolCardsView;
    private HBox publicObjectiveCardView;

    public GameView(PlayerController playerController, Game game, Player player) {
        this.game = game;
        this.playerController = playerController;
        this.player = player;

        setSpacing(SPACING_BETWEEN_CHILDS);
    }

    public PatternCardView getPlayerPatternCardView() {
        return playerPatternCardView;
    }

    /**
     * Gives all players a color
     */
    private void assignPlayerColors() {
        for (int i = 0; i < player.getGame().getPlayers().size(); i++) {
            if (player.getId() == player.getGame().getPlayers().get(i).getId()) {
                player.setPlayerColor(i);
            }
        }
    }

    private void buildOtherPlayerPatternCards() {
        otherPlayerPatternCardViews = new HBox();
        otherPlayerPatternCardViews.setSpacing(SPACING_BETWEEN_CHILDS);
        ArrayList<Player> players = game.getPlayers();

        for (Player player : players) {
            String currentPlayerUsername = this.player.getAccount().getUsername();
            String otherPlayerUsername = player.getAccount().getUsername();
            
            GetPatternCardOfPlayerTask gpcopt = new GetPatternCardOfPlayerTask(player);
            gpcopt.setOnSucceeded(e -> {
                PatternCard patternCard = gpcopt.getValue();
                player.setPatternCard(patternCard);
                if (!currentPlayerUsername.equals(otherPlayerUsername)) {
                    PatternCard playerPatternCard = player.getPatternCard();

                    PatternCardView patternCardView = new PatternCardView(playerController);
                    patternCardView.setCenterShape(true);
                    patternCardView.setPlayerName(otherPlayerUsername);
                    patternCardView.setPlayerColor(player.getPlayerColor());
                    if (player.isCurrentPlayer()) {
                        patternCardView.setCurrentPlayer(true);
                    } else {
                        patternCardView.setCurrentPlayer(false);
                    }
                    patternCardView.setPatternCard(playerPatternCard);
                    patternCardView.render();
                    otherPlayerPatternCardViews.getChildren().add(patternCardView);
                } 
            });
            Thread thread = new Thread(gpcopt);
            thread.setName("Get patternCard of Player");
            thread.setDaemon(true);
            thread.start();
        }
    }

    private void buildToolCards() {
        toolCardsView = new HBox();

        ArrayList<ToolCard> gameToolCards = new ArrayList<>();
        gameToolCards = game.getToolCards();

        for (ToolCard toolCard : gameToolCards) {
            GetFavorTokensOfToolCardTask gftotct = new GetFavorTokensOfToolCardTask(toolCard, game);
            gftotct.setOnSucceeded(e -> {
                ArrayList<FavorToken> favorTokens = gftotct.getValue();
                ToolCardView toolCardView = new ToolCardView(playerController);
                toolCardView.setToolCard(toolCard);
                toolCardView.setFavorTokens(favorTokens, game);
                toolCardView.setMaxSize(CardView.CARD_WIDTH, CardView.CARD_HEIGHT);
                toolCardView.render();
                toolCardsView.getChildren().add(toolCardView);
            });
            Thread thread = new Thread(gftotct);
            thread.setName("Get favortokens of ToolCard");
            thread.setDaemon(true);
            thread.start();
        }
    }

    private void buildRoundTrack() {
        roundTrackView = new RoundTrackView(playerController);
        RoundTrack roundTrack = new RoundTrack();
        GetRoundTrackDiceTask grtdt = game.getTrackDiceTask();
        grtdt.setOnSucceeded(e -> {
            
            ArrayList<GameDie> trackDie = grtdt.getValue();
            for (GameDie gameDie : trackDie) {
                roundTrack.addGameDie(gameDie);
            }
    
            roundTrackView.setRoundTrack(roundTrack);
            roundTrackView.render();
        });
        Thread thread = new Thread(grtdt);
        thread.setName("Get roundtrack dice");
        thread.setDaemon(true);
        thread.start();
    }

    private void buildPublicObjectiveCards() {
        publicObjectiveCardView = new HBox();
        
        GetPublicObjectiveCardTask gpoct = new GetPublicObjectiveCardTask(game);
        gpoct.setOnSucceeded(e -> {
            PublicObjectiveCard[] gamePublicObjectiveCards = gpoct.getValue();
            game.setPublicObjectiveCards(gamePublicObjectiveCards);
            for (PublicObjectiveCard publicObjectiveCard : gamePublicObjectiveCards) {
                PublicObjectiveCardView publicObjectiveCardView =
                        new PublicObjectiveCardView();
                publicObjectiveCardView.setPublicObjectiveCard(publicObjectiveCard);
                publicObjectiveCardView.setMaxSize(CardView.CARD_WIDTH, CardView.CARD_HEIGHT);
                publicObjectiveCardView.render();
                this.publicObjectiveCardView.getChildren().add(publicObjectiveCardView);
            } 
        }); 
        Thread thread = new Thread(gpoct);
        thread.setName("Get public objective card");
        thread.setDaemon(true);
        thread.start();
    }

    private void buildChatLine() {
        chatLineView = new ChatLineView(playerController);
        ChecksumDatabase.setChatLineView(chatLineView);
        chatLineView.render();
    }

    /**
     * Builds the scoreboard inside of the game view.
     */
    private void buildScoreBoard() {
        scoreBoard = new ScoreBoardView(game);
        scoreBoard.render();
    }

    private void buildBalance() {
        balance = new Label();
        GetFavorTokensTask gftt = new GetFavorTokensTask(player);
        gftt.setOnSucceeded(e -> {
            ArrayList<FavorToken> favorTokens = gftt.getValue();
            balance.setText("Betaalstenen: " + favorTokens.size());
        });
        Thread thread = new Thread(gftt);
        thread.setName("Get favortokens");
        thread.setDaemon(true);
        thread.start();
        balance.setPadding(new Insets(0, 0, 0, 5));
    }

    private void buildPlayerPatternCard() {
        playerPatternCardView = new PatternCardView(playerController);
        GetPatternCardOfPlayerTask gpcopt = new GetPatternCardOfPlayerTask(player);
        gpcopt.setOnSucceeded(e -> {
            PatternCard patternCard = gpcopt.getValue();
            playerPatternCardView.setPatternCard(patternCard);
            playerPatternCardView.setPlayerName(player.getAccount().getUsername());
            playerPatternCardView.setPlayerColor(player.getPlayerColor());
            if (player.isCurrentPlayer()) {
                playerPatternCardView.setCurrentPlayer(true);
            } else {
                playerPatternCardView.setCurrentPlayer(false);
            }
            playerPatternCardView.render();
        });
        Thread thread = new Thread(gpcopt);
        thread.setName("Get patternCard of player");
        thread.setDaemon(true);
        thread.start();
    }

    private void buildPlayerPrivateObjectiveCard() {
        privateObjectiveCardView = new PrivateObjectiveCardView(player);
        privateObjectiveCardView.render();
        privateObjectiveCardView.setMaxSize(CardView.CARD_WIDTH, CardView.CARD_HEIGHT);
    }

    private void buildActionButtons() {
        actionButtons = new HBox();

        Button cheatmodeButton = new Button("Cheatmode");
        if (player.isCheatmode()) {
            cheatmodeButton.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        } else {
            cheatmodeButton.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        }
        cheatmodeButton.setOnAction(e -> {
            playerController.actionToggleCheatmode();
            if (player.isCheatmode()) {
                cheatmodeButton.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
            } else {
                cheatmodeButton.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
            }
        });

        Button passButton = new Button("Beurt beeindigen");
        passButton.setOnAction(e -> playerController.actionPass());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> playerController.actionExit());

        actionButtons.getChildren().addAll(cheatmodeButton, passButton, exitButton);
        actionButtons.setAlignment(Pos.BOTTOM_CENTER);
        actionButtons.setSpacing(SPACING_BETWEEN_CHILDS);
        actionButtons.setPadding(new Insets(40, 0, 0, 0));
    }

    private void buildDieOffer() {
        dieOfferView = new DieOfferView(this.game, playerPatternCardView, playerController);
        dieOfferView.render();
    }
    
    /**
     * Method the render the die view
     * For when a die is placed on the patterncard from the
     * Offer
     */
    public void renderDieOfferView() {
        if (dieOfferView != null) {
            dieOfferView.render();
        }
    }

    @Override
    public void render() {
        getChildren().clear();

        assignPlayerColors();
        buildOtherPlayerPatternCards();
        buildScoreBoard();
        buildToolCards();
        buildRoundTrack();
        buildPublicObjectiveCards();
        buildChatLine();
        buildBalance();
        buildPlayerPatternCard();
        buildPlayerPrivateObjectiveCard();
        buildActionButtons();
        buildDieOffer();

        BorderPane firstView = new BorderPane();
        firstView.setPrefHeight(PatternCardView.PATTERNCARD_HEIGHT - 15);
        firstView.setPrefWidth(Main.SCREEN_WIDTH);

        HBox secondView = new HBox();
        secondView.setAlignment(Pos.CENTER);
        secondView.setSpacing(SPACING_BETWEEN_CHILDS);
        secondView.setPrefWidth(Main.SCREEN_WIDTH);

        BorderPane thirdView = new BorderPane();
        thirdView.setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT / 3 - 15);

        firstView.setCenter(otherPlayerPatternCardViews);
        firstView.setRight(scoreBoard);

        secondView.getChildren().add(toolCardsView);
        secondView.getChildren().add(publicObjectiveCardView);
        secondView.getChildren().add(roundTrackView);

        HBox rightThirdView = new HBox();
        rightThirdView.getChildren().addAll(dieOfferView, privateObjectiveCardView);
        thirdView.setLeft(chatLineView);
        thirdView.setRight(rightThirdView);
        thirdView.setBottom(actionButtons);

        HBox thirdViewCenterBox = new HBox();
        thirdViewCenterBox.getChildren().addAll(balance, playerPatternCardView);
        thirdViewCenterBox.setPrefWidth(Main.SCREEN_WIDTH / 3);
        thirdViewCenterBox.setSpacing(SPACING_BETWEEN_CHILDS);
        thirdView.setCenter(thirdViewCenterBox);

        getChildren().add(firstView);
        getChildren().add(secondView);
        getChildren().add(thirdView);
    }
}
