package nl.avans.sagrada.view;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class InviteView extends HBox implements ViewInterface {
    private final int PANE_WIDTH = 200;
    private final int PANE_HEIGHT = 50;
    
    private Account account;
    private CheckBox checkbox;
        
    /**
     * Constructor
     * @param account
     */
    public InviteView(Account account) {
        this.account = account;
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
    }

    @Override
    public void render() {
        checkbox = new CheckBox(account.getUsername());
        getChildren().add(checkbox);
    }
    
    /**
     * Get the account that is in this view
     * @return
     */
    public Account getAccount() {
        return account;
    }
    
    /**
     * Gets the checkbox of this view
     * @return
     */
    public CheckBox getCheckbox() {
        return checkbox;
    }
}
