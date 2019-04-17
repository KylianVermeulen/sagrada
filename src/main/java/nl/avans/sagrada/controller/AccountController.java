package nl.avans.sagrada.controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.GameDAO;
import nl.avans.sagrada.dao.InviteDAO;
import nl.avans.sagrada.dao.PlayerDAO;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.*;

import java.util.ArrayList;

public class AccountController {
    private Account account;
    private MyScene myScene;
    private AccountDAO accountDao;
    private InviteDAO inviteDao;
    private PlayerDAO playerDAO;
    private GameDAO gameDAO;
    
    public AccountController(MyScene myScene) {
        this.myScene = myScene;
        accountDao = new AccountDAO();
        inviteDao = new InviteDAO();
        playerDAO = new PlayerDAO();
        gameDAO = new GameDAO();     
    }

    public void login() {
    }
    
    /**
     * Registers a user via username and password, and checks for certain requirements linked to the username and password.
     * @param username String
     * @param password String
     */
    public void register(String username, String password) {
        Account account = new Account();
        if (username.length() < 3) {
            return;
        }
        if (password.length() < 3) {
            return;
        }
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match = pt.matcher(password);
        if (match.find()) {
            return;
        }
        account.setUsername(username);
        account.setPassword(password);
        if (accountDao.accountExists(account)) {
            return;
        }
        accountDao.addAccount(account);
    }
    
    /**
     * Switches the current pane to the "Login screen" pane.
     */
    public void gotoLogin() {
        //go to login pane
    }
    
    public void register() {
    }

    public void acceptInvite(Invite invite) {
        invite.acceptInvite();
    }

    public void denyInvite(Invite invite) {
        invite.denyInvite();
    }

    public void gameOverview() {
        Pane pane = new Pane();
        account = accountDao.getAccountByUsername("test2");
        ArrayList<Player> players = account.getPlayers();
        ArrayList<Game> games = new ArrayList<Game>();
        for (Player player : players) {
            games.add(player.getGame());
        }

        GameOverviewView gameOverview = new GameOverviewView(this);
        gameOverview.setGames(games);
        gameOverview.render();
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.getChildren().add(gameOverview);

        myScene.setRootPane(pane);
    }

    public void accountListOverview() {
        Pane pane = new Pane();
        ArrayList<Account> accounts = accountDao.getAllAccounts();

        AccountListOverview accountListOverview = new AccountListOverview(this);
        accountListOverview.setAccounts(accounts);
        accountListOverview.render();
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.getChildren().add(accountListOverview);

        myScene.setRootPane(pane);
    }

    public void joinGame(Game game) {
    }

    public void setupNewGame() {
    }

    public void inviteOverview() {
        Pane pane = new Pane();
        account = accountDao.getAccountByUsername("test1");
        ArrayList<Invite> pendingInvites = account.getAllPendingInvites();
        
        InviteOverviewView inviteOverview = new InviteOverviewView(this);
        inviteOverview.setInvites(pendingInvites);
        inviteOverview.render();
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.getChildren().add(inviteOverview);
        
        myScene.setRootPane(pane);
    }
    
    /**
     * Shows the register view, allowing the register screen to be displayed as current screen.
     */
    public void viewRegister() {
        Pane pane = new Pane();
        
        RegisterView registerView = new RegisterView(this);
        registerView.render();
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.getChildren().add(registerView);
        
        myScene.setRootPane(pane);
    }
}
