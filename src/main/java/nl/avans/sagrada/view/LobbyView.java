package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class LobbyView extends BorderPane implements ViewInterface {
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 40;
    private final Image LOBBY_BACKGROUND =
            new Image("/images/backgrounds/lobbybackground-goede-hoogte.png");
    private AccountController accountController;
    private ArrayList<Game> games;
    private ArrayList<Invite> invites;
    private ArrayList<Account> accounts;
    private InviteOverviewView inviteOverview;
    private GameOverviewView gameOverview;
    private AllGamesOverView allGamesOverview;
    private AccountOverviewView accountOverview;
    private Button newGameButton;
    private Button logoutButton;
    private BackgroundSize size =
            new BackgroundSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, false, false, true, false);
    private ArrayList<Game> allgames;

    /**
     * Constructor
     */
    public LobbyView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        String css = this.getClass().getResource("/css/lobbyview.css").toExternalForm();
        getStylesheets().add(css);

        setBackground(
                new Background(
                        new BackgroundImage(
                                LOBBY_BACKGROUND, BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size
                        )
                )
        );
    }

    /**
     * Set all the invites that need to be presented
     */
    public void setInvites(ArrayList<Invite> invites) {
        this.invites = invites;
    }

    /**
     * Set all the game the current account has
     */
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    /**
     * Set all the accounts that have been registered.
     */
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void render() {
        buildInviteOverview();
        buildGamesOverview();
        buildAllGamesOverview();
        buildAccountsOverview();
        buildNewGameBtn();
        buildLogout();
        buildOverview();
    }

    /**
     * Build the button to make a new game
     */
    private void buildNewGameBtn() {
        BorderPane pane = new BorderPane();
        newGameButton = new Button("Maak nieuw spel");
        newGameButton.setOnAction(e -> accountController.actionSetupNewGame());
        newGameButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        pane.setCenter(newGameButton);
        setCenter(pane);
    }

    /**
     * Builds the invite overview
     */
    private void buildInviteOverview() {
        inviteOverview = new InviteOverviewView(accountController);
        inviteOverview.setInvites(invites);
        inviteOverview.render();
    }

    /**
     * Builds the overview of all the games
     */
    private void buildGamesOverview() {
        gameOverview = new GameOverviewView(accountController);
        gameOverview.setGames(games);
        gameOverview.render();
    }

    /**
     * Builds the overview of all accounts.
     */
    private void buildAccountsOverview() {
        accountOverview = new AccountOverviewView(accountController);
        accountOverview.setAccounts(accounts);
        accountOverview.render();
    }

    /**
     * Builds to button to logout
     */
    private void buildLogout() {
        TilePane pane = new TilePane();
        pane.setMaxWidth(100);
        pane.setMinWidth(100);

        logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> accountController.actionLogout());

        pane.setAlignment(Pos.TOP_CENTER);
        pane.getChildren().add(logoutButton);
    }

    /**
     * Builds the lobby overview.
     */
    private void buildOverview() {
        VBox vbox = new VBox();
        VBox vbox2 = new VBox();
        Label playerLabel = new Label("Alle accounts");
        Label allgamesLabel = new Label("Alle games");
        Label inviteLabel = new Label("Invites van spelers");
        inviteLabel.setTextFill(Color.WHITE);
        Label gameOverviewLabel = new Label("Je openstaande spellen");
        gameOverviewLabel.setTextFill(Color.WHITE);
        vbox.getChildren().addAll(inviteLabel, inviteOverview, gameOverviewLabel, gameOverview);
        setLeft(vbox);
        vbox2.getChildren().addAll(logoutButton, playerLabel, accountOverview, allgamesLabel, allGamesOverview);
        vbox2.setAlignment(Pos.CENTER_RIGHT);
        vbox2.setPadding(new Insets(0, 10, 0, 0));
        setRight(vbox2);
    }

    private void buildAllGamesOverview() {
        allGamesOverview = new AllGamesOverView(accountController);
        allGamesOverview.setGames(allgames);
        allGamesOverview.render();


    }

    public void setAllGames(ArrayList<Game> allgames) {
        this.allgames = allgames;
    }
}
