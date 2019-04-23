package nl.avans.sagrada.controller;

import javafx.scene.layout.Pane;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardView;
import nl.avans.sagrada.view.PrivateObjectiveCardView;

public class PlayerController {
    private Player player;
    private MyScene myScene;
    
    public PlayerController(MyScene myScene) {
        this.myScene = myScene;
    }

    public void seeToolcards() {

    }
    public void seeToolcard() {

    }

    public void overviewOfGame() {

    }

    public void viewPatterncardOfPlayer(Player player) {
        Pane pane = new Pane();
        PatternCard patternCard = player.getPatternCard();

        PatternCardView patternCardView = new PatternCardView(this);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();

        pane.getChildren().add(patternCardView);
        myScene.setContentPane(pane);
    }

    public void useToolcard(Toolcard toolcard) {

    }

    public void toggleCheatmode() {

    }

    public void placeDie(GameDie die, PatternCardField patterncardField) {

    }

    public void leaveGame() {

    }
    
    public void viewPrivateObjectiveCard() {
        Pane pane = new Pane();
        
    }
}
