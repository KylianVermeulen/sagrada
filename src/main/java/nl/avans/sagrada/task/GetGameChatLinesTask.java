package nl.avans.sagrada.task;

import java.util.ArrayList;
import javafx.concurrent.Task;
import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;

public class GetGameChatLinesTask extends Task<ArrayList<Chatline>> {
    private Game game;

    public GetGameChatLinesTask(Game game) {
        this.game = game;
    }

    public ArrayList<Chatline> call() {
        return new ChatlineDao().getChatlinesOfGame(game);
    }
}
