package nl.avans.sagrada.runnables;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import nl.avans.sagrada.view.popups.Alert;

public class AlertHandler implements Runnable {
    private ArrayList<Pane> alerts;
    private Pane rootPane;
    
    public AlertHandler(Pane rootPane) {
        this.rootPane = rootPane;
    }
    

    @Override
    public void run() {
        
    }
    
    public void addAlertPane(Pane pane) {
        StackPane.setAlignment(pane, Pos.TOP_RIGHT);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> removeAlertPaneAnimation(pane));
        delay.play();
        alerts.add(pane);
        renderAlertPanes();
    }
    
    private void renderAlertPanes() {
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
    
    private void removeAlertPaneAnimation(Pane pane) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(350), pane);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> removeAlertPane(pane));
        fadeTransition.play();
    }
    
    private void removeAlertPane(Pane pane) {
        alerts.remove(pane);
        rootPane.getChildren().remove(pane);
        renderAlertPanes();
    }

}
