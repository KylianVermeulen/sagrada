package nl.avans.sagrada.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;

public class MyScene extends Scene {
    private Pane rootPane;
    
    private AccountController accountController;
    private PlayerController playerController;
    
    public MyScene() {
        super(new Pane());
        accountController = new AccountController(this);
        playerController = new PlayerController(this);
        
        rootPane = new Pane();
        setRoot(rootPane);
    }

}