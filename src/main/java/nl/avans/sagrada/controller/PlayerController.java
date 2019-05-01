package nl.avans.sagrada.controller;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.dao.PublicObjectiveCardDao;
import nl.avans.sagrada.dao.ToolcardDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.PublicObjectiveCard;
import nl.avans.sagrada.model.Toolcard;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardView;
import nl.avans.sagrada.view.PublicObjectiveCardView;
import nl.avans.sagrada.view.ToolCardView;

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
        ToolCardView[] toolcardviews = new ToolCardView[3];
        ArrayList<Toolcard> toolcards = toolcardDao.getToolcardsOfGame(game);
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
        PublicObjectiveCardView[] publicobjectivecardviews = new PublicObjectiveCardView[3];
        ArrayList<PublicObjectiveCard> publicObjectiveCards = publicObjectiveCardDao
                .getAllPublicObjectiveCardsOfGame(game);
        for (int index = 0; index < publicobjectivecardviews.length; index++) {
            publicobjectivecardviews[index] = new PublicObjectiveCardView(this);
            publicobjectivecardviews[index].setPublicObjectiveCard(publicObjectiveCards.get(index));
            publicobjectivecardviews[index].render();
        }

        BorderPane.setMargin(publicobjectivecardviews[0], new Insets(0, 5, 0, 0));
        BorderPane.setMargin(publicobjectivecardviews[1], new Insets(0, 5, 0, 5));
        BorderPane.setMargin(publicobjectivecardviews[2], new Insets(0, 0, 0, 5));
        pane.setLeft(publicobjectivecardviews[0]);
        pane.setCenter(publicobjectivecardviews[1]);
        pane.setRight(publicobjectivecardviews[2]);
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

    /**
     * Example code
     */
    public void makeDie() {
        GameDie gameDie = new GameDie(1, "geel", 6);
        DieView dieView = new DieView();
        dieView.setGameDie(gameDie);
        dieView.render();
        myScene.setContentPane(dieView);
    }
}
