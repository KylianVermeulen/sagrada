package nl.avans.sagrada.view;


import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Chatline;

public class ChatLineView extends VBox {
	
	private static final int CHATPANE_HEIGHT = 200;
	private static final int CHATPANE_WIDTH = 300;
	
	private static final int TEXTFIELD_HEIGHT = 25;
	private static final int TEXTFIELD_WIDTH = 300;
	
	private PlayerController playercontroller;
	private VBox messagebox;
	
	public ChatLineView(PlayerController playercontroller) {
		this.playercontroller = playercontroller;
		messagebox = new VBox();
		buildChat();
	}
	
	private void buildChat() {
		
		ScrollPane chatpane = new ScrollPane();
		chatpane.setContent(messagebox);
		chatpane.setMaxHeight(CHATPANE_HEIGHT);
		chatpane.setMinHeight(CHATPANE_HEIGHT);
		chatpane.setMaxWidth(CHATPANE_WIDTH);
		chatpane.setMinWidth(CHATPANE_WIDTH);
		
		HBox downpane = new HBox();
		TextField textfield = new TextField();
		textfield.setMaxHeight(TEXTFIELD_HEIGHT);
		textfield.setMinHeight(TEXTFIELD_HEIGHT);
		textfield.setMaxWidth(TEXTFIELD_WIDTH);
		textfield.setMinWidth(TEXTFIELD_WIDTH);
		textfield.setOnAction(e -> playercontroller.sendMessage(textfield.getText()));
		
		downpane.getChildren().add(textfield);
		getChildren().addAll(chatpane, downpane);
		
	}

	public VBox getMessagebox() {
		return messagebox;
	}
	
	public void addMessage(Chatline chatline) {
		int hour = chatline.getTimestamp().getHours();
		int minute = chatline.getTimestamp().getMinutes();
		
		String playername = chatline.getPlayer().getAccount().getUsername();
		String message = chatline.getMessage();
		
		if(message.matches("")) {
			return;
			
		} else {
			Label label = new Label("[" + hour + ":" + minute + "] " + playername + ": " + message);
			
			messagebox.getChildren().add(label);
		}
		
	}
	
	public void addExistingMessages(ArrayList<Chatline> chatlines) {
		if(chatlines != null) {
			for(int i = 0; i < chatlines.size(); i++) {
				addMessage(chatlines.get(i));
			}
		}
		
	}
	


}	
