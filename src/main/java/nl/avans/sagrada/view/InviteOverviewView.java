package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class InviteOverviewView extends ScrollPane implements ViewInterface {
    private static final int PANE_WIDTH = Main.SCREEN_WIDTH / 5;
    private static final int PANE_HEIGHT = Main.SCREEN_HEIGHT / 2;
    private ArrayList<Invite> invites;
    private AccountController accountController;
    
    public InviteOverviewView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setPannable(true);
    }
    
    /**
     * Sets the invites that the view needs to show
     * @param invites
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
        for (Invite invite: invites) {
            Game game = invite.getGame();
            Pane pane = new HBox();
            Label label = new Label("Invite voor spel: " + game.getId());
            label.setPadding(new Insets(5, 4, 5, 4));
            pane.getChildren().add(label);
            
            Button acceptButton = buildButtonToAcceptInvite(invite);
            Button denyButton = buildButtonToDenyInvite(invite);
            
            pane.getChildren().addAll(acceptButton, denyButton);
            vbox.getChildren().add(pane);
        }
        setContent(vbox);
    }
    
    /**
     * Builds a button to accept the invite
     * @param invite
     * @return Button
     */
    private Button buildButtonToAcceptInvite(Invite invite) {
        Button button = new Button("+");
        button.setOnAction(e->accountController.acceptInvite(invite));
        return(button);
    }
    
    /**
     * Builds a button to deny the invite
     * @param invite
     * @return Button
     */
    private Button buildButtonToDenyInvite(Invite invite) {
        Button button = new Button("x");
        button.setOnAction(e->accountController.denyInvite(invite));
        return(button);  
    }
}
