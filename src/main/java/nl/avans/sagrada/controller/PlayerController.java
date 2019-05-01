package nl.avans.sagrada.controller;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.dao.ToolcardDAO;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.*;

public class PlayerController {
    private Player player;
    private MyScene myScene;
    private ToolcardDAO toolcardDAO;

    public PlayerController(MyScene myScene) {
        this.myScene = myScene;
        toolcardDAO = new ToolcardDAO();
    }

    /**
     * Displays all toolcards that belong to a certain game.
     *
     * @param game Game
     */
    public void viewToolcards(Game game) {
        BorderPane pane = new BorderPane();
        ToolCardView[] toolcardviews = new ToolCardView[3];
        ArrayList<Toolcard> toolcards = toolcardDAO.getToolcardsOfGame(game);
        for (int index = 0; index < toolcardviews.length; index++) {
            toolcardviews[index] = new ToolCardView(this);
            toolcardviews[index].setToolCard(toolcards.get(index));
            toolcardviews[index].render();
        }

        BorderPane.setMargin(toolcardviews[0], new Insets(0, 5, 0, 0));
        BorderPane.setMargin(toolcardviews[1], new Insets(0, 5, 0, 5));
        BorderPane.setMargin(toolcardviews[2], new Insets(0, 0, 0, 5));
        pane.setLeft(toolcardviews[0]);
        pane.setCenter(toolcardviews[1]);
        pane.setRight(toolcardviews[2]);
        myScene.setContentPane(pane);
    }

    /**
     * Displays the selected toolcard for the current game.
     *
     * @param game Game
     * @param selection int
     */
    public void viewToolcard(Game game, int selection) {
        Pane pane = new Pane();

        ToolCardView toolCardView = new ToolCardView(this);
        toolCardView.setToolCard(toolcardDAO.getToolcardsOfGame(game).get(selection));
        toolCardView.render();

        pane.getChildren().add(toolCardView);
        myScene.setContentPane(pane);
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


    public void showRoundTrack() {
        RoundTrack roundTrack = new RoundTrack();
        RoundTrackView roundTrackView = new RoundTrackView(roundTrack);
        myScene.setContentPane(roundTrackView);
    }

    /**
     * Makes a random generated patternCard
     * 
     * (adding the difficultly in PatternCard does not matter for a random generated patternCard just make sure standard is false)
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

    public void kaasje() {
        RoundTrack roundTrack = new RoundTrack();

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

}
