package nl.avans.sagrada.controller;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.Notification;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.model.Account;

public class AccountController {
    private Account account;
    private AccountDAO aDAO;
    
    public AccountController() {
        aDAO = new AccountDAO();
    }

    private void createPopup (AlertType alertType, ButtonType buttonType, String popupTitle, String popupHeader, String popupText) {
        Alert a = new Alert(AlertType.NONE, "", buttonType);
        
        a.setTitle(popupTitle);
        a.setHeaderText(popupHeader);
        a.setAlertType(alertType); 
        a.setContentText(popupText); 
        a.setResizable(false);
        
        Optional<ButtonType> result = a.showAndWait();

        if(!result.isPresent() && alertType == AlertType.INFORMATION) {
            //go to login screen
        }
        else if(result.get() == ButtonType.CLOSE) {
            return;
        }
        else if(result.get() == ButtonType.OK) {
            // go to login screen
        }   
        
    }
    
    public void login() {

    }
    
    public void register(String username, String password) {
        Account account = new Account();
        if (username.length() < 3) {
            System.out.println("TEST: username is te kort!");
            createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in gebruikersnaam lengte." , "De ingevulde gebruikersnaam is te kort!");
        } else {
            if (password.length() < 3) {
                System.out.println("TEST: wachtwoord is te kort!");
                createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in wachtwoord lengte." , "Het ingevulde wachtwoord is te kort!");
            } else {
                Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
                Matcher match = pt.matcher(password);
                if (match.find()) {
                    createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Fout in wachtwoord inhoud." , "Gebruik alleen letters en cijfers voor uw wachtwoord.");
                    System.out.println("TEST: wachtwoord heeft leestekens!");
                } else {
                    account.setUsername(username);
                    account.setPassword(password);
                    if (aDAO.accountExists(account)) {
                        createPopup(AlertType.ERROR, ButtonType.CLOSE, "Foutmelding" ,"Gebruiker bestaat al." , "Kies een andere gebruikersnaam.");
                        System.out.println("TEST: account bestaat al!");
                    } else {
                        aDAO.addAccount(account);
                        createPopup(AlertType.INFORMATION, ButtonType.OK, "Bevestiging" , "Uw account is succesvol aangemaakt!", "Uw gebruikersnaam: " + account.getUsername());
                    }
                }
            }
        }
    }
    
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
