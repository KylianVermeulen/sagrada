package nl.avans.sagrada.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (username.length() < 3) {
            System.out.println("TEST: username is te kort!");
            return;
            //message: please enter a valid username...
        } else {
            if (password.length() < 3) {
                System.out.println("TEST: wachtwoord is te kort!");
                return;
                //message: please enter a valid password...
            } else {
                Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
                Matcher match = pt.matcher(password);
                if (match.find()) {
                    System.out.println("TEST: wachtwoord heeft leestekens!");
                    return;
                    //message: no special characters are allowed in a password!
                } else {
                    account.setUsername(username);
                    account.setPassword(password);
                    if (aDAO.accountExists(account)) {
                        System.out.println("TEST: account bestaat al!");
                        return;
                        //message: this account already exists!
                    } else {
                        aDAO.addAccount(account);
                        System.out.println("Account gemaakt! Username: " + account.getUsername() + ", Password: " + account.getPassword() + ".");
                        //confirm account creation? go to login screen?
                    }
                }
            }
        }
    }
    
    public void gotoLogin() {
        //go to login pane
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
