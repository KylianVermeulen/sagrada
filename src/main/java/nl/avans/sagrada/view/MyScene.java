package nl.avans.sagrada.view;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.PlayerDAO;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.view.popups.Alert;

import java.util.ArrayList;

public class MyScene extends Scene {
    private Pane rootPane;
    private Pane contentPane;
    private ArrayList<Pane> alerts;

    private AccountController accountController;
    private PlayerController playerController;
    private PrivateObjectiveCardView test;
    private PlayerDAO playerDao;
    private Player player;

    public MyScene() {
        super(new Pane());
        accountController = new AccountController(this);
        playerController = new PlayerController(this, player);

        playerDao = new PlayerDAO();
        player = playerDao.getPlayerById(1);

        test = new PrivateObjectiveCardView(playerController);

        rootPane = new StackPane();
        contentPane = new Pane();
        alerts = new ArrayList<Pane>();

        rootPane.getChildren().add(contentPane);
        setRoot(test);

    }


    public void setContentPane(Pane pane) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(pane);
    }


    public void addAlertPane(Pane pane) {
        StackPane.setAlignment(pane, Pos.TOP_RIGHT);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> removeAlertPaneAnimation(pane));
        delay.play();
        alerts.add(pane);
        renderAlertPanes();
    }

    /**
     * Render all alerts
     */
    public void renderAlertPanes() {
        for (Pane currentAlert : alerts) {
            rootPane.getChildren().remove(currentAlert);
        }
        for (int i = 0; i < alerts.size(); i++) {
            Pane alert = alerts.get(i);
            int marginDefault = 20;
            int heightPane = Alert.HEIGHT_ALERT;
            int margin = (marginDefault + (i * heightPane + i * (marginDefault / 2)));
            StackPane.setMargin(alert, new Insets(margin, 20, 20, 20));
            rootPane.getChildren().add(alert);
        }
    }


    public void removeAlertPaneAnimation(Pane pane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(350), pane);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> removeAlertPane(pane));
        fadeTransition.play();
    }


    public void removeAlertPane(Pane pane) {
        alerts.remove(pane);
        rootPane.getChildren().remove(pane);
        renderAlertPanes();
    }
}
