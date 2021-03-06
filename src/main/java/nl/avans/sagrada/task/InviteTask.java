package nl.avans.sagrada.task;

import java.util.ArrayList;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.dao.InviteDao;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;

public class InviteTask implements Runnable {
    private Game game;
    private ArrayList<Account> accounts;
    
    /**
     * Constructor for the task
     * 
     * @param game
     * @param accounts
     */
    public InviteTask(Game game, ArrayList<Account> accounts) {
        this.game = game;
        this.accounts = accounts;
    }

    /**
     * Sends the invites to the accounts and set the optional patternCards
     */
    @Override
    public void run() {
        InviteDao inviteDao = new InviteDao();
        GameDao gameDao = new GameDao();
        for (Account invitedAccount : accounts) {
            Invite invite = new Invite();
            invite.setGame(game);
            invite.setInvitedAccount(invitedAccount);
            inviteDao.addInvite(invite);
        }
        ArrayList<Player> players = gameDao.getPlayersOfGame(game);
        game.setPlayers(players);
        game.addRandomRoundsToGameDice();
        game.setOptionPatternCardsForPlayers();
    }
}
