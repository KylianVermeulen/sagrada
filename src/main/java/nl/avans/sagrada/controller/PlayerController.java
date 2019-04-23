package nl.avans.sagrada.controller;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import nl.avans.sagrada.dao.ToolcardDAO;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.CardView;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PatternCardView;
import nl.avans.sagrada.view.ToolCardView;

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
     * @param game Game
     */
    public void viewToolcards(Game game) {
        BorderPane pane = new BorderPane();
        ToolCardView[] toolcardviews = new ToolCardView[3];
        ArrayList<Toolcard> toolcards = toolcardDAO.getToolcardsOfGame(game);
        for (int index = 0; index < toolcardviews.length; index++) {
            toolcardviews[index] = new ToolCardView(this, game);
            Toolcard toolcard = toolcards.get(index);
            toolcardviews[index].setToolCard(toolcards.get(index));
            toolcardviews[index].setToolCardSelection(index);
            toolcardviews[index].render();
            toolcardviews[index].setOnMouseClicked(e -> viewToolcard(game, toolcard));
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
     * @param game Game
     * @param selection int
     */
    public void viewToolcard(Game game, Toolcard toolcard) {
        Pane pane = new Pane();
        
        ToolCardView toolCardView = new ToolCardView(this, game);
        toolCardView.setZoomed(true);
        toolCardView.setToolCard(toolcard);
        toolCardView.render();
        toolCardView.setPrefSize(CardView.ZOOM_CARD_WIDTH, CardView.ZOOM_CARD_HEIGHT);
        BorderPane toolCardViewPane = new BorderPane();
        StackPane useToolCardButtonPane = new StackPane();
        Button useButton = new Button("Gebruik Gereedschapskaart");
        useButton.setOnAction(e -> useToolcard(toolcard));
        useToolCardButtonPane.getChildren().add(useButton);
        useToolCardButtonPane.setPrefSize(CardView.ZOOM_CARD_WIDTH, (CardView.ZOOM_CARD_HEIGHT / 6));   
        toolCardViewPane.setBottom(useToolCardButtonPane);
        toolCardViewPane.setCenter(toolCardView);
        pane.getChildren().add(toolCardViewPane);
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
