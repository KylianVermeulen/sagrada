package nl.avans.sagrada.controller;

import javafx.scene.control.TextField;
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
import nl.avans.sagrada.view.popups.Alert;
import nl.avans.sagrada.view.popups.AlertType;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Login an account and checks is username and password combination is right.
     *
     * @param userTextField TextField
     * @param passwordTextField TextField
     */
    public void login(TextField userTextField, TextField passwordTextField) {
        String username = userTextField.getText();
        String password = passwordTextField.getText();
        Account accountFromDao = accountDao.getAccountByUsername(username);
        if (accountFromDao != null) {
            if (accountFromDao.getPassword().equals(password)) {
                Alert alert = new Alert("Login geslaagd", "Je bent nu ingelogd.", AlertType.SUCCES);
                myScene.addAlertPane(alert);
                account = accountFromDao;
                lobby();
            } else {
                Alert alert = new Alert("Password ongeldig", "Password is niet geldig.", AlertType.ERROR);
                myScene.addAlertPane(alert);
            }
        } else {
            Alert alert = new Alert("Username ongeldig", "Username bestaat niet.", AlertType.ERROR);
            myScene.addAlertPane(alert);
        }
    }

    /**
     * Registers a user via username and password, and checks for certain requirements linked to the username and
     * password.
     *
     * @param username String
     * @param password String
     */
    public void register(String username, String password) {
        Account account = new Account();
        if (username.length() < 3) {
            Alert alert = new Alert("Username invalid", "Username must be 3 characters.", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        if (password.length() < 3) {
            Alert alert = new Alert("Password invalid", "Password must be 3 characters.", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match = pt.matcher(password);
        if (match.find()) {
            Alert alert = new Alert("Password invalid", "Password can only contain letters and numbers.",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        account.setUsername(username);
        account.setPassword(password);
        if (accountDao.accountExists(account)) {
            Alert alert = new Alert("Username invalid", "Username already exists.", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        accountDao.addAccount(account);
        Alert alert = new Alert("Account created", "Account is now created.", AlertType.SUCCES);
        myScene.addAlertPane(alert);
    }
    
    public void logout() {
        account = null;
        viewLogin();
    }

    public void acceptInvite(Invite invite) {
        invite.acceptInvite();
    }

    public void denyInvite(Invite invite) {
        invite.denyInvite();
    }

    public void joinGame(Game game) {
    }

    public void setupNewGame() {
        Pane pane = new Pane();
        int gameId = gameDAO.getNextGameId();
        Game game = new Game();
        game.setId(gameId);
        gameDAO.addGame(game);
        int playerId = playerDAO.getNextPlayerId();
        Player player = new Player();
        player.setId(playerId);
        player.setSeqnr(1);
        player.setPlayerStatus("challenger");
        player.setIsCurrentPlayer(true);
        player.setAccount(account);
        player.setGame(game);
        player.setPrivateObjectivecardColor(game.getRandomAvailablePrivateColor());
        playerDAO.addPlayer(player);

        game.setTurnPlayer(player);
        gameDAO.updateGame(game);

        ArrayList<Account> accounts = accountDao.getAllInviteableAccounts(account);
        GameSetupView gameSetupView = new GameSetupView(this, accounts, game);
        gameSetupView.render();
        pane.getChildren().add(gameSetupView);

        myScene.setContentPane(pane);
    }

    public void lobby() {
        Pane pane = new Pane();
        account = accountDao.getAccountByUsername(account.getUsername());
        // Update the account

        ArrayList<Invite> pendingInvites = account.getAllPendingInvites();
        ArrayList<Game> games = account.getGames();

        LobbyView lobbyView = new LobbyView(this);
        lobbyView.setInvites(pendingInvites);
        lobbyView.setGames(games);
        lobbyView.render();

        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.getChildren().add(lobbyView);

        myScene.setContentPane(pane);
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

        myScene.setContentPane(pane);
    }

    /**
     * Shows the login view, allowing the login screen to be displayed as current screen.
     */
    public void viewLogin() {
        Pane pane = new Pane();

        LoginView loginView = new LoginView(this);
        loginView.render();
        pane.getChildren().add(loginView);

        myScene.setContentPane(pane);
    }

    public void sendInvites(ArrayList<InviteView> inviteViews, Game game) {
        ArrayList<Account> invitedAccounts = new ArrayList<>();
        for (InviteView inviteView: inviteViews) {
            if (inviteView.getCheckbox().isSelected()) {
                invitedAccounts.add(inviteView.getAccount());
            }
        }
        if (invitedAccounts.size() == 0) {
            System.out.println("Te weinig accounts ge-invite");
            Alert alert = new Alert("Invites niet verstuurd", "Te weinig accounts geselecteerd", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        if (invitedAccounts.size() > 3) {
            Alert alert = new Alert("Invites niet verstuurd", "Te veel accounts geselecteerd", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }

        for (Account invitedAccount: invitedAccounts) {
            Invite invite = new Invite();
            invite.setGame(game);
            invite.setInvitedAccount(invitedAccount);
            inviteDao.addInvite(invite);
        }
        Alert alert = new Alert("Invites verstuurd", "Invites zijn verstuurd", AlertType.INFO);
        myScene.addAlertPane(alert);
        game.setOptionPatternCardsForPlayers();
        lobby();
    }
}
