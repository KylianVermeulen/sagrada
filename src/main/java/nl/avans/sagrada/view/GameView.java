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
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.PublicObjectiveCard;
import nl.avans.sagrada.model.ToolCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameView extends VBox implements ViewInterface {
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
    private Pane roundTrack;
    private Pane chatLine;
    private Pane privateObjectiveCardView;
    
    public GameView(PlayerController playerController, Game game, Player player) {
        this.game = game;
        this.playerController = playerController;
        this.player = player;
    }
    
    private void buildOtherPlayerPatternCards() {
        otherPlayerPatternCardViews = new HBox();
        ArrayList<Player> players = game.getPlayers();
        
        
        for (Player player: players) {
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
        
        for (ToolCard toolcard: gameToolCards) {
            ToolCardView toolcardView = new ToolCardView(playerController);
            toolcardView.setToolCard(toolcard);
            toolcardView.render();
            
            toolCardViews.add(toolcardView);
        }
    }
    
    private void buildRoundTrack() {
        roundTrack = new Pane();
        roundTrack.setPrefSize(350, 66);
        roundTrack.setMaxSize(350, 70);
        roundTrack.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
    }
    
    private void buildPublicObjectiveCards() {
        publicObjectiveCardViews = new ArrayList<>();
        PublicObjectiveCard[] gamePublicObjectiveCards = game.getPublicObjectiveCards();
        
        for (PublicObjectiveCard publicObjectiveCard: gamePublicObjectiveCards) {
            PublicObjectiveCardView publicObjectiveCardView = new PublicObjectiveCardView(playerController);
            publicObjectiveCardView.setPublicObjectiveCard(publicObjectiveCard);
            publicObjectiveCardView.render();
            publicObjectiveCardViews.add(publicObjectiveCardView);
        }
    }
    
    private void buildChatLine() {
        chatLine = new Pane();
        chatLine.setPrefHeight(200);
        chatLine.setPrefWidth(Main.SCREEN_WIDTH / 3);
        chatLine.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
    }
    
    private void buildScoreBoard() {
        scoreBoard = new Pane();
        scoreBoard.setPrefSize(300, 300);
        scoreBoard.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
    }
    
    private void buildBalance() {
        balance = new Label("Balance " + player.getScore());
    }
    
    private void buildPlayerPatternCard() {
        PatternCard playerPatternCard = player.getPatternCard();
        playerPatternCardView = new PatternCardView(playerController);
        playerPatternCardView.setPatternCard(playerPatternCard);
        playerPatternCardView.render();
    }
    
    private void buildPlayerPrivateObjectiveCard() {
        privateObjectiveCardView = new Pane();
        privateObjectiveCardView.setPrefSize((CardView.CARD_WIDTH / 1.2), (CardView.CARD_WIDTH / 4));
        privateObjectiveCardView.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
    }
    
    private void buildActionButtons() {
        actionButtons = new HBox();
        
        Button passButton = new Button("Pass");
        passButton.setOnAction(e -> playerController.actionPass());
        
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> playerController.exit());
        
        actionButtons.getChildren().addAll(passButton, exitButton);
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
        firstView.setPrefHeight(PatternCardView.PATTERNCARD_HEIGHT);
        firstView.setPrefWidth(Main.SCREEN_WIDTH);
        firstView.setPadding(new Insets(0,0,20,0));
        
        HBox secondView = new HBox();
        secondView.setAlignment(Pos.CENTER);
        secondView.setPadding(new Insets(0,0,20,0));
        BorderPane thirdView = new BorderPane();
        thirdView.setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT / 3);
        
        firstView.setCenter(otherPlayerPatternCardViews);
        firstView.setRight(scoreBoard);
       
        
        secondView.getChildren().addAll(toolCardViews);
        secondView.getChildren().addAll(publicObjectiveCardViews);
        secondView.getChildren().add(roundTrack);
        
        thirdView.setLeft(chatLine);
        thirdView.setRight(privateObjectiveCardView);
        thirdView.setBottom(actionButtons);
        
        HBox thirdViewCenterBox = new HBox();
        thirdViewCenterBox.getChildren().addAll(balance, playerPatternCardView);
        thirdViewCenterBox.setPrefWidth(Main.SCREEN_WIDTH / 3);
        thirdView.setCenter(thirdViewCenterBox);
        
        
        
        getChildren().add(firstView);
        getChildren().add(secondView);
        getChildren().add(thirdView);
    }
}
