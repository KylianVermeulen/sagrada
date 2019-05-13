package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameSetupView extends VBox implements ViewInterface {
    private final Insets padding = new Insets(10, 10, 10, 10);
    private final int BUTTON_WIDTH = 100;
    private final int BUTTON_HEIGHT = 50;
    private AccountController accountController;
    private VBox gameSelectorPane;
    private HBox bottumButtonPane;
    private ArrayList<Account> accounts;
    private ScrollPane inviteContainer;
    private Game game;
    private Button startButton;
    private Button backButton;
    private ArrayList<InviteView> inviteViews;

    /**
     * Constructor
     */
    public GameSetupView(AccountController accountController, ArrayList<Account> accounts,
            Game game) {
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        String css = this.getClass().getResource("/css/inviteview.css").toExternalForm();
        getStylesheets().add(css);
        setPadding(padding);
        this.accountController = accountController;
        this.accounts = accounts;
        this.game = game;
    }

    @Override
    public void render() {
        buidGameSelector();
        buildInviteList();
        buildStartAndBackButton();
        Label inviteLabel = new Label("Invite spelers: ");
        getChildren().addAll(gameSelectorPane, inviteLabel, inviteContainer, bottumButtonPane);
    }

    /**
     * Builds the game selector
     */
    private void buidGameSelector() {
        RadioButton[] radioButtons = new RadioButton[2];
        radioButtons[0] = new RadioButton("Normale patroonkaarten");
        radioButtons[0].setSelected(true);
        radioButtons[0].setPadding(new Insets(15, 0, 5, 10));
        radioButtons[0].setOnAction(e -> {
            game.setGamemode(Game.GAMEMODE_NORMAL);
        });
        radioButtons[1] = new RadioButton("Gegenereerde patroonkaarten");
        radioButtons[1].setPadding(new Insets(5, 0, 5, 10));
        radioButtons[1].setOnAction(e -> {
            game.setGamemode(Game.GAMEMODE_GENERATED);
        });

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(radioButtons);

        Label label = new Label("Kies spel modus: ");

        gameSelectorPane = new VBox();
        gameSelectorPane.getChildren().add(label);
        gameSelectorPane.getChildren().addAll(radioButtons);
        gameSelectorPane.setPrefSize(Main.SCREEN_WIDTH / 3, Main.SCREEN_HEIGHT / 7);
        gameSelectorPane.setAlignment(Pos.BASELINE_LEFT);
    }

    /**
     * Builds the list of all accounts that can be invited
     */
    private void buildInviteList() {
        inviteContainer = new ScrollPane();
        inviteContainer.setPannable(true);
        inviteContainer.setPrefSize(Main.SCREEN_WIDTH - 200, Main.SCREEN_HEIGHT / 1.5);

        inviteViews = new ArrayList<>();
        VBox invites = new VBox();

        invites.setMaxHeight(100);
        invites.setMinWidth(Main.SCREEN_WIDTH - 30);

        for (Account account : accounts) {
            InviteView inviteView = new InviteView(account);
            inviteView.render();
            inviteViews.add(inviteView);
        }
        invites.getChildren().addAll(inviteViews);

        inviteContainer.setContent(invites);
    }

    private void buildStartAndBackButton() {
        bottumButtonPane = new HBox();
        bottumButtonPane.setSpacing(1060);
        bottumButtonPane.setMinHeight(80);
        bottumButtonPane.setAlignment(Pos.CENTER);
        
        startButton = new Button("Opslaan");
        startButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setOnAction(e -> accountController.actionSendInvites(inviteViews, game));

        backButton = new Button("Back");
        backButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        backButton.setOnAction(e -> accountController.viewLobby());
        backButton.setPadding(padding);

        bottumButtonPane.getChildren().addAll(startButton, backButton);
    }

}
