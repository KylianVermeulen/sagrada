package nl.avans.sagrada.controller;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import nl.avans.sagrada.view.LoginView;
import nl.avans.sagrada.view.MyScene;

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
	 * @param username
	 * @param password
	 * @return
	 */
	public Account login(String username, String password) {
		Account accountFromDao = accountDao.getAccountByUsername(username);
		if (accountFromDao != null) {
			if (accountFromDao.getPassword().equals(password)) {
				System.out.println("go to HomeScreen");
				Alert alert = new Alert("Login succesfull", "You are now logged in", AlertType.SUCCES);
			} else {
				Alert alert = new Alert("Passeword invalid", "password is incorrect, try again", AlertType.ERROR);
			}
		} else {
			System.out.println("no user");
			Alert alert = new Alert("Username invalid", "Username does not excists.", AlertType.ERROR);
		}
		return accountFromDao;
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

	/**
	 * Switches the current pane to the "register screen" pane.
	 */
	public void goRegister() {
		System.out.println("Go to register screen");
	}

	/**
	 * Shows the login view, allowing the login screen to be displayed as current
	 * screen.
	 */
	public void viewLogin() {
		Pane pane = new Pane();

		LoginView loginView = new LoginView(this);
		loginView.render();
		pane.getChildren().add(loginView);

		myScene.setContentPane(pane);
	}
}
