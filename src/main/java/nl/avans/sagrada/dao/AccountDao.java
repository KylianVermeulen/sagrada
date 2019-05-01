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
}
