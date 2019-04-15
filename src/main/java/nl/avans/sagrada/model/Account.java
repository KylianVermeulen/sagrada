package nl.avans.sagrada.model;

import java.util.ArrayList;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.InviteDAO;

public class Account {
    private String username;
    private String password;
    private ArrayList<Player> players;
    private ArrayList<Invite> pendingInvites;
    
    public Account() {}
    
    public Account(String username) {
        AccountDAO accountDao = new AccountDAO();
        InviteDAO inviteDao = new InviteDAO();
        Account account = accountDao.getAccountByUsername(username);
        this.username = account.getUsername();
        password = account.getPassword();
        
        pendingInvites = inviteDao.getAllPendingInvitesOfAccount(this);
    }
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
