package nl.avans.sagrada.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class InviteView extends HBox implements ViewInterface {
    private final int BUTTON_WIDTH = 25;
    private final int BUTTON_HEIGHT = 25;
    
    private final int PANE_WIDTH = 200;
    private final int PANE_HEIGHT = 50;
    
    private AccountController accountController;
    private Account account;
        
    public InviteView(AccountController accountController, Account account) {
        this.accountController = accountController;
        this.account = account;
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
    }

    @Override
    public void render() {
        Label label = new Label("Invite: " + account.getUsername());
        Button button = new Button();
        button.setText("->");
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setOnAction(e->accountController.sendInvite(account));
        getChildren().addAll(label, button);
    }
}
