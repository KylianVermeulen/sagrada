package nl.avans.sagrada.dao;

import java.sql.ResultSet;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO {
    private DBConnection dbConnection;
    
    public AccountDAO() {
        dbConnection = new DBConnection();
    }

    /**
     * Gets a account by the username
     * When there was no account found it will return null
     * @param username
     * @return Account model
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
            // TODO: handle exception
        }
        return account;
    }

    /**
     * Get all accounts
     *
     * @return ArrayList<Account>
     */
    public ArrayList<Account> getAllAccounts() {
        DBConnection dbConnection = new DBConnection();
        ArrayList<Account> list = new ArrayList<Account>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM account", "query"));
            while (rs.next()) {
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Update account
     *
     * @param account Account
     */
    public void updateAccount(Account account) {
        dbConnection = new DBConnection();
        if (accountExists(account)) {
            try {
                ResultSet rs = dbConnection.executeQuery(
                        new Query("UPDATE account SET password=? WHERE username=?", "update"),
                        new QueryParameter(QueryParameter.STRING, account.getPassword()),
                        new QueryParameter(QueryParameter.STRING, account.getUsername())
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't update non existing account");
        }
    }

    /**
     * Add account
     *
     * @param account Account
     */
    public void addAccount(Account account) {
        dbConnection = new DBConnection();
        if (!accountExists(account)) {
            try {
                ResultSet rs = dbConnection.executeQuery(
                        new Query("INSERT INTO account (username, password) VALUES (?, ?)", "update"),
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
     * Check if account exists
     *
     * @param account Account
     * @return boolean true when exists
     */
    public boolean accountExists(Account account) {
        dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT count(*) as count FROM account WHERE username=?", "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    return  true;
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
