package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class LobbyView extends BorderPane implements ViewInterface {
    private AccountController accountController;
    private ArrayList<Game> games;
    private ArrayList<Invite> invites;
    
    private InviteOverviewView inviteOverview;
    private GameOverviewView gameOverview;
    private Button newGameButton;
    
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 40;
    
    public LobbyView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    }
    public void setInvites(ArrayList<Invite> invites) {
        this.invites = invites;
    }
    
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    @Override
    public void render() {
        buildInviteOverview();
        buildGamesOverview();
        buildNewGameBtn();
        
        VBox vbox = new VBox();
        Label inviteLabel = new Label("Invites van spelers");
        Label gameOverviewLabel = new Label("Je openstaande spellen");
        vbox.getChildren().addAll(inviteLabel, inviteOverview, gameOverviewLabel, gameOverview);
        setLeft(vbox);
        setCenter(newGameButton);
    }
    
    private void buildNewGameBtn() {
        newGameButton = new Button("Maak nieuw spel");
        newGameButton.setOnAction(e->accountController.setupNewGame());
        newGameButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    }
    
    private void buildInviteOverview() {
        inviteOverview = new InviteOverviewView(accountController);
        inviteOverview.setInvites(invites);
        inviteOverview.render();
    }
    
    private void buildGamesOverview() {
        gameOverview = new GameOverviewView(accountController);
        gameOverview.setGames(games);
        gameOverview.render();
    }
}
