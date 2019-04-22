package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.AccountController;

public class LoginView extends VBox {
	private final static int BUTTONWIDTH = 120;
	private final static int BUTTONHEIGHT = 30;

	private AccountController accountController;

	public LoginView(AccountController accountController) {
		this.accountController = accountController;
		setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setAlignment(Pos.CENTER);
	}

	/**
	 * Creates a "LoginView" layout. A LoginView layout is a VBox wich has one main
	 * pane: a "content (vbox) pane", which has a title, a username textfield, a
	 * password textfield, a login button and a register text. The button actions
	 * are handled by the AccountController.
	 */
	public void render() {
		getChildren().clear();
		HBox userHBox = new HBox();

		Label userName = new Label("Username: ");
		userName.setPadding(new Insets(10, 20, 10, 20));
		TextField userTextField = new TextField();
		userHBox.setAlignment(Pos.CENTER);

		userHBox.getChildren().addAll(userName, userTextField);

		HBox passwordHBox = new HBox();

		Label password = new Label("Password: ");
		TextField passwordTextField = new TextField();
		password.setPadding(new Insets(10, 20, 10, 20));
		passwordHBox.setAlignment(Pos.CENTER);

		passwordHBox.getChildren().addAll(password, passwordTextField);

		Button loginButton = new Button("Login");
		loginButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		loginButton.setOnAction(e -> accountController.login(userTextField.toString(), passwordTextField.toString()));

		Label registerLabel = new Label("If you don't have an account click here: ");
		registerLabel.setPadding(new Insets(10, 20, 10, 20));
		registerLabel.setStyle("-fx-underline: true;");
		registerLabel.setOnMouseClicked(e -> accountController.viewRegister());

		makeTitle();
		getChildren().addAll(userHBox, passwordHBox, loginButton, registerLabel);
	}

	private void makeTitle() {
		Text scenetitle = new Text("Login");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		getChildren().add(scenetitle);

	}

}
