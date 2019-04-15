package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;

public class InviteOverviewView extends VBox {
    private static final int PANE_WIDTH = 300;
    private static final int PANE_HEIGHT = Main.SCREEN_HEIGHT / 2;
    private ArrayList<Invite> invites;
    private AccountController accountController;
    
    public InviteOverviewView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
    }
    
    public void setInvites(ArrayList<Invite> invites) {
        this.invites = invites;
    }
    
    public void render() {
        for (Invite invite: invites) {
            Player sendedPlayer = invite.getSendedPlayer();
            Account sendedAccount = sendedPlayer.getAccount();
            Pane pane = new HBox();
            Label label = new Label("Invite van: " + sendedAccount.getUsername());
            pane.getChildren().add(label);
            
            Button acceptButton = buildButtonToAcceptInvite(invite);
            Button denyButton = buildButtonToDenyInvite(invite);
            
            pane.getChildren().addAll(acceptButton, denyButton);
            getChildren().add(pane);
        }
    }
    
    private Button buildButtonToAcceptInvite(Invite invite) {
        Button button = new Button("+");
        button.setOnAction(e->accountController.acceptInvite(invite));
        return(button);
    }
    private Button buildButtonToDenyInvite(Invite invite) {
        Button button = new Button("x");
        button.setOnAction(e->accountController.denyInvite(invite));
        return(button);  
    }
}
