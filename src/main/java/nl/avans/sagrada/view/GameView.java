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
import nl.avans.sagrada.model.ToolCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameView extends VBox implements ViewInterface {
    private Game game;
    private Player player;
    private PlayerController playerController;
    
    private VBox toolcardAndRoundTrack;
    private HBox publicObjectiveCards;
    private HBox otherPlayersPatternCards;
    private HBox playerInfo;
    private Pane chatLine;
    
    public GameView(PlayerController playerController, Game game, Player player) {
        this.game = game;
        this.playerController = playerController;
        this.player = player;
        
        otherPlayersPatternCards = new HBox();
        toolcardAndRoundTrack = new VBox();
        publicObjectiveCards = new HBox();
        playerInfo = new HBox();
    }
    
    private void buildOtherPlayersPatternCard() {
        ArrayList<PatternCardView> otherPlayersPatternCardsViews = new ArrayList<>();
        ArrayList<Player> players = game.getPlayers();
        for (Player player: players) {
            String playerAccountUsername = player.getAccount().getUsername();
            String currentPlayerAccountUsername = this.player.getAccount().getUsername();
            if (!playerAccountUsername.equals(currentPlayerAccountUsername)) {
                PatternCard patternCard = player.getPatternCard();
                
                PatternCardView patternCardView = new PatternCardView(playerController);
                patternCardView.setPatternCard(patternCard);
                patternCardView.render();
                
                otherPlayersPatternCardsViews.add(patternCardView);
            }
        }
        otherPlayersPatternCards.getChildren().addAll(otherPlayersPatternCardsViews);
    }
    
    private void buildPublicObjectiveCards() {
        publicObjectiveCards.setPrefSize(Main.SCREEN_WIDTH / 3, Main.SCREEN_HEIGHT / 3);
        ArrayList<ToolCard> toolCards = game.getToolCards();
        HBox toolCardViews = new HBox();
        
        for (ToolCard toolCard: toolCards) {
            ToolCardView toolCardView = new ToolCardView(playerController);
            toolCardView.setToolCard(toolCard);
            toolCardView.render();
            toolCardViews.getChildren().add(toolCardView);
        }
        publicObjectiveCards.getChildren().add(toolCardViews);
    }
    
    private void buildRoundTrack() {
        Pane roundTrack = new Pane();
        roundTrack.setBackground(new Background(new BackgroundFill(Color.RED, null ,null)));
        toolcardAndRoundTrack.getChildren().add(roundTrack);
    }
    
    private void buildChat() {
        chatLine = new Pane();
        chatLine.setPrefWidth(Main.SCREEN_WIDTH / 3);
        chatLine.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }
    
    private void buildPlayerInfo() {
        Label balanceLabel = new Label("Balance " + player.getFavorTokens().size());
        
        PatternCard patternCard = player.getPatternCard();
        
        PatternCardView patternCardView = new PatternCardView(playerController);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();
        
        Pane privateObjectiveCard = new Pane();
        privateObjectiveCard.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        
        playerInfo.getChildren().addAll(balanceLabel, patternCardView, privateObjectiveCard);
    }

    @Override
    public void render() {
        getChildren().clear();
        
        buildOtherPlayersPatternCard();
        buildPublicObjectiveCards();
        buildRoundTrack();
        buildChat();
        buildPlayerInfo();
        
        HBox secondView = new HBox();
        secondView.setPrefHeight(Main.SCREEN_HEIGHT / 3);
        secondView.getChildren().add(toolcardAndRoundTrack);
        secondView.getChildren().add(publicObjectiveCards);
        
        HBox thirdView = new HBox();
        thirdView.setPrefHeight(Main.SCREEN_HEIGHT / 3);
        thirdView.getChildren().add(chatLine);
        thirdView.getChildren().add(playerInfo);
        
        getChildren().add(otherPlayersPatternCards);
        getChildren().add(secondView);
        getChildren().add(thirdView);
    }
}
