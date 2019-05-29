package nl.avans.sagrada.task;

import nl.avans.sagrada.dao.ChatlineDao;
import nl.avans.sagrada.model.Game;

public class GetGameChatLinesTask extends Task<ArrayList<ChatLine>>{
    private Game game;

    public GetGameChatLinesTask(Game game) {
        this.game = game;
    }

    public ArrayList<ChatLine> call() {
        return new ChatlineDao().getChatlinesOfGame(game);
    }

}
