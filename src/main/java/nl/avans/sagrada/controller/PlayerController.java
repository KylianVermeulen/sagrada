package nl.avans.sagrada.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
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

    public void seeToolcards(Game game) {
        BorderPane pane = new BorderPane();
        
        ToolCardView toolCardView1 = new ToolCardView(this);
        toolCardView1.setToolCard(toolcardDAO.getToolcardsOfGame(game).get(0));
        toolCardView1.getToolCard().setImageUrl("/images/toolcard1.png"); //image werkt nu raar
        toolCardView1.render();        
        
        ToolCardView toolCardView2 = new ToolCardView(this);
        toolCardView2.setToolCard(toolcardDAO.getToolcardsOfGame(game).get(1));
        toolCardView2.getToolCard().setImageUrl("/images/toolcard1.png"); //image werkt nu raar
        toolCardView2.render();
        
        ToolCardView toolCardView3 = new ToolCardView(this);
        toolCardView3.setToolCard(toolcardDAO.getToolcardsOfGame(game).get(2));
        toolCardView3.getToolCard().setImageUrl("/images/toolcard1.png"); //image werkt nu raar
        toolCardView3.render();        
        
        pane.setLeft(toolCardView1);
        pane.setCenter(toolCardView2);
        pane.setRight(toolCardView3);
        myScene.setRootPane(pane);
    }
    
    /**
     * Displays the selected toolcard from the current game.
     * @param game Game
     * @param selection int
     * @param imageUrl String
     */
    public void seeToolcard(Game game, int selection, String imageUrl) {
        Pane pane = new Pane();
        
        ToolCardView toolCardView = new ToolCardView(this);
        toolCardView.setToolCard(toolcardDAO.getToolcardsOfGame(game).get(selection));
        toolCardView.getToolCard().setImageUrl(imageUrl);
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
