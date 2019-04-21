package nl.avans.sagrada.view;
	
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;
	
	public class MyScene extends Scene {
	    private Pane rootPane;
	
	    private AccountController accountController;
	    private PlayerController playerController;
	
	    public MyScene() {

	        super(new Pane());
	    	Player player = new Player();
	    	Game game = new Game(2);
	    	Account account = new Account("Ian", "Ian");
	    	player.setAccount(account);
	    	player.setGame(game);
	    	player.setId(0);
	    	
	        accountController = new AccountController(this);
	        playerController = new PlayerController(this);
	        rootPane = new ChatLineView(playerController);
	        playerController.setPlayer(player);
	        setRoot(rootPane);
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