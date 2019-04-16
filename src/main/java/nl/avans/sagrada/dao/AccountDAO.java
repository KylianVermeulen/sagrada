package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;

public class AccountDAO {
    private DBConnection dbConnection;
    /**
     * Gets an account from the database by creating a query. This query selects
     * the account from the database which has the same username as the username
     * which was entered as a parameter for this method.
     * @param username String
     * @return an account of type Account if the username exists in the database, or else it will return null.
     */
    public Account getAccountByUsername(String username) {
        dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM account WHERE username=?", "query",
                    new QueryParameter(QueryParameter.STRING, username))
            );
            if (rs.next()) {
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                return account;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * A method used to return all accounts which are currently stored in the database.
     * @return all accounts in the database. If none exist, method will return null.
     */
    public ArrayList<Account> getAllAccounts() {
        DBConnection dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM account", "query"));
            ArrayList<Account> list = new ArrayList<Account>();
            while (rs.next()) {
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                list.add(account);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an account within the database, in order to change database fields linked to this account.
     * @param account Account
     */
    public void updateAccount(Account account) {
        dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("UPDATE account SET password=? WHERE username=?", "update"),
                    new QueryParameter(QueryParameter.STRING, account.getPassword()),
                    new QueryParameter(QueryParameter.STRING, account.getUsername())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Adds an account to the database, and also sets a username and a password for this account.
     * @param account Account
     */
    public void addAccount(Account account) {
        dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("INSERT INTO account (username, password) VALUES (?, ?)", "update"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername()),
                    new QueryParameter(QueryParameter.STRING, account.getPassword())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if an account exists within the database, by checking for the existence of the account's username.
     * @param account Account
     * @return true if the account exists within the database, returns false if the account does not exist within the database.
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
        return true;
    }
	

}
