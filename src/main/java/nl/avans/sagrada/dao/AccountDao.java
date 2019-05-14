package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;

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
                ResultSet rs = dbConnection.executeQuery(
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getCountWins(Account account) {
        int count = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT account.username AS playername, COUNT(game_idgame) AS count_wins FROM player JOIN account ON player.username = account.username WHERE playstatus_playstatus = 'Finished' AND score = (SELECT MAX(score) FROM player GROUP BY game_idgame) AND account.username=? GROUP BY playername ORDER BY count_wins;",
                            "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()) {
                count = rs.getInt("count_wins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getCountLoses(Account account) {
        int count = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT account.username AS playername, COUNT(game_idgame) AS count_loses FROM player JOIN account ON player.username = account.username WHERE playstatus_playstatus = 'Finished' AND score = (SELECT MIN(score) FROM player GROUP BY game_idgame) AND account.username=? GROUP BY playername ORDER BY count_loses;",
                            "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()) {
                count = rs.getInt("count_loses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getHighestScore(Account account) {
        int score = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT account.username AS playername, MAX(score) AS hoogste_score FROM player JOIN account ON account.username = player.username WHERE account.username=? GROUP BY playername;", "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()){
                score = rs.getInt("hoogste_score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public String getMoseUsedColor(Account account) {
        String color = "";
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT account.username AS playername, diecolor AS meest_gebruikte_kleur, COUNT(diecolor) AS aantal_keer_gebruikt FROM player JOIN account ON account.username = player.username JOIN playerframefield ON player.idplayer = playerframefield.player_idplayer WHERE diecolor IS NOT NULL AND account.username=? GROUP BY playername, diecolor ORDER BY aantal_keer_gebruikt LIMIT 1;", "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next())  {
                color = rs.getString("meest_gebruikte_kleur");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return color;
    }

    public int getMostUsedValue(Account account) {
        int value = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT account.username AS playername, dienumber AS meest_gebruikte_waarde, COUNT(dienumber) AS aantal_keer_gebruikt FROM player JOIN account ON account.username = player.username JOIN playerframefield ON player.idplayer = playerframefield.player_idplayer WHERE account.username=? AND dienumber IS NOT NULL GROUP BY playername, dienumber ORDER BY aantal_keer_gebruikt LIMIT 1;","query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()) {
                value = rs.getInt("meest_gebruikte_waarde");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public int getCountDifferentPlayedAccounts(Account account) {
        int count = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT account.username AS playername, COUNT(DISTINCT idplayer) AS aantal_verschillende_tegenstanders FROM player JOIN account ON account.username = player.username WHERE player.username=? GROUP BY playername ORDER BY aantal_verschillende_tegenstanders;", "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
