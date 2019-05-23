package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.concurrent.Task;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.dao.InviteDao;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;

public class InviteTask implements Runnable {
    private AccountController accountController;
    private Game game;
    private ArrayList<Account> accounts;
    
    public InviteTask(AccountController accountController, Game game, ArrayList<Account> accounts) {
        this.accountController = accountController;
        this.game = game;
        this.accounts = accounts;
    }

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
