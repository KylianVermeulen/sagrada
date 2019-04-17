package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class RegisterView extends VBox implements ViewInterface {
    private AccountController accountController;
    private int backButtonHeight = 35;
    private int backButtonWidth = 75;
    
    /**
     * RegisterView constructor. Sets the RegisterView pane size to the screensize and generates the pane content.
     * @param accountController AccountController
     */
    
    public RegisterView (AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);        
    }
    
    /**
     * Creates a "RegisterView" layout. A RegisterView is a VBox which has two other panes within it: a BackButton pane (top left) and 
     * a "content (vbox) pane", which, in turn, has a title, a username textfield, a password textfield and 
     * a register button. The button actions are handled by the AccountController.
     */
    @Override
    public void render() {
        getChildren().clear();
        TextField usernameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        Button registerButton = new Button("Register");
        Label registerText = new Label("Register");
        Label usernameText = new Label("Username:");
        Label passwordText = new Label("Password:");
        Button backButton = new Button();
        
        registerText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));   
        usernameText.setPadding(new Insets(10, 20, 10, 20));
        passwordText.setPadding(new Insets(10, 20, 10, 20));
        backButton.setPrefSize(backButtonWidth, backButtonHeight);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        backButton.getStylesheets().add(css);
        backButton.setId("backButton");
        backButton.setOnAction(e -> accountController.gotoLogin());
        
        registerButton.setOnAction(e -> accountController.register(usernameInput.getText(), passwordInput.getText()));
        
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        FlowPane backButtonPane = new FlowPane();
        
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        backButtonPane.setAlignment(Pos.TOP_LEFT);
        vbox.setAlignment(Pos.CENTER);
        
        hbox1.getChildren().addAll(usernameText, usernameInput);
        hbox2.getChildren().addAll(passwordText, passwordInput);
        vbox.getChildren().addAll(registerText, hbox1, hbox2, registerButton);
        backButtonPane.getChildren().add(backButton);
        
        vbox.setPadding(new Insets(321, 0, 0, 0)); //Dit moet later aangepast worden om precies het midden te verkrijgen (net als bij login scherm)
        
        getChildren().addAll(backButtonPane, vbox);
    }

}
