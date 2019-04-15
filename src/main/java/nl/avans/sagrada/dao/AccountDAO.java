package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO {
    private DBConnection dbConnection;

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
