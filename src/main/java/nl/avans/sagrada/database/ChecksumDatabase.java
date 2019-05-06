package nl.avans.sagrada.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.avans.sagrada.AnimationTimerExt;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.model.enumerations.AccountStatus;

public class ChecksumDatabase {
    private AnimationTimerExt animationTimerExt;
    private DBConnection dbConnection;
    private AccountController accountController;
    private String checksumPlayer;

    public ChecksumDatabase(AccountController accountController) {
        dbConnection = new DBConnection();
        this.accountController = accountController;
        createTimer();
        animationTimerExt.start();
    }

    private void createTimer() {
        animationTimerExt = new AnimationTimerExt(6000) {
            @Override
            public void handle() {
                checksumPlayer();
            }
        };
    }

    private void checksumPlayer() {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("checksum table player;", "query"));
            if (rs.next()) {
                String checksumPlayer = rs.getString("Checksum");
                if (!checksumPlayer.equals(this.checksumPlayer)) {
                    handlePlayer();
                }
                this.checksumPlayer = checksumPlayer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handlePlayer() {
        if (accountController.getAccount() != null) {
            if (accountController.getAccount().accountStatus == AccountStatus.LOBBY) {
                accountController.viewLobby();
            }
        }
    }
}
