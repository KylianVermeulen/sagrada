package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.task.GetGameChatLinesTask;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class ChatLineView extends VBox implements ViewInterface {
    private final int CHATPANE_HEIGHT = 200;
    private final int CHATPANE_WIDTH = 300;
    private final int TEXTFIELD_HEIGHT = 25;
    private final int TEXTFIELD_WIDTH = 300;
    private PlayerController playercontroller;
    private VBox messagebox;
    private ArrayList<Chatline> chatlines;
    private ScrollPane chatpane;

    /**
     * Constructor
     * 
     * @param playercontroller PlayerController
     */
    public ChatLineView(PlayerController playercontroller) {
        this.playercontroller = playercontroller;
        chatlines = new ArrayList<>();
        messagebox = new VBox();
    }
    
    /**
     * Sets the chatlines a view needs to show
     * @param chatlines
     */
    public void setChatLines(ArrayList<Chatline> chatlines) {
        this.chatlines = chatlines;
    }
    
    public void addChatline(Chatline chatline) {
        chatlines.add(chatline);
    }

    /**
     * Method that builds the view for the chat
     */
    private void buildChat() {
        chatpane = new ScrollPane();
        chatpane.setContent(messagebox);
        chatpane.setMaxHeight(CHATPANE_HEIGHT);
        chatpane.setMinHeight(CHATPANE_HEIGHT);
        chatpane.setMaxWidth(CHATPANE_WIDTH);
        chatpane.setMinWidth(CHATPANE_WIDTH);
        chatpane.setVvalue(1.0);

        HBox downpane = new HBox();
        TextField textfield = new TextField();
        textfield.setMaxHeight(TEXTFIELD_HEIGHT);
        textfield.setMinHeight(TEXTFIELD_HEIGHT);
        textfield.setMaxWidth(TEXTFIELD_WIDTH);
        textfield.setMinWidth(TEXTFIELD_WIDTH);
        textfield.setOnAction(e -> {
            playercontroller.actionSendMessage(textfield.getText(), this);
            textfield.clear();
        });

        downpane.getChildren().add(textfield);
        getChildren().addAll(chatpane, downpane);
    }

    /**
     * Method that adds a message to the view
     * 
     * @param chatline Chatline
     */
    public void addMessage(Chatline chatline) {
        int hour = chatline.getTimestamp().getHours();
        int minute = chatline.getTimestamp().getMinutes();
        int second = chatline.getTimestamp().getSeconds();

        String playername = chatline.getPlayer().getAccount().getUsername();
        String message = chatline.getMessage();

        Label label = new Label(
                "[" + hour + ":" + minute + ":" + second + "] " + playername + ": " + message);
        messagebox.getChildren().add(label);
    }

    /**
     * Method that adds a array of existing messages to the view
     */
    public void addExistingMessages() {
        for (int i = 0; i < chatlines.size(); i++) {
            addMessage(chatlines.get(i));
        }
    }

    @Override
    public void render() {
        getChildren().clear();
        messagebox.getChildren().clear();
        buildChat();
        GetGameChatLinesTask ggclt = new GetGameChatLinesTask(playercontroller.getPlayer().getGame());
        ggclt.setOnSucceeded(e -> {
            setChatLines(ggclt.getValue());
            addExistingMessages();
            chatpane.setVvalue(1.0);
        });
        Thread thread = new Thread(ggclt);
        thread.setDaemon(true);
        thread.start();
    }
}
