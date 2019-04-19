package nl.avans.sagrada.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;

public class MyScene extends Scene {
    private Pane rootPane;
    private ToolCardView tcv;
	
    private AccountController accountController;
    private PlayerController playerController;

    public MyScene() {
        super(new Pane());
        accountController = new AccountController(this);
        playerController = new PlayerController(this);
        rootPane = new Pane();
        setRoot(rootPane);
        playerController.seeToolcard();
    }

    /**
     * Set the rootpane of the pane that we have as root from the scene
     * @param pane Pane
     */
    public void setRootPane(Pane pane) {
        rootPane.getChildren().clear();
        rootPane.getChildren().add(pane);
    }
}
