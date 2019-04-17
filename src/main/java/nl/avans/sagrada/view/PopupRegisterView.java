package nl.avans.sagrada.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import nl.avans.sagrada.controller.AccountController;

public class PopupRegisterView extends PopupView {
    private AccountController accountController;
    
    /**
     * An instance of PopupView is an extension of Alert (javafx.scene.control.Alert).
     * This instance requests an AlertType, a text (Dialogue) and an array of ButtonType (in order to add multiple buttons to the popup) as parameters.
     * @param alertType AlertType
     * @param contentText String
     * @param buttonType ButtonType[]
     */
    public PopupRegisterView(AccountController accountController, AlertType alertType, String contentText, ButtonType[] buttonType) {
        super(accountController, alertType, contentText, buttonType);
    }
    
    @Override
    public void createPopup (AlertType alertType, ButtonType buttonType, String popupTitle, String popupHeader, String popupText) {
        super.createPopup(alertType, buttonType, popupTitle, popupHeader, popupText);
        ButtonType result = super.getResult();
        if(!result.isPresent() && alertType == AlertType.INFORMATION) {
            accountController.gotoLogin();
        }
        else if(result.get() == ButtonType.CLOSE) {
            return;
        }
        else if(result.get() == ButtonType.OK) {
           accountController.gotoLogin();
        }   
    }

}
