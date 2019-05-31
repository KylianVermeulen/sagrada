package nl.avans.sagrada.task;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.model.Chatline;

public class ChatlineTimeTask extends Task<Boolean> {
    private Chatline chatline;

    public ChatlineTimeTask(Chatline chatline){
        this.chatline = chatline;
    }

    @Override
    protected Boolean call() throws Exception {
        return new ChatlineDao().timeExistsOfPlayer(chatline);
    }
}
