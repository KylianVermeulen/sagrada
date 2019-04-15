package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.Account;

public class RegisterView extends VBox {
    
    private AccountController ac;
    private int backButtonHeight = 35;
    private int backButtonWidth = 75;
    
    public RegisterView () {
        ac = new AccountController();
        TextField usernameInput = new TextField();
        TextField passwordInput = new TextField();
        Button button1 = new Button("Register");
        Label registerText = new Label("Register");
        Label usernameText = new Label("Username:");
        Label passwordText = new Label("Password:");
        Button backButton = new Button("<-");
        
        registerText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));   
        usernameText.setPadding(new Insets(10, 20, 10, 20));
        passwordText.setPadding(new Insets(10, 20, 10, 20));
        backButton.setMaxSize(backButtonWidth, backButtonHeight);
        backButton.setStyle("-fx-background-image: url('/images/ARROW_LEFT.png'); -fx-background-size: " + (backButtonWidth-10) + "px " + (backButtonHeight-5) + "px; -fx-background-repeat: no-repeat; -fx-background-position: center;");
        //backButton.setGraphic(ImageView.new(image(src/main/resources/images/ARROW_LEFT.png)));
        backButton.setOnAction(e -> ac.gotoLogin());
        
        button1.setOnAction(e -> ac.register(usernameInput.getText(), passwordInput.getText()));
        
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        FlowPane backButtonPane = new FlowPane();
        
        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        backButtonPane.setAlignment(Pos.TOP_LEFT);
        vbox.setAlignment(Pos.CENTER);
        
        vbox.setPadding(new Insets(321, 0, 0, 0)); //Dit moet later aangepast worden om precies het midden te verkrijgen (net als bij login scherm)
        
        hbox1.getChildren().addAll(usernameText, usernameInput);
        hbox2.getChildren().addAll(passwordText, passwordInput);
        vbox.getChildren().addAll(registerText, hbox1, hbox2, button1);
        backButtonPane.getChildren().add(backButton);
        getChildren().addAll(backButtonPane, vbox);
        
    }

}
