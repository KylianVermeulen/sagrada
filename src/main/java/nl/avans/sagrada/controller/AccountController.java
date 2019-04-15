package nl.avans.sagrada.controller;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.model.Account;

public class AccountController {
    private Account account;
    private AccountDAO aDAO;
    
    public AccountController() {
        aDAO = new AccountDAO();
    }

    public void login() {

    }
    public void register(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        if (aDAO.accountExists(account)) {
            return;
        } else {
            aDAO.addAccount(account);
        }
    }
    public void acceptInvite() {

    }

    public void denyInvite() {

    }

    public void gameOverview() {

    }

    public void joinGame() {

    }

    public void setupNewGame() {

    }

    public void inviteOverview() {

    }
}
