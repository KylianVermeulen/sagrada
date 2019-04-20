package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameSetupView extends VBox implements ViewInterface {
    private AccountController accountController;
    private VBox gameSelectorPane;
    private ArrayList<Account> accounts;
    private ScrollPane inviteContainer;
    private Game game;
    private Button startButton;
    private ArrayList<InviteView> inviteViews;

    private final Insets padding = new Insets(10, 10, 10, 10);
    private final int BUTTON_WIDTH = 100;
    private final int BUTTON_HEIGHT = 50;

    public GameSetupView(AccountController accountController, ArrayList<Account> accounts, Game game) {
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setPadding(padding);
        this.accountController = accountController;
        this.accounts = accounts;
        this.game = game;
    }

    public void setInviteAbleAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void render() {
        buidGameSelector();
        buildInviteList();
        buildStartButton();
        getChildren().addAll(gameSelectorPane, inviteContainer, startButton);
    }

    private void buidGameSelector() {
        RadioButton[] radioButtons = new RadioButton[2];
        radioButtons[0] = new RadioButton("Normale patroonkaarten");
        radioButtons[0].setSelected(true);
        radioButtons[0].setOnAction(e -> {
            game.setGamemode(Game.GAMEMODE_NORMAL);
        });
        radioButtons[1] = new RadioButton("Gegenereerde patroonkaarten");
        radioButtons[1].setOnAction(e -> {
            game.setGamemode(Game.GAMEMODE_GENERATED);
        });


        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(radioButtons);

        Label label = new Label("Kies spel modus");

        gameSelectorPane = new VBox();
        gameSelectorPane.getChildren().add(label);
        gameSelectorPane.getChildren().addAll(radioButtons);
        gameSelectorPane.setPrefSize(Main.SCREEN_WIDTH / 3, Main.SCREEN_HEIGHT / 4);
        gameSelectorPane.setAlignment(Pos.BASELINE_LEFT);
    }

    private void buildInviteList() {
        inviteContainer = new ScrollPane();
        inviteContainer.setPannable(true);
        inviteContainer.setPrefSize(Main.SCREEN_WIDTH / 3, Main.SCREEN_HEIGHT / 3);
        inviteContainer.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        
        inviteViews = new ArrayList<>();
        VBox invites = new VBox();
        
        Label label = new Label("Invite spelers");
        invites.getChildren().add(label);
        for (Account account : accounts) {
            InviteView inviteView = new InviteView(account);
            inviteView.render();
            inviteViews.add(inviteView);
        }
        invites.getChildren().addAll(inviteViews);
        
        inviteContainer.setContent(invites);
    }
    
    private void buildStartButton() {
        startButton = new Button("Opslaan");
        startButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        startButton.setOnAction(e-> accountController.sendInvites(inviteViews, game));
    }

}
