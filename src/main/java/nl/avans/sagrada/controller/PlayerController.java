package nl.avans.sagrada.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.dao.FavorTokenDao;
import nl.avans.sagrada.dao.PublicObjectiveCardDao;
import nl.avans.sagrada.dao.ToolcardDao;
import nl.avans.sagrada.model.FavorToken;
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
     * Handles the logic behind a toolcard payment. The method first checks if
     * a player has already paid for a toolcard before or not, and if the player has sufficient funds.
     * <p>
     * If the toolcard has not received payment before, the player will hand over one favortoken as payment
     * for the toolcard. This toolcard's status will then be set to "has already been paid for before".
     * </br>
     * If the toolcard has received payment before, then the player will hand over two
     * favortokens as payment for the toolcard.
     * </p>
     * <p>
     * If the player has insufficient funds, a message will appear on screen 
     * informing the player about their lack of funds, and the player will not be able to use this toolcard.
     * </p>
     * 
     * @param game Game
     * @param toolcard Toolcard
     */
    public void payForToolcard(Game game, Toolcard toolcard) {
        FavorTokenDao favorTokenDao = new FavorTokenDao();
        
        if (favorTokenDao.getFavortokensOfPlayer(player).size() > 0) {
            if (!toolcard.hasBeenPaidForBefore()) {
                FavorToken favorToken = player.getFavorTokens().get(0);
                favorTokenDao.setFavortokensForToolcard(favorToken, toolcard, game);
                player.getFavorTokens().remove(0);
                toolcard.setHasBeenPaidForBefore(true);
            } else {
                if (favorTokenDao.getFavortokensOfPlayer(player).size() > 1) {
                    FavorToken favorToken = player.getFavorTokens().get(0);
                    favorTokenDao.setFavortokensForToolcard(favorToken, toolcard, game);
                    player.getFavorTokens().remove(0);
                    FavorToken favorToken2 = player.getFavorTokens().get(0);
                    favorTokenDao.setFavortokensForToolcard(favorToken2, toolcard, game);
                    player.getFavorTokens().remove(0);
                } else {
                    handleToolcardPaymentRejection(game);
                }
            }
        } else {
            handleToolcardPaymentRejection(game);
        }
    }

    /**
     * Displays a message, informing the player that they cannot use the toolcard due to
     * insufficient funds.
     * 
     * @param game Game
     */
    private void handleToolcardPaymentRejection(Game game) {
        //visuals for unsuccessful payment are done by Ian.
        viewToolcards(game);
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
