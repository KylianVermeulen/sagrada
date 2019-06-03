package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.model.Chatline;

public class AddChatlineTask implements Runnable {
    private Chatline chatline;

    /**
     * Constructor for the task to add a chatline
     * @param chatline
     */
    public AddChatlineTask(Chatline chatline){
        this.chatline = chatline;
    }

    @Override
    public void run() {
        new ChatlineDao().addChatline(chatline);
    }
}
