package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class LoginView extends BorderPane implements ViewInterface {
    private final static int BUTTONWIDTH = 120;
    private final static int BUTTONHEIGHT = 30;

    private AccountController accountController;

    public LoginView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("loginBackground");
    }

    /**
     * Creates a "LoginView" layout. A LoginView layout is a VBox wich has one main pane: a "content
     * (vbox) pane", which has a title, a username textfield, a password textfield, a login button
     * and a register text. The button actions are handled by the AccountController.
     */
    public void render() {
        getChildren().clear();
        VBox loginPane = new VBox();
        loginPane.setId("loginPane");
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setMaxHeight(350);
        loginPane.setMinHeight(350);
        loginPane.setMaxWidth(400);
        loginPane.setMinWidth(400);
        
        BorderPane userHBox = new BorderPane();
        Label userName = new Label("Username");
        userName.setPadding(new Insets(10, 20, 10, 20));
        userName.getStyleClass().add("loginLabel");
        TextField userTextField = new TextField();
        userTextField.setMaxHeight(25);
        userTextField.setMinHeight(25);
        userTextField.setMaxWidth(200);
        userTextField.setMinWidth(200);
        userTextField.getStyleClass().add("loginTextfield");
        userHBox.setTop(userName);
        userHBox.setAlignment(userName, Pos.CENTER);
        userHBox.setCenter(userTextField);

        BorderPane passwordHBox = new BorderPane();
        Label password = new Label("Password");
        PasswordField passwordTextField = new PasswordField();
        password.getStyleClass().add("loginLabel");
        password.setPadding(new Insets(10, 20, 10, 20));
        passwordTextField.setMaxHeight(25);
        passwordTextField.setMinHeight(25);
        passwordTextField.setMaxWidth(200);
        passwordTextField.setMinWidth(200);
        passwordTextField.getStyleClass().add("loginTextfield");
        passwordHBox.setTop(password);
        passwordHBox.setAlignment(password, Pos.CENTER);
        passwordHBox.setCenter(passwordTextField);
        passwordHBox.setPadding(new Insets(12, 12, 20, 12));

        Button loginButton = new Button("Login");
        loginButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        loginButton.setOnAction(e -> accountController.actionLogin(userTextField.getText(), passwordTextField.getText()));
        loginButton.setId("loginButton");
        
        Label registerLabel = new Label("If you don't have an account click here");
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        registerLabel.getStylesheets().add(css);
        registerLabel.setId("registerLabel");
        registerLabel.setPadding(new Insets(20, 0, 0, 0));
        registerLabel.setOnMouseClicked(e -> accountController.viewRegister());
        
        BorderPane topPane = new BorderPane();
        topPane.setMaxHeight(130);
        topPane.setMinHeight(130);
        topPane.setMaxWidth(1280);
        topPane.setMinWidth(1280);
        Pane logoPane = new Pane();
        logoPane.setMaxHeight(110);
        logoPane.setMinHeight(110);
        logoPane.setMaxWidth(313);
        logoPane.setMinWidth(313);
        logoPane.setPadding(new Insets(25));
        logoPane.setId("loginLogo");
        topPane.setCenter(logoPane);

        loginPane.getChildren().addAll(userHBox, passwordHBox, loginButton, registerLabel);
        
        //makeTitle();
        setTop(topPane);
        setCenter(loginPane);
        setAlignment(logoPane, Pos.CENTER);
    }

    private void makeTitle() {
        Text scenetitle = new Text("Sagrada");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        setTop(scenetitle);

    }
    
    private void showTop() {
       
        
    }

}
