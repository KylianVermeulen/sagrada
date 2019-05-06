package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.database.ChecksumDatabase;
import nl.avans.sagrada.view.popups.Alert;

public class MyScene extends Scene {
    private Pane rootPane;
    private Pane contentPane;
    private ArrayList<Pane> alerts;
    private AccountController accountController;
    private PlayerController playerController;
    private ChecksumDatabase checksumDatabase;

    public MyScene() {
        super(new Pane());
        
        Player player = new Player();
        player.setId(1);
        Game game = new Game(1);
        Account account = new Account();
        account.setUsername("test1");
        player.setAccount(account);
        player.setGame(game);
        
        accountController = new AccountController(this);
        playerController = new PlayerController(this);
        checksumDatabase = new ChecksumDatabase(accountController);

        rootPane = new StackPane();
        contentPane = new Pane();
        alerts = new ArrayList<Pane>();
        
        rootPane.getChildren().add(contentPane);
        setRoot(rootPane);
        
        //accountController.viewLogin();
        playerController.setPlayer(player);
        playerController.viewChat();
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * Set the contentPane of the rootPane that we have as content for the scene
     *
     * @param pane Pane
     */
    public void setContentPane(Pane pane) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(pane);
    }

    /**
     * Add alert pane to alerts list and call method render all alerts
     *
     * @param pane Pane
     */
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

    /**
     * Remove alert pane animation
     *
     * @param pane Pane
     */
    public void removeAlertPaneAnimation(Pane pane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(350), pane);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> removeAlertPane(pane));
        fadeTransition.play();
    }

    /**
     * Remove alert from alerts list and call method render all alerts
     *
     * @param pane Pane
     */
    public void removeAlertPane(Pane pane) {
        alerts.remove(pane);
        rootPane.getChildren().remove(pane);
        renderAlertPanes();
    }
}
