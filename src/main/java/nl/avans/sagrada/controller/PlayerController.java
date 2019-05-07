package nl.avans.sagrada.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.dao.PublicObjectiveCardDao;
import nl.avans.sagrada.dao.ToolcardDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.PublicObjectiveCard;
import nl.avans.sagrada.model.Toolcard;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardView;
import nl.avans.sagrada.view.PublicObjectiveCardView;
import nl.avans.sagrada.view.ToolCardView;

import java.util.ArrayList;

public class PlayerController {
    private Player player;
    private MyScene myScene;

    public PlayerController(MyScene myScene) {
        this.myScene = myScene;
    }

    /**
     * Example code
     *
     * @param game the game to view the three Toolcard's of.
     */
    public void viewToolcards(Game game) {
        ToolcardDao toolcardDao = new ToolcardDao();
        BorderPane pane = new BorderPane();
        ToolCardView[] toolcardViews = new ToolCardView[3];
        Toolcard[] toolcards = toolcardDao.getToolcardsOfGame(game).toArray(new Toolcard[3]);
        for (int index = 0; index < toolcardViews.length; index++) {
            toolcardViews[index] = new ToolCardView(this);
            toolcardViews[index].setToolCard(toolcards[index]);
            toolcardViews[index].render();
        }

        BorderPane.setMargin(toolcardViews[0], new Insets(0, 5, 0, 0));
        BorderPane.setMargin(toolcardViews[1], new Insets(0, 5, 0, 5));
        BorderPane.setMargin(toolcardViews[2], new Insets(0, 0, 0, 5));
        pane.setLeft(toolcardViews[0]);
        pane.setCenter(toolcardViews[1]);
        pane.setRight(toolcardViews[2]);
        myScene.setContentPane(pane);
    }

    /**
     * Example code
     *
     * @param game the game to view Toolcard of.
     * @param selection the selected Toolcard to view.
     */
    public void viewToolcard(Game game, int selection) {
        ToolcardDao toolcardDao = new ToolcardDao();

        Pane pane = new Pane();
        ToolCardView toolCardView = new ToolCardView(this);
        toolCardView.setToolCard(toolcardDao.getToolcardsOfGame(game).get(selection));
        toolCardView.render();

        pane.getChildren().add(toolCardView);
        myScene.setContentPane(pane);
    }

    /**
     * Example code
     *
     * @param game Game
     */
    public void viewPublicObjectiveCards(Game game) {
        BorderPane pane = new BorderPane();
        PublicObjectiveCardDao publicObjectiveCardDao = new PublicObjectiveCardDao();
        PublicObjectiveCardView[] publicObjectiveCardViews = new PublicObjectiveCardView[3];
        PublicObjectiveCard[] publicObjectiveCards = publicObjectiveCardDao
                .getAllPublicObjectiveCardsOfGame(game).toArray(new PublicObjectiveCard[3]);
        for (int index = 0; index < publicObjectiveCardViews.length; index++) {
            publicObjectiveCardViews[index] = new PublicObjectiveCardView(this);
            publicObjectiveCardViews[index].setPublicObjectiveCard(publicObjectiveCards[index]);
            publicObjectiveCardViews[index].render();
        }

        BorderPane.setMargin(publicObjectiveCardViews[0], new Insets(0, 5, 0, 0));
        BorderPane.setMargin(publicObjectiveCardViews[1], new Insets(0, 5, 0, 5));
        BorderPane.setMargin(publicObjectiveCardViews[2], new Insets(0, 0, 0, 5));
        pane.setLeft(publicObjectiveCardViews[0]);
        pane.setCenter(publicObjectiveCardViews[1]);
        pane.setRight(publicObjectiveCardViews[2]);
        myScene.setContentPane(pane);
    }

    /**
     * Example code
     *
     * @param player the player to view the PatternCard of.
     */
    public void viewPatternCardOfPlayer(Player player) {
        Pane pane = new Pane();
        PatternCard patternCard = player.getPatternCard();
        PatternCardView patternCardView = new PatternCardView(this);
        patternCardView.setPatternCard(patternCard);
        patternCardView.render();
        pane.getChildren().add(patternCardView);
        myScene.setContentPane(pane);
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

    /**
     * Example code
     */
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
        GameDieDao gamediedao = new GameDieDao();
        gamediedao.placeDie(die, patterncardField, player);
        patterncardField.placeDie(die);
        
    }
}
