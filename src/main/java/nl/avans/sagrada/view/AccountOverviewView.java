package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class AccountOverviewView extends ScrollPane implements ViewInterface {
    private final int PANE_WIDTH = Main.SCREEN_WIDTH / 5;
    private final int PANE_HEIGHT = Main.SCREEN_HEIGHT;
    private final int STAT_BUTTON_HEIGHT = 50;
    private final int STAT_BUTTON_WIDTH = 50;
    private ArrayList<Account> accounts;
    private AccountController accountController;

    /**
     * Filled Constructor
     * 
     * @param accountController PlayerController
     */
    public AccountOverviewView(AccountController accountController) {
        this.accountController = accountController;
        setMaxHeight(PANE_HEIGHT);
        setMinHeight(PANE_HEIGHT);
        setMaxWidth(PANE_WIDTH);
        setMinWidth(PANE_WIDTH);
        setPannable(true);
    }
    
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    
    @Override
    public void render() {
        getChildren().clear();
        VBox vbox = new VBox();
        for (Account account : accounts) {
            Pane pane = new Pane();
            Label label = new Label("Account: " + account.getUsername());
            label.setPadding(new Insets(20, 4, 20, 65));

            Button statsButton = buildButtonToViewStatsOfAccount(account);

            pane.getChildren().add(statsButton);
            pane.getChildren().add(label);
            vbox.getChildren().add(pane);
        }
        setContent(vbox);
    }

    /**
     * Builds a button to view the statistics of an account.
     * 
     * @return Button
     */
    private Button buildButtonToViewStatsOfAccount(Account account) {
        Button button = new Button();
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        button.getStylesheets().add(css);
        button.setId("statsButton");
        button.setPrefSize(STAT_BUTTON_WIDTH, STAT_BUTTON_HEIGHT);
        return button;
    }
}
