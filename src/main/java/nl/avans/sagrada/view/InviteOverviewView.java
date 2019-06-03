package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class InviteOverviewView extends ScrollPane implements ViewInterface {
    private static final int PANE_WIDTH = Main.SCREEN_WIDTH / 5;
    private static final int PANE_HEIGHT = Main.SCREEN_HEIGHT / 2;
    private final int BUTTON_WIDTH = 50;
    private ArrayList<Invite> invites;
    private AccountController accountController;

    /**
     * Constructor
     */
    public InviteOverviewView(AccountController accountController) {
        this.accountController = accountController;
        String css = this.getClass().getResource("/css/lobbyview.css").toExternalForm();
        getStylesheets().add(css);
        setMaxHeight(PANE_HEIGHT);
        setMinHeight(PANE_HEIGHT);
        setMaxWidth(PANE_WIDTH);
        setMinWidth(PANE_WIDTH);
        setPannable(true);
    }

    /**
     * Sets the invites that the view needs to show
     */
    public void setInvites(ArrayList<Invite> invites) {
        this.invites = invites;
    }

    /**
     * Renders the view with all the information
     */
    @Override
    public void render() {
        getChildren().clear();
        VBox vbox = new VBox();
        vbox.setMinHeight(PANE_HEIGHT - 2);
        vbox.setMinWidth(PANE_WIDTH - 2);
        for (Invite invite : invites) {
            Game game = invite.getGame();
            Pane pane = new HBox();

            pane.setBackground(new Background(new BackgroundFill(Color.ROSYBROWN, null, null)));
            pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY, new BorderWidths(2))));

            Label label = new Label("Invite voor spel: " + game.getId());
            label.setPadding(new Insets(5, 20, 5, 4));
            label.setMinWidth(150);
            pane.getChildren().add(label);

            Button acceptButton = buildButtonToAcceptInvite(invite);
            acceptButton.setId("accept-button");
            Button denyButton = buildButtonToDenyInvite(invite);
            denyButton.setId("deny-button");

            pane.getChildren().addAll(acceptButton, denyButton);
            vbox.getChildren().add(pane);
        }
        setContent(vbox);
    }

    /**
     * Builds a button to accept the invite
     *
     * @return Button
     */
    private Button buildButtonToAcceptInvite(Invite invite) {
        Button button = new Button("+");
        button.setOnAction(e -> accountController.actionAcceptInvite(invite));
        button.setMinWidth(BUTTON_WIDTH);
        return (button);
    }

    /**
     * Builds a button to deny the invite
     *
     * @return Button
     */
    private Button buildButtonToDenyInvite(Invite invite) {
        Button button = new Button("x");
        button.setOnAction(e -> accountController.actionDenyInvite(invite));
        button.setMinWidth(BUTTON_WIDTH);
        return (button);
    }
}
