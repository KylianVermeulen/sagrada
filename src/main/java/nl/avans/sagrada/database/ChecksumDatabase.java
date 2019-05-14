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
    private String checksumPlayer;
    private String checksumChat;
    private String checksumScore;

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
                checksumScore();
            }
        };
    }

    /**
     * Generates a checksum for the chatline table, and checks if this checksum is different from
     * the already existing checksum.
     */
    private void checksumChat() {
        try {
            ResultSet rs =
                    dbConnection.executeQuery(new Query("CHECKSUM TABLE chatline;", "query"));
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
     * Generates a checksum for the player table, and checks if this checksum is different from the
     * already existing checksum.
     */
    private void checksumScore() {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("CHECKSUM TABLE player;", "query"));
            if (rs.next()) {
                String checksumScore = rs.getString("Checksum");
                if (!checksumScore.equals(this.checksumScore)) {
                    handleScoreUpdate();
                }
                this.checksumScore = checksumScore;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            if (accountController.getAccount().getAccountStatus() == AccountStatus.LOBBY) {
                accountController.viewLobby();
            } else if (accountController.getAccount().getAccountStatus() == AccountStatus.GAME) {
                if (playerController.getPlayer().getGame() != null) {
                    if (playerController.getPlayer().getGame().everyoneSelectedPatternCard()) {
                        playerController.viewGame();
                    }
                }
            }
        }
    }

    /**
     * Reloads the gameview when an account is in a game (meant to handle chat).
     */
    private void handleChat() {
        if (accountController.getAccount() != null) {
            if (accountController.getAccount().getAccountStatus() == AccountStatus.GAME) {
                playerController.viewGame();
            }
        }
    }

    /**
     * Reloads the gameview when an account is in a game (meant to handle score).
     */
    private void handleScoreUpdate() {
        if (accountController.getAccount() != null) {
            if (accountController.getAccount().getAccountStatus() == AccountStatus.GAME) {
                playerController.viewGame();
            }
        }
    }
}
