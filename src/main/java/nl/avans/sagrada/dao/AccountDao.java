package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;

public class AccountDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public AccountDao() {
        dbConnection = new DBConnection();
    }

    /**
     * This method will return a new account object with information from the database using the
     * parameter username as unique identifier.
     *
     * @param username The username for account.
     * @return A account object with username and password.
     */
    public Account getAccountByUsername(String username) {
        Account account = null;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM account WHERE username=?", "query",
                            new QueryParameter(QueryParameter.STRING, username)
                    )
            );
            if (rs.next()) {
                String accountUsername = rs.getString("username");
                String accountPassword = rs.getString("password");
                account = new Account(accountUsername, accountPassword);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * This method will return a list of all accounts saved in the database.
     *
     * @return A list of all accounts.
     */
    public ArrayList<Account> getAllAccounts() {
        DBConnection dbConnection = new DBConnection();
        ArrayList<Account> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM account", "query"));
            while (rs.next()) {
                String accountUsername = rs.getString("username");
                String accountPassword = rs.getString("password");
                Account account = new Account(accountUsername, accountPassword);
                list.add(account);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * This method will return a list of all accounts a certain account, given as parameter, can
     * invite.
     *
     * @param account The account sending the invite.
     * @return A list of all invite able accounts.
     */
    public ArrayList<Account> getAllInviteableAccounts(Account account) {
        ArrayList<Account> accounts = getAllAccounts();
        ArrayList<Account> inviteAbleAccounts = new ArrayList<>();

        for (Account inviteAccount : accounts) {
            String usernameAccount = account.getUsername();
            String usernameInviteAbleAccount = inviteAccount.getUsername();
            if (!usernameAccount.equals(usernameInviteAbleAccount)) {
                inviteAbleAccounts.add(inviteAccount);
            }
        }
        return inviteAbleAccounts;
    }

    /**
     * This method will add a new account to the database.
     *
     * @param account The account object to save in the database.
     */
    public void addAccount(Account account) {
        if (!accountExists(account)) {
            try {
                dbConnection.executeQuery(
                        new Query("INSERT INTO account (username, password) VALUES (?, ?)",
                                "update"),
                        new QueryParameter(QueryParameter.STRING, account.getUsername()),
                        new QueryParameter(QueryParameter.STRING, account.getPassword())
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't create a new account, already exists");
        }
    }

    /**
     * This method will check if a account exists in the database.
     *
     * @param account The account to check for.
     * @return True when account exists.
     */
    public boolean accountExists(Account account) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT count(*) as count FROM account WHERE username=?", "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the highest score from the database by account.
     *
     * @param account The account.
     * @return Highest score.
     */
    public int getHighestScore(Account account) {
        int score = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT MAX(score) AS hoogste_score FROM player WHERE username=?",
                            "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()) {
                score = rs.getInt("hoogste_score");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public String getMoseUsedColor(Account account) {
        String color = "";
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT diecolor AS color, COUNT(diecolor) AS meest_gebruikte_kleur FROM playerframefield JOIN player ON player.idplayer = playerframefield.player_idplayer WHERE player.username=? GROUP BY diecolor ORDER BY meest_gebruikte_kleur DESC LIMIT 1",
                            "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()) {
                color = rs.getString("color");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return color;
    }

    /**
     * Gets the amount of wins from the account
     * @param account Account
     * @return
     */
    public int getWins(Account account){
        ArrayList<Game> games = new GameDao().getAllGames();
        int wins = 0;
        for(int i = 0; i < games.size(); i++){
            try {
                ResultSet rs = dbConnection.executeQuery(
                        new Query(
                                "SELECT username, MAX(score) AS winscore FROM player WHERE game_idgame=? AND playstatus_playstatus = 'uitgespeeld' GROUP BY username, game_idgame ORDER BY winscore DESC LIMIT 1", "query"),
                        new QueryParameter(QueryParameter.INT, games.get(i).getId())
                );
                if (rs.next()){
                    if(rs.getString("username").equals(account.getUsername())){
                        wins++;
                    }
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wins;
    }

    /**
     * Gets the amount of loses from the account
     * @param account Account
     * @return
     */
    public int getLoses(Account account){
        ArrayList<Game> games = new GameDao().getAllGames();
        int loses = 0;
        for(int i = 0; i < games.size(); i++){
            try {
                ResultSet rs = dbConnection.executeQuery(
                        new Query(
                                "SELECT username FROM player \n" +
                                        "WHERE game_idgame=? AND playstatus_playstatus = 'uitgespeeld' AND score < (SELECT MAX(score) FROM player WHERE game_idgame=?)\n" +
                                        "GROUP BY username, game_idgame", "query"),
                        new QueryParameter(QueryParameter.INT, games.get(i).getId()),
                        new QueryParameter(QueryParameter.INT, games.get(i).getId())
                );
                while (rs.next()){
                    if(rs.getString("username").equals(account.getUsername())){
                        loses++;
                    }
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return loses;
    }

    /**
     * Gets the most used value from the database by account.
     *
     * @param account The account.
     * @return Most used value.
     */
    public int getMostUsedValue(Account account) {
        int value = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT eyes, COUNT(eyes) AS aantal_keer_gebruikt\n" +
                                    "FROM playerframefield JOIN player ON player.idplayer = playerframefield.player_idplayer\n" +
                                    "JOIN gamedie ON gamedie.dienumber = playerframefield.dienumber\n" +
                                    "WHERE player.username =?\n" +
                                    "GROUP BY eyes\n" +
                                    "ORDER BY aantal_keer_gebruikt DESC\n" +
                                    "LIMIT 1",
                            "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()) {
                value = rs.getInt("eyes");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Gets the count of different played accounts by account.
     *
     * @param account The account.
     * @return Count of different played accounts.
     */
    public int getCountDifferentPlayedAccounts(Account account) {
        int count = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT COUNT(DISTINCT username) AS count FROM player WHERE username !=? AND game_idgame IN(SELECT game_idgame FROM player WHERE username =?)",
                            "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername()),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if(rs.next()){
                count = rs.getInt("count");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
