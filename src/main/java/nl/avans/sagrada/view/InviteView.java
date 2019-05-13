package nl.avans.sagrada.view;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class InviteView extends HBox implements ViewInterface {
    private final int PANE_WIDTH = 200;
    private final int PANE_HEIGHT = 50;

    private Account account;
    private CheckBox checkbox;

    /**
     * Constructor
     */
    public InviteView(Account account) {
        this.account = account;
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setMaxWidth(Main.SCREEN_WIDTH - 300);
        String css = this.getClass().getResource("/css/inviteview.css").toExternalForm();
        getStylesheets().add(css);
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2))));
    }

    @Override
    public void render() {
        checkbox = new CheckBox(account.getUsername());
        checkbox.setMinWidth(Main.SCREEN_WIDTH - 26);
        getChildren().add(checkbox);
    }

    /**
     * Get the account that is in this view
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Gets the checkbox of this view
     */
    public CheckBox getCheckbox() {
        return checkbox;
    }
}
