package nl.avans.sagrada.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.TextField;
import nl.avans.sagrada.dao.ChatlineDAO;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.Toolcard;
import nl.avans.sagrada.view.ChatLineView;
import nl.avans.sagrada.view.MyScene;

public class PlayerController {
    private Player player;
    private MyScene myScene;
    
    private ChatlineDAO chatlineDAO;
    
    public PlayerController(MyScene myScene) {
        this.myScene = myScene;
        chatlineDAO = new ChatlineDAO();
    }

    public void seeToolcards() {

    }
    public void seeToolcard() {

    }

    public void overviewOfGame() {

    }

    public void seePatterncardOfPlayer(Player player) {

    }

    public void useToolcard(Toolcard toolcard) {

    }

    public void toggleCheatmode() {

    }

    public void placeDie(GameDie die, PatternCardField patterncardField) {

    }

    public void leaveGame() {

    }
    
    public void sendMessage(TextField textfield) {
		String text = textfield.getText();

    	Chatline chatline = new Chatline(player, text);
		
    	chatlineDAO.setTime(chatline);
		
    	if (chatlineDAO.timeExists(chatline) == false) {
    		
        	chatlineDAO.addChatline(chatline);
        	
        	ArrayList<Chatline> chatlines = player.getGame().getChatlines();
        	
        	ChatLineView chatview = new ChatLineView(this);
        	chatview.addExistingMessages(chatlines);
        	chatview.addMessage(chatline);
        
        	player.getGame().addChatLine(chatline);
        	
        	myScene.setRootPane(chatview);
    	} else {
    		System.out.println("alert");
    	}
    }

	public void setPlayer(Player player) {
		this.player = player;
	}
}
