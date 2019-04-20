package nl.avans.sagrada.view;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;

public class MyScene extends Scene {
	private Pane rootPane;
	private Pane contentPane;

	private AccountController accountController;
	private PlayerController playerController;
//	private ArrayList<Pane> loginAlerts;

	public MyScene() {
		super(new Pane());
		accountController = new AccountController(this);
		playerController = new PlayerController(this);

		rootPane = new StackPane();
		contentPane = new Pane();
//		loginAlerts = new ArrayList<Pane>();
		accountController.viewLogin();

		rootPane.getChildren().add(contentPane);
		setRoot(rootPane);
	}

	/**
	 * Set the rootpane of the pane that we have as root from the scene
	 * 
	 * @param pane
	 *            Pane
	 */
	public void setRootPane(Pane pane) {
		rootPane.getChildren().clear();
		rootPane.getChildren().add(pane);
	}

	public void setContentPane(Pane pane) {
		contentPane.getChildren().clear();
		contentPane.getChildren().add(pane);
	}
	
//	public void addLoginAlertPane(Pane pane) {
//		StackPane.setAlignment(pane, Pos.TOP_RIGHT);
//		PauseTransition delay = new PauseTransition(Duration.seconds(3));
//		delay.setOnFinished(e -> removeLoginAlertPaneAnimation(pane));
//		delay.play();
//		loginAlerts.add(pane);
//		renderLoginAlertPanes();
//		
//	}
//
//	private void renderLoginAlertPanes() {
//		for(Pane currentAlert : loginAlerts) {
//			rootPane.getChildren().remove(currentAlert);
//		}
//		for (int i = 0; i < loginAlerts.size(); i++) {
//			Pane alert = loginAlerts.get(i);
//			int marginDefault = 20;
//			int heightPane = 90;
//			int margin = (marginDefault + (i * heightPane + i * (marginDefault / 2)));
//			StackPane.setMargin(alert, new Insets(margin, 20, 20, 20));
//			rootPane.getChildren().add(alert);
//		}
//		
//	}
//
//	private void removeLoginAlertPaneAnimation(Pane pane) {
//		FadeTransition fadeTransition = new FadeTransition(Duration.millis(350), pane);
//		fadeTransition.setToValue(0.0);
//		fadeTransition.setOnFinished(e -> removeLoginAlertPane(pane));
//		fadeTransition.play();
//	}
//	
//	private void removeLoginAlertPane(Pane pane) {
//		loginAlerts.remove(pane);
//		rootPane.getChildren().remove(pane);
//		renderLoginAlertPanes();
//	}

}
