package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import nl.avans.sagrada.dao.AccountDao;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.task.ActiveGameTask;
import nl.avans.sagrada.task.AllAccountsTask;
import nl.avans.sagrada.task.AllGamesTask;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class LobbyView extends BorderPane implements ViewInterface {
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 40;
    private final int COMBOBOX_WIDTH = 250;
    private final int COMBOBOX_HEIGHT = 5;
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
    private ComboBox comboBox;
    private BackgroundSize size =
            new BackgroundSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, false, false, true, false);
    private ArrayList<Game> allGames;

    /**
     * Constructor
     */
    public LobbyView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        String css = this.getClass().getResource("/css/lobbyview.css").toExternalForm();
        getStylesheets().add(css);

        setBackground(
                new Background(new BackgroundImage(LOBBY_BACKGROUND, BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)));
        accounts = new ArrayList<>();
    }

    /**
     * Set all the invites that need to be presented
     */
    public void setInvites(ArrayList<Invite> invites) {
        this.invites = invites;
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
        ActiveGameTask agt = new ActiveGameTask(accountController.getAccount());
        agt.setOnSucceeded(e -> {
            games = agt.getValue();
            gameOverview.setGames(games);
            gameOverview.render();
        });
        Thread th = new Thread(agt);
        th.setDaemon(true);
        th.start();
    }

    /**
     * Builds the overview of all accounts.
     */
    private void buildAccountsOverview() {
        accountOverview = new AccountOverviewView(accountController);
        AllAccountsTask act = new AllAccountsTask();
        act.setOnSucceeded(e -> {
            accounts = act.getValue();
            accountOverview.setAccounts(accounts);
            accountOverview.render();
        });
        Thread th = new Thread(act);
        th.setDaemon(true);
        th.start();
    }

    /**
     * Builds to button to logout
     */
    private void buildLogout() {
        TilePane pane = new TilePane();
        pane.setMaxWidth(100);
        pane.setMinWidth(100);

        logoutButton = new Button("Uitloggen");
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
        Label allGamesLabel = new Label("Alle games");
        Label inviteLabel = new Label("Invites van spelers");
        inviteLabel.setTextFill(Color.WHITE);
        Label gameOverviewLabel = new Label("Je openstaande spellen");
        gameOverviewLabel.setTextFill(Color.WHITE);
        vbox.getChildren().addAll(inviteLabel, inviteOverview, gameOverviewLabel, gameOverview);
        setLeft(vbox);
        vbox2.getChildren()
                .addAll(logoutButton, playerLabel, accountOverview, comboBox, allGamesLabel,
                        allGamesOverview);
        vbox2.setAlignment(Pos.CENTER_RIGHT);
        vbox2.setPadding(new Insets(0, 17, 0, 0));
        setRight(vbox2);
    }

    /**
     * Builds the ComboBox that you use to filter the games on wins or set it back to normal
     */
    public void buildComboBox() {
        comboBox = new ComboBox();
        String option1 = "Normaal";
        String option2 = "Gewonnen games";
        comboBox.setPrefSize(COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        comboBox.getItems().add(option1);
        comboBox.getItems().add(option2);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setOnAction(e -> {
            if (comboBox.getValue().equals(option1)) {
                AllAccountsTask allAccountsTask = new AllAccountsTask();
                allAccountsTask.setOnSucceeded(event -> {
                    accountOverview.setAccounts(allAccountsTask.getValue());
                    accountOverview.render();
                });
                new Thread(allAccountsTask).start();
            }
            if (comboBox.getValue().equals(option2)) {
                AllAccountsTask allAccountsTask = new AllAccountsTask();
                allAccountsTask.setOnSucceeded(event -> {
                    accounts = allAccountsTask.getValue();
                    AccountDao accountDao = new AccountDao();
                    ArrayList<Account> winsPerAccount = accountDao.getWinsPerAccount();
                    ArrayList<Account> addAccounts = new ArrayList<Account>();
                    boolean hasAcc = false;
                    for (Account account : accounts) {
                        for (Account checkAccounts : winsPerAccount) {
                            if (account.getUsername().equals(checkAccounts.getUsername())) {
                                hasAcc = true;
                            }
                        }
                        if (!hasAcc) {
                            addAccounts.add(account);
                        }
                        hasAcc = false;
                    }
                    winsPerAccount.addAll(addAccounts);
                    accountOverview.setAccounts(winsPerAccount);
                    accountOverview.render();
                });
                new Thread(allAccountsTask).start();
            }
        });
    }

    private void buildAllGamesOverview() {
        allGamesOverview = new AllGamesOverView();
        AllGamesTask agt = new AllGamesTask();
        agt.setOnSucceeded(e -> {
            allGames = agt.getValue();
            allGamesOverview.setGames(allGames);
            allGamesOverview.render();
        });
        Thread th = new Thread(agt);
        th.setDaemon(true);
        th.start();
    }

    public void setAllGames(ArrayList<Game> allgames) {
        this.allGames = allgames;
    }
}
