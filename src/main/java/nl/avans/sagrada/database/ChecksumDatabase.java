package nl.avans.sagrada.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.avans.sagrada.AnimationTimerExt;
import nl.avans.sagrada.controller.AccountController;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.enumerations.AccountStatus;

public class ChecksumDatabase {
    private AnimationTimerExt animationTimerExt;
    private DBConnection dbConnection;
    private AccountController accountController;
    private PlayerController playerController;
    private boolean refreshed = false;
    private String checksumPlayer;
    private String checksumChat;
    private String checksumPlayerFrameField;

    public ChecksumDatabase(AccountController accountController,
            PlayerController playerController) {
        dbConnection = new DBConnection();
        this.accountController = accountController;
        this.playerController = playerController;
        createTimer();
        animationTimerExt.start();
    }

    private void createTimer() {
        animationTimerExt = new AnimationTimerExt(3000) {
            @Override
            public void handle() {
                checksumPlayer();
                checksumChat();
                checksumPlayerFrameField();
                refreshed = false;
            }
        };
    }
    
    /**
     * Generates a checksum for the chatline table, and checks if this checksum is different from the already existing checksum.
     */
    private void checksumChat() {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("CHECKSUM TABLE chatline;", "query"));
            if (rs.next()) {
                String checksumChat = rs.getString("Checksum");
                if (!checksumChat.equals(this.checksumChat)) {
                    handleChat();
                }
                this.checksumChat = checksumChat;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a checksum for the player table, and checks if this checksum is different from the already existing checksum.
     */
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

    /**
     * Generates a checksum for the player frame field table, and checks if this checksum is different from the already existing checksum.
     */
    private void checksumPlayerFrameField() {
        try {
            ResultSet rs = dbConnection
                    .executeQuery(new Query("checksum table playerframefield;", "query"));
            if (rs.next()) {
                String checksumPlayerFrameField = rs.getString("Checksum");
                if (!checksumPlayerFrameField.equals(this.checksumPlayerFrameField)) {
                    handlePlayerFrameField();
                }
                this.checksumPlayerFrameField = checksumPlayerFrameField;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the player table checksum change
     */
    private void handlePlayer() {
        if (!refreshed) {
            if (accountController.getAccount() != null) {
                if (accountController.getAccount().getAccountStatus() == AccountStatus.LOBBY) {
                    accountController.viewLobby();
                    refreshed = true;
                } else if (accountController.getAccount().getAccountStatus()
                        == AccountStatus.GAME) {
                    if (playerController.getPlayer().getGame() != null) {
                        if (playerController.getPlayer().getGame().everyoneSelectedPatternCard()) {
                            playerController.viewGame();
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles the chat table checksum change
     */
    private void handleChat() {
        if (!refreshed) {
            if (accountController.getAccount() != null) {
                if (accountController.getAccount().getAccountStatus() == AccountStatus.GAME) {
                    playerController.viewGame();
                    refreshed = true;
                }
            }
        }
    }

    /**
     * Handles the player frame field table checksum change
     */
    private void handlePlayerFrameField() {
        if (!refreshed) {
            if (accountController.getAccount() != null) {
                if (accountController.getAccount().getAccountStatus() == AccountStatus.GAME) {
                    playerController.viewGame();
                    refreshed = true;
                }
            }
        }
    }
}
