package nl.avans.sagrada.controller;

import javafx.scene.layout.Pane;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.MyScene;

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

    public void seePatterncardOfPlayer(Player player) {
        Pane pane = new Pane();
        PatternCard patternCard = player.getPatternCard();
    }

    public void useToolcard(Toolcard toolcard) {

    }

    public void toggleCheatmode() {

    }

    public void placeDie(GameDie die, PatternCardField patterncardField) {

    }

    public void leaveGame() {

    }
}
