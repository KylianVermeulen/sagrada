package nl.avans.sagrada.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.dao.ToolcardDAO;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardView;
import nl.avans.sagrada.view.ToolCardView;

import java.util.ArrayList;

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

    public void makeClickPlacement() {
        HBox mainPane = new HBox();
        VBox secondPane = new VBox();

        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();

        PatternCard patternCard = new PatternCard(1, 0, false);
        PatternCardView patternCardView = new PatternCardView(this);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();

        GameDie gameDie1 = new GameDie(1, "geel");
        DieView dieView1 = new DieView();
        dieView1.setGameDie(gameDie1);
        dieView1.render();

        GameDie gameDie2 = new GameDie(3, "paars");
        DieView dieView2 = new DieView();
        dieView2.setGameDie(gameDie2);
        dieView2.render();

        GameDie gameDie3 = new GameDie(5, "rood");
        DieView dieView3 = new DieView();
        dieView3.setGameDie(gameDie3);
        dieView3.render();

        pane1.setPadding(new Insets(5));
        pane2.setPadding(new Insets(5));
        pane3.setPadding(new Insets(5));

        pane1.getChildren().add(dieView1);
        pane2.getChildren().add(dieView2);
        pane3.getChildren().add(dieView3);

        secondPane.setPadding(new Insets(5));
        secondPane.getChildren().addAll(pane1, pane2, pane3);
        mainPane.getChildren().addAll(patternCardView, secondPane);
        myScene.setContentPane(mainPane);
    }

    public void makeDie(int eyes, String color) {
        Pane pane = new Pane();
        GameDie gameDie = new GameDie(eyes, color);
        DieView dieView = new DieView();
        dieView.setGameDie(gameDie);
        dieView.render();
        pane.getChildren().add(dieView);

        myScene.setContentPane(pane);

    }


    public void emptyPatternCard() {
        Pane pane = new Pane();
        PatternCard patternCard = new PatternCard();
        PatternCardView patternCardView = new PatternCardView(this);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();

        pane.getChildren().add(patternCardView);
        myScene.setContentPane(pane);
    }

    public void placeDie(GameDie die, PatternCardField patterncardField) {
        patterncardField.placeDie(die);
    }

    public void leaveGame() {

    }
}
