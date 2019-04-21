package nl.avans.sagrada.controller;

import javafx.scene.layout.Pane;
import nl.avans.sagrada.dao.ToolcardDAO;
import nl.avans.sagrada.model.*;
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

    public void seeToolcards() {

    }
    public void seeToolcard(Game game, int selection) {
        Pane pane = new Pane();
        
        ToolCardView toolCardView = new ToolCardView(this);
        toolCardView.setToolCard(toolcardDAO.getToolcardsOfGame(game).get(selection));
        toolCardView.render();
        
        pane.getChildren().add(toolCardView);
        myScene.setRootPane(pane);
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
        myScene.setRootPane(pane);
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
