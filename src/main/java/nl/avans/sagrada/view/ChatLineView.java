package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class ChatLineView extends VBox implements ViewInterface {
	
	private final int CHATPANE_HEIGHT = 200;
	private final int CHATPANE_WIDTH = 300;
	
	private final int TEXTFIELD_HEIGHT = 25;
	private final int TEXTFIELD_WIDTH = 300;
	
	private PlayerController playercontroller;
	private VBox messagebox;
	
	/**
	 * Constructor
	 * @param playercontroller PlayerController
	 */
	public ChatLineView(PlayerController playercontroller) {
		this.playercontroller = playercontroller;
		messagebox = new VBox();
		render();
	}
	
	/**
	 * Method that builds the view for the chat
	 */
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
	
	/**
	 * Method that adds a message to the view
	 * @param chatline Chatline
	 */
	public void addMessage(Chatline chatline) {
		int hour = chatline.getTimestamp().getHours();
		int minute = chatline.getTimestamp().getMinutes();
		int second = chatline.getTimestamp().getSeconds();
		
		String playername = chatline.getPlayer().getAccount().getUsername();
		String message = chatline.getMessage();
		
		Label label = new Label("[" + hour + ":" + minute + ":" + second + "] " + playername + ": " + message);
		messagebox.getChildren().add(label);
	}
	
	/**
	 * Method that adds a array of existing messages to the view
	 * @param chatlines Chatline
	 */
	public void addExistingMessages(ArrayList<Chatline> chatlines) {
		if(chatlines != null) {
			for(int i = 0; i < chatlines.size(); i++) {
				addMessage(chatlines.get(i));
			}
		}
		
	}

	@Override
	public void render() {
		buildChat();
	}
}	
