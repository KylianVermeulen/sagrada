package nl.avans.sagrada.view;

import java.util.ArrayList;

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
    
    public GameView(PlayerController playerController, Game game, Player player) {
        this.game = game;
        this.playerController = playerController;
        this.player = player;
        
        otherPlayersPatternCards = new HBox();
        toolcardAndRoundTrack = new VBox();
        publicObjectiveCards = new HBox();
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

    @Override
    public void render() {
        getChildren().clear();
        
        buildOtherPlayersPatternCard();
        buildPublicObjectiveCards();
        buildRoundTrack();
        
        HBox secondView = new HBox();
        secondView.getChildren().add(toolcardAndRoundTrack);
        secondView.getChildren().add(publicObjectiveCards);
        
        getChildren().add(otherPlayersPatternCards);
        getChildren().add(secondView);
    }
}
