package nl.avans.sagrada;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Player;

import java.util.ArrayList;

public class MySceneTest extends Scene {

    public MySceneTest() {
        super(new Pane());
        VBox vBox = new VBox();

        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = accountDAO.getAllAccounts();

        for (Account account : accounts) {
            Label label = new Label("Account: " + account.getUsername());
            vBox.getChildren().add(label);

            account.setPlayers();
            if (account.getPlayers() != null) {
                for (Player player : account.getPlayers()) {
                    player.setGame();
                    Label labelPlayer = new Label("Player: " + player.getId() + " - Game: " + player.getGame().getId());
                    vBox.getChildren().add(labelPlayer);
                }
            }
        }
        setRoot(vBox);
    }
}
