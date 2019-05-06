package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
        chatLine.setPrefSize(300, 200);
        chatLine.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
    }
    
    private void buildScoreBoard() {
        scoreBoard = new Pane();
        scoreBoard.setPrefSize(100, 300);
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
        
        HBox firstView = new HBox();
        HBox secondView = new HBox();
        HBox thirdView = new HBox();
        
        firstView.getChildren().addAll(otherPlayerPatternCardViews);
        firstView.getChildren().add(scoreBoard);
        
        HBox combinedToolCards = new HBox();
        combinedToolCards.getChildren().addAll(toolCardViews);
        VBox combinedToolCardsAndRoundTrack = new VBox();
        combinedToolCardsAndRoundTrack.getChildren().addAll(combinedToolCards, roundTrack);
        
        secondView.getChildren().add(combinedToolCardsAndRoundTrack);
        secondView.getChildren().addAll(publicObjectiveCardViews);
        
        thirdView.getChildren().add(chatLine);
        thirdView.getChildren().add(balance);
        thirdView.getChildren().add(playerPatternCardView);
        thirdView.getChildren().add(privateObjectiveCardView);
        
        
        getChildren().add(firstView);
        getChildren().add(secondView);
        getChildren().add(thirdView);
    }
}
