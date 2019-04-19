package nl.avans.sagrada.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
    
    public PlayerController(MyScene myScene) {
        this.myScene = myScene;
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
    
    public void sendMessage(String message) {
    	Date date = new Date();
		long time = date.getTime();
		
		Timestamp timestamp = new Timestamp(time);
    	Chatline chatline = new Chatline(player, message, timestamp);
    	ArrayList<Chatline> chatlines = player.getGame().getChatlines();
    	
    	ChatLineView chatview = new ChatLineView(this);
    	chatview.addExistingMessages(chatlines);
    	chatview.addMessage(chatline);
    
    	player.getGame().addChatLine(chatline);
    	
    	chatline.addtoDB();

    	myScene.setRootPane(chatview);
    }

	public void setPlayer(Player player) {
		this.player = player;
	}
}
