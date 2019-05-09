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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.PublicObjectiveCard;
import nl.avans.sagrada.model.RoundTrack;
import nl.avans.sagrada.model.ToolCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameView extends VBox implements ViewInterface {
    private final int SPACING_BETWEEN_CHILDS = 5;
    private Game game;
    private Player player;
    private PlayerController playerController;
    private HBox otherPlayerPatternCardViews;
    private HBox actionButtons;
    private ArrayList<ToolCardView> toolCardViews;
    private ArrayList<PublicObjectiveCardView> publicObjectiveCardViews;
    private Label balance;
    private PatternCardView playerPatternCardView;
    private Pane scoreBoard;
    private RoundTrackView roundTrackView;
    private ChatLineView chatLineView;
    private PrivateObjectiveCardView privateObjectiveCardView;

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

            if (!currentPlayerUsername.equals(otherPlayerUsername)) {
                PatternCard playerPatternCard = player.getPatternCard();

                PatternCardView patternCardView = new PatternCardView(playerController);
                patternCardView.setCenterShape(true);
                patternCardView.setPatternCard(playerPatternCard);
                patternCardView.render();
                otherPlayerPatternCardViews.getChildren().add(patternCardView);
            }
        }
    }

    private void buildToolCards() {
        toolCardViews = new ArrayList<>();

        ArrayList<ToolCard> gameToolCards = new ArrayList<>();
        gameToolCards = game.getToolCards();

        for (ToolCard toolCard : gameToolCards) {
            ToolCardView toolCardView = new ToolCardView(playerController);
            toolCardView.setToolCard(toolCard);
            toolCardView.setMaxSize(CardView.CARD_WIDTH, CardView.CARD_HEIGHT);
            toolCardView.render();

            toolCardViews.add(toolCardView);
        }
    }

    private void buildRoundTrack() {
        RoundTrack roundTrack = new RoundTrack();

        roundTrackView = new RoundTrackView(roundTrack);
        roundTrackView.render();
    }

    private void buildPublicObjectiveCards() {
        publicObjectiveCardViews = new ArrayList<>();
        PublicObjectiveCard[] gamePublicObjectiveCards = game.getPublicObjectiveCards();

        for (PublicObjectiveCard publicObjectiveCard : gamePublicObjectiveCards) {
            PublicObjectiveCardView publicObjectiveCardView = new PublicObjectiveCardView(
                    playerController);
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

    private void buildScoreBoard() {
        scoreBoard = new Pane();
        scoreBoard.setPrefSize(300, 300);
        scoreBoard.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
    }

    private void buildBalance() {
        balance = new Label("Betaalstenen: " + player.getScore());
        balance.setPadding(new Insets(0, 0, 0, 5));
    }

    private void buildPlayerPatternCard() {
        PatternCard playerPatternCard = player.getPatternCard();
        playerPatternCardView = new PatternCardView(playerController);
        playerPatternCardView.setPatternCard(playerPatternCard);
        playerPatternCardView.render();
    }

    private void buildPlayerPrivateObjectiveCard() {
        privateObjectiveCardView = new PrivateObjectiveCardView(player);
        privateObjectiveCardView.render();
        privateObjectiveCardView.setMaxSize(CardView.CARD_WIDTH, CardView.CARD_HEIGHT);
    }

    private void buildActionButtons() {
        actionButtons = new HBox();

        Button passButton = new Button("Pass");
        passButton.setOnAction(e -> playerController.actionPass());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> playerController.exit());

        actionButtons.getChildren().addAll(passButton, exitButton);
        actionButtons.setAlignment(Pos.BOTTOM_CENTER);
        actionButtons.setSpacing(SPACING_BETWEEN_CHILDS);
        actionButtons.setPadding(new Insets(40, 0, 0, 0));
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

        thirdView.setLeft(chatLineView);
        thirdView.setRight(privateObjectiveCardView);
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