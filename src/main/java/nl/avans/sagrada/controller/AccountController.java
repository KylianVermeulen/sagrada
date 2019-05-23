package nl.avans.sagrada.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.dao.AccountDao;
import nl.avans.sagrada.dao.GameDao;
import nl.avans.sagrada.dao.InviteDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.enumerations.AccountStatus;
import nl.avans.sagrada.task.InviteTask;
import nl.avans.sagrada.view.GameSetupView;
import nl.avans.sagrada.view.InviteView;
import nl.avans.sagrada.view.LobbyView;
import nl.avans.sagrada.view.LoginView;
import nl.avans.sagrada.view.MyScene;
import nl.avans.sagrada.view.RegisterView;
import nl.avans.sagrada.view.popups.Alert;
import nl.avans.sagrada.view.popups.AlertType;
import nl.avans.sagrada.view.popups.Stats;

public class AccountController {
    private Account account;
    private MyScene myScene;

    public AccountController(MyScene myScene) {
        this.myScene = myScene;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    /**
     * Displays the login view as content pane. Allows the user to login with a username and
     * password.
     */
    public void viewLogin() {
        Pane pane = new Pane();
        LoginView loginView = new LoginView(this);
        loginView.render();
        pane.getChildren().add(loginView);
        myScene.setContentPane(pane);
    }

    /**
     * Login a user using a username and password obtained by the view. Checks the username and
     * password from the database.
     *
     * @param username the username of the account
     * @param password the password of the account
     */
    public void actionLogin(String username, String password) {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.getAccountByUsername(username);

        if (account != null) {
            if (account.getPassword().equals(password)) {
                Alert alert = new Alert("Login geslaagd", "Je bent nu ingelogd.", AlertType.SUCCES);
                myScene.addAlertPane(alert);
                this.account = account;
                viewLobby();
            } else {
                Alert alert =
                        new Alert("Password ongeldig", "Password is niet geldig.", AlertType.ERROR);
                myScene.addAlertPane(alert);
            }
        } else {
            Alert alert = new Alert("Username ongeldig", "Username bestaat niet.", AlertType.ERROR);
            myScene.addAlertPane(alert);
        }
    }

    /**
     * Displays the register view as content pane. Allows the user to register with a username and
     * password.
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
     * Registers a user using a username and password, and checks for certain requirements linked to
     * the username and password.
     *
     * @param username the username of the new account
     * @param password the password of the new account
     */
    public void actionRegister(String username, String password) {
        AccountDao accountDao = new AccountDao();
        Account account = new Account();

        if (username.length() < 3) {
            Alert alert = new Alert("Username ongeldig",
                    "Username moet minstens 3 characters zijn.", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        if (password.length() < 3) {
            Alert alert = new Alert("Password ongeldig", "Password moet minstens 3 characters zijn",
                    AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match = pt.matcher(password);
        if (match.find()) {
            Alert alert = new Alert("Password ongeldig",
                    "Moet alleen letters en/of cijfers bevatten.", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }

        account.setUsername(username);
        account.setPassword(password);
        if (accountDao.accountExists(account)) {
            Alert alert = new Alert("Username ongeldig", "Username bestaat al.", AlertType.ERROR);
            myScene.addAlertPane(alert);
            return;
        }
        accountDao.addAccount(account);
        Alert alert = new Alert("Account aangemaakt", "Account is aangemaakt.", AlertType.SUCCES);
        myScene.addAlertPane(alert);
        viewLogin();
    }

    /**
     * Logs the user out of the game. Will display the login view.
     */
    public void actionLogout() {
        account = null;
        viewLogin();
    }

    /**
     * Displays the lobby view of the account. Receives the pending invites and games from the
     * database.
     */
    public void viewLobby() {
        AccountDao accountDao = new AccountDao();
        Pane pane = new Pane();
        account = accountDao.getAccountByUsername(account.getUsername());
        ArrayList<Invite> pendingInvites = account.getPendingInvites();
        ArrayList<Game> games = account.getActiveGames();
        ArrayList<Account> accounts = accountDao.getAllAccounts();

        LobbyView lobbyView = new LobbyView(this);
        lobbyView.setInvites(pendingInvites);
        lobbyView.setGames(games);
        lobbyView.setAccounts(accounts);
        lobbyView.render();

        pane.getChildren().add(lobbyView);
        myScene.setContentPane(pane);
        account.setAccountStatus(AccountStatus.LOBBY);
    }

    /**
     * Displays the setup game view as content pane. Makes a game object and start players object
     * and saved to the database.
     */
    public void actionSetupNewGame() {
        GameDao gameDao = new GameDao();
        PlayerDao playerDao = new PlayerDao();
        AccountDao accountDao = new AccountDao();

        int gameId = gameDao.getNextGameId();
        Timestamp creationDate = gameDao.getTime();
        Game game = new Game();
        game.setId(gameId);
        game.setCreationDate(creationDate);
        gameDao.addGame(game);
        game.assignRandomToolCards();
        game.assignRandomPublicObjectiveCards();
        game.addDice();
        game.setPlayers(gameDao.getPlayersOfGame(game));

        int playerId = playerDao.getNextPlayerId();
        Player player = new Player();
        player.setId(playerId);
        player.setSeqnr(1);
        player.setPlayerStatus("uitdager");
        player.setIsCurrentPlayer(true);
        player.setAccount(account);
        player.setGame(game);
        player.setPrivateObjectivecardColor(game.getRandomAvailablePrivateColor());
        playerDao.addPlayer(player);

        game.setTurnPlayer(player);
        gameDao.updateGame(game);
        ArrayList<Account> accounts = accountDao.getAllInviteableAccounts(account);

        Pane pane = new Pane();
        GameSetupView gameSetupView = new GameSetupView(this, accounts, game);
        gameSetupView.render();

        pane.getChildren().add(gameSetupView);
        myScene.setContentPane(pane);
        account.setAccountStatus(AccountStatus.SETUP);
    }

    /**
     * Displays the lobby view as content pane when all checks have passed. Checks for the count of
     * invites and if start player hasn't already sent an invite to an invited player.
     *
     * @param inviteViews list of invites
     * @param game the game object for which the invites are.
     */
    public void actionSendInvites(ArrayList<InviteView> inviteViews, Game game) {
        ArrayList<Account> invitedAccounts = new ArrayList<>();
        for (InviteView inviteView : inviteViews) {
            if (inviteView.getCheckbox().isSelected()) {
                invitedAccounts.add(inviteView.getAccount());
            }
        }

        if (invitedAccounts.size() == 0) {
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
        
        InviteTask inviteTask = new InviteTask(game, invitedAccounts);
        Thread inviteThread = new Thread(inviteTask);
        inviteThread.setName("Sending invites");
        inviteThread.start();
        Alert alert = new Alert("Invites verstuurd", "Invites zijn verstuurd", AlertType.INFO);
        myScene.addAlertPane(alert);
        System.out.println("Done in ctrl");
        viewLobby();
    }

    /**
     * Accept an invite and will set the lobby view as content pane.
     */
    public void actionAcceptInvite(Invite invite) {
        InviteDao inviteDao = new InviteDao();

        invite.acceptInvite();
        inviteDao.updateInvite(invite);
        Player player = invite.getPlayer();
        player.setGame(invite.getGame());
        account.setAccountStatus(AccountStatus.GAME);
        myScene.getPlayerController().setPlayer(player);
        myScene.getPlayerController().viewOptionalPatternCards();
    }

    /**
     * Denies an invite and will set the lobby view as content pane.
     */
    public void actionDenyInvite(Invite invite) {
        InviteDao inviteDao = new InviteDao();
        invite.denyInvite();
        inviteDao.updateInvite(invite);
        Game game = invite.getGame();
        game.cancel();
        viewLobby();
    }

    /**
     * Join a game.
     *
     * @param game the game to join
     */
    public void actionJoinGame(Game game) {
        myScene.getPlayerController().actionJoinGame(account, game);
        account.setAccountStatus(AccountStatus.GAME);
    }

    public void viewStats(Account account) {
        Stats stats = new Stats(myScene, account);
        myScene.addPopupPane(stats);
    }
}
