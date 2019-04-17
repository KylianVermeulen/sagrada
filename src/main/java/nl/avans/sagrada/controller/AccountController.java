package nl.avans.sagrada.controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
import nl.avans.sagrada.view.GameOverviewView;
import nl.avans.sagrada.view.InviteOverviewView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.PopupView;

public class AccountController {
    private Account account;
    private MyScene myScene;
    private AccountDAO accountDao;
    private InviteDAO inviteDao;
    private PlayerDAO playerDAO;
    private GameDAO gameDAO;
    private PopupView pv;
    
    
    public AccountController(MyScene myScene) {
        this.myScene = myScene;
        accountDao = new AccountDAO();
        inviteDao = new InviteDAO();
        playerDAO = new PlayerDAO();
        gameDAO = new GameDAO();
        pv = new PopupView(AlertType.NONE, "", null);
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
            System.out.println("TEST: username is te kort!");
            pv.createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in gebruikersnaam lengte." , "De ingevulde gebruikersnaam is te kort!");
            return;
        }
        if (password.length() < 3) {
            System.out.println("TEST: wachtwoord is te kort!");
            pv.createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in wachtwoord lengte." , "Het ingevulde wachtwoord is te kort!");
            return;
        }
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match = pt.matcher(password);
        if (match.find()) {
            pv.createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in wachtwoord inhoud." , "Gebruik alleen letters en cijfers voor uw wachtwoord.");
            System.out.println("TEST: wachtwoord heeft leestekens!");
            return;
        }
        account.setUsername(username);
        account.setPassword(password);
        if (accountDao.accountExists(account)) {
            pv.createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Gebruiker bestaat al." , "Kies een andere gebruikersnaam.");
            System.out.println("TEST: account bestaat al!");
            return;
        }
        accountDao.addAccount(account);
        pv.createPopup(AlertType.INFORMATION, ButtonType.OK, "Bevestiging" , "Uw account is succesvol aangemaakt!", "Uw gebruikersnaam: " + account.getUsername());
    }
    
    /**
     * Switches the current pane to the "Login screen" pane.
     */
    public void gotoLogin() {
        System.out.println("TEST: go to login screen!");
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
}
