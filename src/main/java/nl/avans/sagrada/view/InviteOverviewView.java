package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;

public class InviteOverviewView extends VBox {
    private static final int PANE_WIDTH = 300;
    private static final int PANE_HEIGHT = Main.SCREEN_HEIGHT / 2;
    private ArrayList<Invite> invites;
    
    public InviteOverviewView() {
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
    }
    
    public void setInvites(ArrayList<Invite> invites) {
        this.invites = invites;
    }
    
    public void render() {
        for (Invite invite: invites) {
            System.out.println("Invite");
            Player sendedPlayer = invite.getSendedPlayer();
            Account sendedAccount = sendedPlayer.getAccount();
            Pane pane = new Pane();
            Label label = new Label(sendedAccount.getUsername());
            pane.getChildren().add(label);
            getChildren().add(pane);
        }
    }
}
