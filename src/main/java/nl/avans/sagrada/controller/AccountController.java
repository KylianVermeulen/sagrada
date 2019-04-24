package nl.avans.sagrada.controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import nl.avans.sagrada.view.GameSetupView;
import nl.avans.sagrada.view.InviteView;
import nl.avans.sagrada.view.LobbyView;
import nl.avans.sagrada.view.LoginView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.RegisterView;
import nl.avans.sagrada.view.popups.Alert;
import nl.avans.sagrada.view.popups.AlertType;

public class AccountController {
    private Account account;
    private MyScene myScene;

    public AccountController(MyScene myScene) {
        this.myScene = myScene;
    }

    /**
     * Login an account and checks is username and password combination is right.
     *
     * @param userTextField TextField
     * @param passwordTextField TextField
     */
    public void login(TextField userTextField, TextField passwordTextField) {
        AccountDAO accountDAO = new AccountDAO();
        String username = userTextField.getText();
        String password = passwordTextField.getText();
        Account accountFromDao = accountDAO.getAccountByUsername(username);
        if (accountFromDao != null) {
            if (accountFromDao.getPassword().equals(password)) {
                Alert alert = new Alert("Login geslaagd", "Je bent nu ingelogd.", AlertType.SUCCES);
                myScene.addAlertPane(alert);
                account = accountFromDao;
                viewLobby();
            } else {
                Alert alert = new Alert("Password ongeldig", "Password is niet geldig.",
                        AlertType.ERROR);
                myScene.addAlertPane(alert);
            }
        } else {
            Alert alert = new Alert("Username ongeldig", "Username bestaat niet.", AlertType.ERROR);
            myScene.addAlertPane(alert);
        }
    }

    /**
     * Registers a user via username and password, and checks for certain requirements linked to the
     * username and password.
     *
     * @param username String
     * @param password String
     */
    public void register(String username, String password) {
        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account();
        if (username.length() < 3) {
            Alert alert = new Alert("Username invalid", "Username must be 3 characters.",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        if (password.length() < 3) {
            Alert alert = new Alert("Password invalid", "Password must be 3 characters.",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match = pt.matcher(password);
        if (match.find()) {
            Alert alert = new Alert("Password invalid",
                    "Password can only contain letters and numbers.", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        account.setUsername(username);
        account.setPassword(password);
        if (accountDAO.accountExists(account)) {
            Alert alert = new Alert("Username invalid", "Username already exists.",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        accountDAO.addAccount(account);
        Alert alert = new Alert("Account created", "Account is now created.", AlertType.SUCCES);
        myScene.addAlertPane(alert);
    }

    /**
     * Controlls the logout
     */
    public void logout() {
        account = null;
        viewLogin();
    }

    /**
     * Controlls the accept of a invite
     */
    public void acceptInvite(Invite invite) {
        InviteDAO inviteDAO = new InviteDAO();
        invite.acceptInvite();
        inviteDAO.updateInvite(invite);
        viewLobby();
    }

    /**
     * Controlls the deny of a invite
     */
    public void denyInvite(Invite invite) {
        InviteDAO inviteDAO = new InviteDAO();
        invite.denyInvite();
        inviteDAO.updateInvite(invite);
        Game game = invite.getGame();
        game.cancel();
        viewLobby();
    }

    public void joinGame(Game game) {
    }

    public void setupNewGame() {
        GameDAO gameDAO = new GameDAO();
        PlayerDAO playerDAO = new PlayerDAO();
        AccountDAO accountDAO = new AccountDAO();

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

        ArrayList<Account> accounts = accountDAO.getAllInviteableAccounts(account);
        GameSetupView gameSetupView = new GameSetupView(this, accounts, game);
        gameSetupView.render();
        pane.getChildren().add(gameSetupView);

        myScene.setContentPane(pane);
    }

    public void viewLobby() {
        AccountDAO accountDAO = new AccountDAO();
        Pane pane = new Pane();
        account = accountDAO.getAccountByUsername(account.getUsername());

        ArrayList<Invite> pendingInvites = account.getAllPendingInvites();
        ArrayList<Game> games = account.getActiveGames();

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
        InviteDAO inviteDAO = new InviteDAO();

        ArrayList<Account> invitedAccounts = new ArrayList<>();
        for (InviteView inviteView : inviteViews) {
            if (inviteView.getCheckbox().isSelected()) {
                invitedAccounts.add(inviteView.getAccount());
            }
        }
        if (invitedAccounts.size() == 0) {
            System.out.println("Te weinig accounts ge-invite");
            Alert alert = new Alert("Invites niet verstuurd", "Te weinig accounts geselecteerd",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        if (invitedAccounts.size() > 3) {
            Alert alert = new Alert("Invites niet verstuurd", "Te veel accounts geselecteerd",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }

        for (Account invitedAccount : invitedAccounts) {
            if (invitedAccount.hasPendingInviteFromAccount(account)) {
                String subMessage =
                        "Account: " + invitedAccount.getUsername() + " heeft al een invite";
                Alert alert = new Alert("Al een active invite", subMessage, AlertType.ERROR);
                myScene.addAlertPane(alert);
                return;
            }
        }

        for (Account invitedAccount : invitedAccounts) {
            Invite invite = new Invite();
            invite.setGame(game);
            invite.setInvitedAccount(invitedAccount);
            inviteDAO.addInvite(invite);
        }
        Alert alert = new Alert("Invites verstuurd", "Invites zijn verstuurd", AlertType.INFO);
        myScene.addAlertPane(alert);
        game.setOptionPatternCardsForPlayers();
        viewLobby();
    }
}
