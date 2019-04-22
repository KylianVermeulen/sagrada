package nl.avans.sagrada.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.dao.ToolcardDAO;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardView;
import nl.avans.sagrada.view.ToolCardView;

public class PlayerController {
    private Player player;
    private MyScene myScene;

    public PlayerController(MyScene myScene) {
        this.myScene = myScene;
        toolcardDAO = new ToolcardDAO();
    }

    public void seeToolcards(Game game) {
        BorderPane pane = new BorderPane();
        ToolCardView[] toolcardviews = new ToolCardView[3];
        for (int index = 0; index < toolcardviews.length; index++) {
            toolcardviews[index] = new ToolCardView(this);
            toolcardviews[index].setToolCard(toolcardDAO.getToolcardsOfGame(game).get(index));
            toolcardviews[index].render();
        }

        BorderPane.setMargin(toolcardviews[0], new Insets(0, 5, 0, 0));
        BorderPane.setMargin(toolcardviews[1], new Insets(0, 5, 0, 5));
        BorderPane.setMargin(toolcardviews[2], new Insets(0, 0, 0, 5));
        pane.setLeft(toolcardviews[0]);
        pane.setCenter(toolcardviews[1]);
        pane.setRight(toolcardviews[2]);
        myScene.setRootPane(pane);
    }
    
    /**
     * Displays the selected toolcard from the current game.
     * @param game Game
     * @param selection int
     * @param imageUrl String
     */
    public void seeToolcard(Game game, int selection) {
        Pane pane = new Pane();
        
        ToolCardView toolCardView = new ToolCardView(this);
        toolCardView.setToolCard(toolcardDAO.getToolcardsOfGame(game).get(selection));
        toolCardView.render();
        
        pane.getChildren().add(toolCardView);
        myScene.setRootPane(pane);
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
