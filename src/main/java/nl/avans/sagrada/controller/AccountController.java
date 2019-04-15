package nl.avans.sagrada.controller;

import java.util.ArrayList;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.InviteDAO;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.view.InviteOverviewView;
import nl.avans.sagrada.view.MyScene;

public class AccountController {
    private Account account;
    private MyScene myScene;
    private AccountDAO accountDao;
    private InviteDAO inviteDao;
    
    public AccountController(MyScene myScene) {
        this.myScene = myScene;
        accountDao = new AccountDAO();
        inviteDao = new InviteDAO();
    }

    public void login() {

    }
    public void register() {

    }
    public void acceptInvite(Invite invite) {
        invite.acceptInvite();
    }

    public void denyInvite(Invite invite) {
        invite.denyInvite();
    }

    public void gameOverview() {

    }

    public void joinGame() {

    }

    public void setupNewGame() {

    }

    public void inviteOverview() {
        Pane pane = new Pane();
        account = accountDao.getAccountByUsername("test1");
        ArrayList<Invite> pendingInvites = account.getInvites();
        
        InviteOverviewView inviteOverview = new InviteOverviewView(this);
        inviteOverview.setInvites(pendingInvites);
        inviteOverview.render();
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.getChildren().add(inviteOverview);
        myScene.setRootPane(pane);
    }
}
