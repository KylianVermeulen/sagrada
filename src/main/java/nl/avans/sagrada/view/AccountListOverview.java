package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.interfaces.ViewInterface;

import java.util.ArrayList;

public class AccountListOverview extends VBox implements ViewInterface {
    private static final int PANE_WIDTH = Main.SCREEN_WIDTH / 5;
    private static final int PANE_HEIGHT = Main.SCREEN_HEIGHT;
    private ArrayList<Account> accounts;
    private AccountController accountController;

    /**
     * Partial constructor
     *
     * @param accountController AccountController
     */
    public AccountListOverview(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
    }

    /**
     * Set accounts to view
     *
     * @param accounts ArrayList<Account>
     */
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Renders the view with all the information
     */
    @Override
    public void render() {
        getChildren().clear();
        for (Account account : accounts) {
            HBox hBox = new HBox();

            Label username = new Label("Account: " +  account.getUsername());
            username.setPadding(new Insets(5, 4, 5, 4));
            username.setPrefWidth(150);

            Button viewStatsButton = buildButtonToViewStats();

            hBox.getChildren().add(username);
            hBox.getChildren().add(viewStatsButton);
            getChildren().add(hBox);
        }
    }

    /**
     * Builds a button to view stats
     * @return Button
     */
    private Button buildButtonToViewStats() {
        Button button = new Button("stats");
        button.setOnAction(e -> accountController.gameOverview());
        return button;
    }
}
