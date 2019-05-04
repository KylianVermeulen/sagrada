package nl.avans.sagrada.controller;

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
     * Assigns three random toolcards to the current game (given as parameter).
     * Firstly, the method makes a total of three random (double) numbers, which
     * in turn are converted into integers after rounding the doubles.
     * Then, the method checks whether the toolcardID from the array is the first
     * entry of the array or not, and, if not, checks if the current id is the
     * same as the previous array entry id. If these two are the same, the current id gets
     * an increase of one in order to make sure the two ids are not the same. If the new
     * value of the current toolcard id is higher than 12, the id gets decreased by 2.
     * If the current toolcard id is the third array entry, the method ensures that this
     * new value (as described above) is not the same as the first AND second array entries.
     * If, before the scenario as pictured above takes place, the current toolcard id is the third entry,
     * and the current toolcard id is the same as the first array entry, the same action as above
     * takes place, except now another increase in id happens. If, again, this value is higher than 12,
     * the value gets a decrease of two.
     * 
     * @param game Game
     */
    public void assignRandomToolcards(Game game) {
        ToolcardDao toolcardDao = new ToolcardDao();
        int counter = 0;
        int[] randomToolCardIds = new int[3];
        double[] randomNumbers = new double[3];
        
        while (counter < 3) {
            randomNumbers[counter] = (Math.random()*11) + 1;
            randomToolCardIds[counter] = (int) Math.round(randomNumbers[counter]);
            if ((counter - 1) >= 0 & randomToolCardIds[counter] == randomToolCardIds[counter-1]) {
                randomToolCardIds[counter]++;
                if ((counter - 2) >= 0 & randomToolCardIds[counter] == randomToolCardIds[counter-2]) {
                    randomToolCardIds[counter]++;
                    if (randomToolCardIds[counter] > 12) {
                        randomToolCardIds[counter] = (randomToolCardIds[counter-2] - 2);
                    }
                } else if (randomToolCardIds[counter] > 12) {
                    randomToolCardIds[counter] = (randomToolCardIds[counter-1] - 2);
                    if ((counter - 2) >= 0 & randomToolCardIds[counter] == randomToolCardIds[counter-2]) {
                        randomToolCardIds[counter]--;
                    }
                }
            }
            toolcardDao.addToolcardToGame(toolcardDao.getToolcardById(randomToolCardIds[counter]), game);
            counter++;
        }
    }

    /**
     * Example code
     *
     * @param game the game to view the three Toolcards of.
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
