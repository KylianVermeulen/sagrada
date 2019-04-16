package nl.avans.sagrada.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.PopupView;

public class AccountController {
    private Account account;
    private AccountDAO aDAO;
    private PopupView pv;
    
    /**
     * An instance of AccountController, which initializes an AccountDAO and a PopupView.
     */
    public AccountController() {
        aDAO = new AccountDAO();
        pv = new PopupView(AlertType.NONE, "", null);
    }
    
    public void login() {

    }
    
    /**
     * Registers a user via username and password, and checks for certain requirements linked to the username and password.
     * @param username String
     * @param password String
     */
    
    public void register(String username, String password) {
        Account account = new Account();
        if (username.length() < 3) {
            System.out.println("TEST: username is te kort!");
            pv.createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in gebruikersnaam lengte." , "De ingevulde gebruikersnaam is te kort!");
            return;
        }
        if (password.length() < 3) {
            System.out.println("TEST: wachtwoord is te kort!");
            pv.createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in wachtwoord lengte." , "Het ingevulde wachtwoord is te kort!");
            return;
        }
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        Matcher match = pt.matcher(password);
        if (match.find()) {
            pv.createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in wachtwoord inhoud." , "Gebruik alleen letters en cijfers voor uw wachtwoord.");
            System.out.println("TEST: wachtwoord heeft leestekens!");
            return;
        }
        account.setUsername(username);
        account.setPassword(password);
        if (aDAO.accountExists(account)) {
            pv.createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Gebruiker bestaat al." , "Kies een andere gebruikersnaam.");
            System.out.println("TEST: account bestaat al!");
            return;
        }
        aDAO.addAccount(account);
        pv.createPopup(AlertType.INFORMATION, ButtonType.OK, "Bevestiging" , "Uw account is succesvol aangemaakt!", "Uw gebruikersnaam: " + account.getUsername());
    }
    
    /**
     * Switches the current pane to the "Login screen" pane.
     */
    public void gotoLogin() {
        System.out.println("TEST: go to login screen!");
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
