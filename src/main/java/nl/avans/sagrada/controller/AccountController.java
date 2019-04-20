package nl.avans.sagrada.controller;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.GameDAO;
import nl.avans.sagrada.dao.InviteDAO;
import nl.avans.sagrada.dao.PlayerDAO;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.*;

import java.util.ArrayList;

public class AccountController {
    private Account account;
    private MyScene myScene;
    private AccountDAO accountDao;
    private InviteDAO inviteDao;
    private PlayerDAO playerDAO;
    private GameDAO gameDAO;
    
    public AccountController(MyScene myScene) {
        this.myScene = myScene;
        accountDao = new AccountDAO();
        inviteDao = new InviteDAO();
        playerDAO = new PlayerDAO();
        gameDAO = new GameDAO();
        account = accountDao.getAccountByUsername("test1");
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

    public void accountListOverview() {
        Pane pane = new Pane();
        ArrayList<Account> accounts = accountDao.getAllAccounts();

        AccountListOverview accountListOverview = new AccountListOverview(this);
        accountListOverview.setAccounts(accounts);
        accountListOverview.render();
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.getChildren().add(accountListOverview);

        myScene.setRootPane(pane);
    }

    public void joinGame(Game game) {
    }

    public void setupNewGame() {        
        Pane pane = new Pane();
        Game game = gameDAO.createNewGame();
        int playerId = playerDAO.getNextPlayerId();
        Player player = new Player();
        player.setId(playerId);
        player.setSeqnr(1);
        player.setPlayerStatus("challenger");
        player.setIsCurrentPlayer(true);
        player.setAccount(account);
        player.setGame(game);
        player.setPrivateObjectivecardColor(game.getRandomAvailablePrivateColor());
        playerDAO.addPlayer(player);
        
        game.setTurnPlayer(player);
        gameDAO.updateGame(game);
        
        ArrayList<Account> accounts = accountDao.getAllInviteableAccounts(account);
        GameSetupView gameSetupView = new GameSetupView(this, accounts, game);
        gameSetupView.render();
        pane.getChildren().add(gameSetupView);
        
        myScene.setRootPane(pane);
        
    }
    
    public void lobby() {
        Pane pane = new Pane();
        ArrayList<Invite> pendingInvites = account.getAllPendingInvites();
        ArrayList<Game> games = account.getGames();

        
        LobbyView lobbyView = new LobbyView(this);
        lobbyView.setInvites(pendingInvites);
        lobbyView.setGames(games);
        lobbyView.render();
        
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.getChildren().add(lobbyView);
        
        myScene.setRootPane(pane);
    }

    public void sendInvites(ArrayList<InviteView> inviteViews, Game game) {
        ArrayList<Account> invitedAccounts = new ArrayList<>();
        for (InviteView inviteView: inviteViews) {
            if (inviteView.getCheckbox().isSelected()) {
                invitedAccounts.add(inviteView.getAccount());
            }
        }
        if (invitedAccounts.size() == 0) {
            System.out.println("Te weinig accounts ge-invite");
            return;
        }
        if (invitedAccounts.size() > 3) {
            System.out.println("Te veel accounts ge-invite");
            return;
        }
        
        for (Account invitedAccount: invitedAccounts) {
            Invite invite = new Invite();
            invite.setGame(game);
            invite.setInvitedAccount(invitedAccount);
            inviteDao.addInvite(invite);
        }
        System.out.println("Invites zijn verstuurd!");
        game.setOptionPatternCardsForPlayers();
        lobby();
    }
}
