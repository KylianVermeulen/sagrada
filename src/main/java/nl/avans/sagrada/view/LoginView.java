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
import nl.avans.sagrada.controller.AccountController;

public class LoginView extends VBox {
	private static int buttonWitdth = 120;
	private static int buttonHeight = 30;

	private AccountController accountcontroller;

	public LoginView() {
		accountcontroller = new AccountController();
		setAlignment(Pos.CENTER);

		makeTitle();
		makeContent();
	}

	private void makeContent() {
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
		loginButton.setPrefSize(buttonWitdth, buttonHeight);
		loginButton.setOnAction(e -> accountcontroller.login(userTextField.getText(), passwordTextField.getText()));

		Label registerLabel = new Label("If you don't have an account click here: ");
		registerLabel.setPadding(new Insets(10, 20, 10, 20));
		registerLabel.setStyle("-fx-underline: true;");
		registerLabel.setOnMouseClicked(e -> registerClick());

		getChildren().addAll(userHBox, passwordHBox, loginButton, registerLabel);
	}

	private void registerClick() {
		System.out.println("Go to register screen");
	}

	private void makeTitle() {
		Text scenetitle = new Text("Login");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		getChildren().add(scenetitle);

	}

}
