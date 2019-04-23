package nl.avans.sagrada.controller;

import javafx.scene.layout.Pane;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardView;
import nl.avans.sagrada.view.PrivateObjectiveCardView;

public class PlayerController {
    private Player player;
    private MyScene myScene;

    public PlayerController(MyScene myScene, Player player) {
        this.myScene = myScene;
        this.player = player;
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

    /**
     * Makes a random generated patternCard
     *
     * (adding the difficultly in PatternCard does not matter for a random generated
     * patternCard just make sure standard is false)
     */
    public void makeRandomPatternCard() {
        Pane pane = new Pane();
        PatternCard patternCard = new PatternCard(1, 0, false);
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

    public void makeDie() {
        GameDie gameDie = new GameDie(6, "geel");
        DieView dieView = new DieView();
        dieView.setGameDie(gameDie);
        dieView.render();
        myScene.setContentPane(dieView);

    }

    public void placeDie(GameDie die, PatternCardField patterncardField) {

    }

    public void leaveGame() {

    }

    public void viewPrivateObjectiveCard() {
        Pane pane = new Pane();
        PrivateObjectiveCardView privateObjectiveCardView = new PrivateObjectiveCardView(this);
        privateObjectiveCardView.render();

        pane.getChildren().add(privateObjectiveCardView);
        myScene.setContentPane(pane);
    }
}
