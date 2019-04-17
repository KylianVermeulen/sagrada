package nl.avans.sagrada.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.PlayerDAO;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Player;

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

    /**
     * Set the rootpane of the pane that we have as root from the scene
     * @param pane
     */
    public void setRootPane(Pane pane) {
        rootPane.getChildren().clear();
        rootPane.getChildren().add(pane);
    }

    public void testPatterncardView() {
        Account account = new AccountDAO().getAccountByUsername("test1");
        Player player = new PlayerDAO().getPlayerById(account.getPlayers().get(0).getId());
        playerController.viewPatterncardOfPlayer(player);
    }
}
