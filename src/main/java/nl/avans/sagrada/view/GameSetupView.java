package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class GameSetupView extends BorderPane implements ViewInterface {
    private AccountController accountController;
    private VBox gameSelectorPane;
    private ArrayList<Account> accounts;
    private Account account;
    private VBox inviteContainer;

    private final Insets padding = new Insets(10, 10, 10, 10);

    public GameSetupView(AccountController accountController, ArrayList<Account> accounts, Account currentAccount) {
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setPadding(padding);
        this.accountController = accountController;
        this.accounts = accounts;
        this.account = currentAccount;
    }

    public void setInviteAbleAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void render() {
        buidGameSelector();
        buildInviteList();
        setLeft(gameSelectorPane);
        setCenter(inviteContainer);
    }

    private void buidGameSelector() {
        RadioButton[] radioButtons = new RadioButton[2];
        radioButtons[0] = new RadioButton("Normale patroonkaarten");
        radioButtons[1] = new RadioButton("Gegenereerde patroonkaarten");

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(radioButtons);

        Label label = new Label("Kies spel modus");

        gameSelectorPane = new VBox();
        gameSelectorPane.getChildren().add(label);
        gameSelectorPane.getChildren().addAll(radioButtons);
        gameSelectorPane.setPrefSize(Main.SCREEN_WIDTH / 3, 400);
        gameSelectorPane.setAlignment(Pos.BASELINE_LEFT);
    }

    private void buildInviteList() {
        inviteContainer = new VBox();
        inviteContainer.setPrefSize(Main.SCREEN_WIDTH / 3, Main.SCREEN_HEIGHT);
        
        Label label = new Label("Invite spelers");
        getChildren().add(label);
        for (Account account : accounts) {
            InviteView inviteView = new InviteView(accountController, account);
            inviteView.render();
            inviteContainer.getChildren().add(inviteView);
        }
    }

}
