package nl.avans.sagrada.view;

import java.util.ArrayList;
import java.util.Observable;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class AccountOverviewView extends ScrollPane implements ViewInterface {
    private final int PANE_WIDTH = Main.SCREEN_WIDTH / 5;
    private final int PANE_HEIGHT = Main.SCREEN_HEIGHT;
    private final int LABEL_WIDTH = 200;
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
        accounts = new ArrayList<>();
    }
    
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    
    @Override
    public void render() {
        
        getChildren().clear();
        setContent(null);
        VBox vbox = new VBox();
        for (Account account : accounts) {
            HBox hBox = new HBox();
            Label label = new Label("Account: " + account.getUsername());
            label.setPrefWidth(LABEL_WIDTH);
            label.setPadding(new Insets(0, 20, 0, 20));

            Button statsButton = buildButtonToViewStatsOfAccount(account);

            hBox.getChildren().add(label);
            hBox.getChildren().add(statsButton);
            vbox.getChildren().add(hBox);
        }
        setContent(vbox);
    }

    /**
     * Builds a button to view the statistics of an account.
     * 
     * @return Button
     */
    private Button buildButtonToViewStatsOfAccount(Account account) {
        Button button = new Button("Stats");
        button.setOnAction(e -> accountController.viewStats(account));
        return button;
    }
}
