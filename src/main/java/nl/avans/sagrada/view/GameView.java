package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PatternCardDao;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.PublicObjectiveCard;
import nl.avans.sagrada.model.RoundTrack;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameView extends VBox implements ViewInterface {
    private final int SPACING_BETWEEN_CHILDS = 5;
    private Game game;
    private Player player;
    private PlayerController playerController;
    private AccountController accountController;
    private HBox otherPlayerPatternCardViews;
    private HBox actionButtons;
    private ArrayList<ToolCardView> toolCardViews;
    private ArrayList<PublicObjectiveCardView> publicObjectiveCardViews;
    private Label balance;
    private PatternCardView playerPatternCardView;
    private ScoreBoardView scoreBoard;
    private RoundTrackView roundTrackView;
    private ChatLineView chatLineView;
    private PrivateObjectiveCardView privateObjectiveCardView;
    private DieOfferView dieOfferView;

    public GameView(PlayerController playerController, Game game, Player player) {
        this.game = game;
        this.playerController = playerController;
        this.player = player;

        setSpacing(SPACING_BETWEEN_CHILDS);
    }

    private void buildOtherPlayerPatternCards() {
        otherPlayerPatternCardViews = new HBox();
        otherPlayerPatternCardViews.setSpacing(SPACING_BETWEEN_CHILDS);
        ArrayList<Player> players = game.getPlayers();

        for (Player player : players) {
            String currentPlayerUsername = this.player.getAccount().getUsername();
            String otherPlayerUsername = player.getAccount().getUsername();
            PatternCardDao PatternCardDao = new PatternCardDao();
            PatternCard patternCard = PatternCardDao.getSelectedPatterncardOfPlayer(player);
            player.setPatternCard(patternCard);
            if (!currentPlayerUsername.equals(otherPlayerUsername)) {
                PatternCard playerPatternCard = player.getPatternCard();

                PatternCardView patternCardView = new PatternCardView(playerController);
                patternCardView.setCenterShape(true);
                patternCardView.setPlayerName(otherPlayerUsername);
                if (player.isCurrentPlayer()) {
                    patternCardView.setCurrentPlayer(true);
                } else {
                    patternCardView.setCurrentPlayer(false);
                }
                patternCardView.setPatternCard(playerPatternCard);
                patternCardView.render();
                otherPlayerPatternCardViews.getChildren().add(patternCardView);
            }
        }
    }

    private void buildToolCards() {
        toolCardViews = new ArrayList<>();
        FavorTokenDao favorTokenDao = new FavorTokenDao();

        ArrayList<ToolCard> gameToolCards = new ArrayList<>();
        gameToolCards = game.getToolCards();

        for (ToolCard toolCard : gameToolCards) {
            ArrayList<FavorToken> favorTokens = favorTokenDao.getToolCardTokens(toolCard, game);
            ToolCardView toolCardView = new ToolCardView(playerController);
            toolCardView.setToolCard(toolCard);
            toolCardView.setFavorTokens(favorTokens, game);
            toolCardView.setMaxSize(CardView.CARD_WIDTH, CardView.CARD_HEIGHT);
            toolCardView.render();
            toolCardViews.add(toolCardView);
        }
    }

    private void buildRoundTrack() {
        RoundTrack roundTrack = new RoundTrack();
        for (GameDie gameDie: game.getTrackDice()) {
            roundTrack.addGameDie(gameDie);
        }
        
        roundTrackView = new RoundTrackView(roundTrack);
        roundTrackView.render();
    }

    private void buildPublicObjectiveCards() {
        publicObjectiveCardViews = new ArrayList<>();
        PublicObjectiveCard[] gamePublicObjectiveCards = game.getPublicObjectiveCards();

        for (PublicObjectiveCard publicObjectiveCard : gamePublicObjectiveCards) {
            PublicObjectiveCardView publicObjectiveCardView =
                    new PublicObjectiveCardView(playerController);
            publicObjectiveCardView.setPublicObjectiveCard(publicObjectiveCard);
            publicObjectiveCardView.setMaxSize(CardView.CARD_WIDTH, CardView.CARD_HEIGHT);
            publicObjectiveCardView.render();
            publicObjectiveCardViews.add(publicObjectiveCardView);
        }
    }

    private void buildChatLine() {
        ArrayList<Chatline> chatLines = game.getChatlines();
        chatLineView = new ChatLineView(playerController);

        chatLineView.setChatLines(chatLines);
        chatLineView.render();
    }

    /**
     * Builds the scoreboard inside of the game view.
     */
    private void buildScoreBoard() {
        scoreBoard = new ScoreBoardView(game, playerController);
        scoreBoard.render();
    }

    private void buildBalance() {
        balance = new Label("Betaalstenen: " + player.getFavorTokens().size());
        balance.setPadding(new Insets(0, 0, 0, 5));
    }

    private void buildPlayerPatternCard() {
        PatternCard playerPatternCard = player.getPatternCard();
        playerPatternCardView = new PatternCardView(playerController);
        playerPatternCardView.setPatternCard(playerPatternCard);
        playerPatternCardView.setPlayerName(player.getAccount().getUsername());
        if (player.isCurrentPlayer()) {
            playerPatternCardView.setCurrentPlayer(true);
        } else {
            playerPatternCardView.setCurrentPlayer(false);
        }
        playerPatternCardView.render();
    }

    private void buildPlayerPrivateObjectiveCard() {
        privateObjectiveCardView = new PrivateObjectiveCardView(player);
        privateObjectiveCardView.render();
        privateObjectiveCardView.setMaxSize(CardView.CARD_WIDTH, CardView.CARD_HEIGHT);
    }

    private void buildActionButtons() {
        actionButtons = new HBox();

        Button passButton = new Button("Beurt beeindigen");
        passButton.setOnAction(e -> playerController.actionPass());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> playerController.actionExit());

        actionButtons.getChildren().addAll(passButton, exitButton);
        actionButtons.setAlignment(Pos.BOTTOM_CENTER);
        actionButtons.setSpacing(SPACING_BETWEEN_CHILDS);
        actionButtons.setPadding(new Insets(40, 0, 0, 0));
    }
    
    private void buildDieOffer() {
        dieOfferView = new DieOfferView(this.game);
        dieOfferView.render();
    }

    @Override
    public void render() {
        getChildren().clear();

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

        secondView.getChildren().addAll(toolCardViews);
        secondView.getChildren().addAll(publicObjectiveCardViews);
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
