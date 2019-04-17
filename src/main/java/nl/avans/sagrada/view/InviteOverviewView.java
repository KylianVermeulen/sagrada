package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Invite;

public class InviteOverviewView extends VBox implements ViewInterface {
    private static final int PANE_WIDTH = Main.SCREEN_WIDTH / 5;
    private static final int PANE_HEIGHT = Main.SCREEN_HEIGHT / 2;
    private ArrayList<Invite> invites;
    private AccountController accountController;
    
    public InviteOverviewView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
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
    public void render() {
        getChildren().clear();
        for (Invite invite: invites) {
            Account sendedAccount = invite.getSendedPlayer().getAccount();
            Pane pane = new HBox();
            Label label = new Label("Invite van: " + sendedAccount.getUsername());
            label.setPadding(new Insets(5, 4, 5, 4));
            pane.getChildren().add(label);
            
            Button acceptButton = buildButtonToAcceptInvite(invite);
            Button denyButton = buildButtonToDenyInvite(invite);
            
            pane.getChildren().addAll(acceptButton, denyButton);
            getChildren().add(pane);
        }
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
