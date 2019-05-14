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
    private final int TEXTFIELD_WIDTH = 200;
    private final int TEXTFIELD_HEIGHT = 25;
    private final int LEFTPANE_WIDTH = 313;
    private final int LOGOPANE_WIDHT = 313;
    private final int LOGOPANE_HEIGHT = 110;

    private AccountController accountController;

    public LoginView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("loginBackground");
        setPrefSize(1280, 800);
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
        loginPane.setPadding(new Insets(10, 10, 100, 20));
        
        BorderPane userHBox = new BorderPane();
        Label userName = new Label("Username");
        userName.setPadding(new Insets(10, 20, 10, 20));
        userName.getStyleClass().add("loginLabel");
        TextField userTextField = new TextField();
        userTextField.setMaxHeight(TEXTFIELD_HEIGHT);
        userTextField.setMinHeight(TEXTFIELD_HEIGHT);
        userTextField.setMaxWidth(TEXTFIELD_WIDTH);
        userTextField.setMinWidth(TEXTFIELD_WIDTH);
        userTextField.getStyleClass().add("loginTextfield");
        userHBox.setTop(userName);
        userHBox.setAlignment(userName, Pos.CENTER);
        userHBox.setCenter(userTextField);

        BorderPane passwordHBox = new BorderPane();
        Label password = new Label("Password");
        PasswordField passwordTextField = new PasswordField();
        password.getStyleClass().add("loginLabel");
        password.setPadding(new Insets(10, 20, 10, 20));
        passwordTextField.setMaxHeight(TEXTFIELD_HEIGHT);
        passwordTextField.setMinHeight(TEXTFIELD_HEIGHT);
        passwordTextField.setMaxWidth(TEXTFIELD_WIDTH);
        passwordTextField.setMinWidth(TEXTFIELD_WIDTH);
        passwordTextField.getStyleClass().add("loginTextfield");
        passwordHBox.setTop(password);
        passwordHBox.setAlignment(password, Pos.CENTER);
        passwordHBox.setCenter(passwordTextField);
        passwordHBox.setPadding(new Insets(12, 12, 20, 12));

        Button loginButton = new Button("Login");
        loginButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        loginButton.setOnAction(e -> accountController.actionLogin(userTextField.getText(), passwordTextField.getText()));
        loginButton.setId("loginButton");
        loginButton.setPadding(new Insets(8, 12, 8, 12));
        
        Label registerLabel = new Label("If you don't have an account click here");
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        registerLabel.getStylesheets().add(css);
        registerLabel.setId("registerLabel");
        registerLabel.setPadding(new Insets(20, 0, 0, 0));
        registerLabel.setOnMouseClicked(e -> accountController.viewRegister());

        Pane logoPane = new Pane();
        logoPane.setMaxHeight(LOGOPANE_HEIGHT);
        logoPane.setMinHeight(LOGOPANE_HEIGHT);
        logoPane.setMaxWidth(LOGOPANE_WIDHT);
        logoPane.setMinWidth(LOGOPANE_WIDHT);
        logoPane.setPadding(new Insets(25));
        logoPane.setId("loginLogo");

        loginPane.getChildren().addAll(userHBox, passwordHBox, loginButton, registerLabel);

        BorderPane pane = new BorderPane();
        pane.setMaxWidth(LEFTPANE_WIDTH);
        pane.setMinWidth(LEFTPANE_WIDTH);
        pane.setId("leftPane");
        pane.setLeft(loginPane);
        pane.setTop(logoPane);
        setLeft(pane);
    }
}
