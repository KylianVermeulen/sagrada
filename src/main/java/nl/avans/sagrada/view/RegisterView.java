package nl.avans.sagrada.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class RegisterView extends BorderPane implements ViewInterface {
    private AccountController accountController;
    private final int BACK_BUTTON_HEIGHT = 35;
    private final int BACK_BUTTON_WIDTH = 75;
    private final int BUTTON_WIDTH = 120;
    private final int BUTTON_HEIGHT = 30;
    private final int TEXTFIELD_WIDTH = 200;
    private final int TEXTFIELD_HEIGHT = 25;
    private final int LEFT_PANE_WIDTH = 313;
    private VBox registerPane;
    private Pane backPane;

    /**
     * RegisterView constructor. Sets the RegisterView pane size to the screensize and generates the
     * pane content.
     *
     * @param accountController AccountController
     */
    public RegisterView(AccountController accountController) {
        this.accountController = accountController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("registerBackground");
    }

    /**
     * Creates a "RegisterView" layout. A RegisterView is a VBox which has two other panes within
     * it: a BackButton pane (top left) and a "content (vbox) pane", which, in turn, has a title, a
     * username textfield, a password textfield and a register button. The button actions are
     * handled by the AccountController.
     */
    @Override
    public void render() {
        getChildren().clear();
        buildRegisterPane();
        buildBackPane();
        buildLeftPane();
    }

    /**
     * Method to build the registerPane
     */
    private void buildRegisterPane() {
        registerPane = new VBox();
        registerPane.setAlignment(Pos.CENTER);
        registerPane.setPadding(new Insets(10, 10, 100, 20));

        BorderPane userPane = new BorderPane();
        Label usernameText = new Label("Gebruikersnaam:");
        usernameText.setPadding(new Insets(10, 20, 10, 20));
        usernameText.getStyleClass().add("registerLabel");
        TextField usernameInput = new TextField();
        usernameInput.setMaxHeight(TEXTFIELD_HEIGHT);
        usernameInput.setMinHeight(TEXTFIELD_HEIGHT);
        usernameInput.setMaxWidth(TEXTFIELD_WIDTH);
        usernameInput.setMinWidth(TEXTFIELD_WIDTH);
        usernameInput.getStyleClass().add("registerTextField");
        userPane.setAlignment(usernameText, Pos.CENTER);
        userPane.setTop(usernameText);
        userPane.setCenter(usernameInput);

        BorderPane passwordPane = new BorderPane();
        Label passwordText = new Label("Wachtwoord:");
        passwordText.setPadding(new Insets(10, 20, 10, 20));
        passwordText.getStyleClass().add("registerLabel");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setMaxHeight(TEXTFIELD_HEIGHT);
        passwordInput.setMinHeight(TEXTFIELD_HEIGHT);
        passwordInput.setMaxWidth(TEXTFIELD_WIDTH);
        passwordInput.setMinWidth(TEXTFIELD_WIDTH);
        passwordInput.getStyleClass().add("registerTextField");
        passwordInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    accountController.actionRegister(usernameInput.getText(), passwordInput.getText());
                }
            }
        });
        passwordPane.setAlignment(passwordText, Pos.CENTER);
        passwordPane.setTop(passwordText);
        passwordPane.setCenter(passwordInput);
        passwordPane.setPadding(new Insets(12, 12, 20, 12));

        Button registerButton = new Button("Registreren");
        registerButton.setOnAction(
                e -> accountController.actionRegister(usernameInput.getText(), passwordInput.getText()));
        registerButton.setId("registerButton");
        registerButton.setPadding(new Insets(8, 12, 8, 12));
        registerButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        registerPane.getChildren().addAll(userPane, passwordPane, registerButton);
    }

    /**
     * Method to build the backPane
     */
    private void buildBackPane(){
        backPane = new Pane();
        Button backButton = new Button();
        backButton.setPrefSize(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        backButton.getStylesheets().add(css);
        backButton.setId("backButton");
        backButton.setOnAction(e -> accountController.viewLogin());
        backPane.getChildren().add(backButton);
    }

    /**
     * Method to build the leftPane
     */
    private void buildLeftPane(){
        BorderPane leftPane = new BorderPane();
        leftPane.setMaxWidth(LEFT_PANE_WIDTH);
        leftPane.setMinWidth(LEFT_PANE_WIDTH);
        leftPane.setId("registerLeftPane");
        leftPane.setLeft(registerPane);
        leftPane.setTop(backPane);
        setLeft(leftPane);
    }
}