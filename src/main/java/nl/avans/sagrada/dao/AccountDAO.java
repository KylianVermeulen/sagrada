package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;

public class AccountDAO {
	private DBConnection dbConnection;
	
	
    public Account mapRowOne(ResultSet rs) throws SQLException {
        Account account = new Account();
        if (rs.next()) {
            account.setUsername(rs.getString("username"));
            account.setPassword(rs.getString("password"));
        }
        return account;
    }
    
	public Account getAccountByUsername(String username) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("select * from account where username=?", "query",
                            new QueryParameter(QueryParameter.STRING, username)));
            Account account = mapRowOne(rs);
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	
    public void addAccount(Account account) {
        if (accountExists(account)) {System.out.println("Account already exists"); return;}
        DBConnection dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("INSERT INTO account (username, password) VALUES (?, ?, ?)", "update"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername()),
                    new QueryParameter(QueryParameter.STRING, account.getPassword()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean accountExists(Account account) {
        DBConnection dbConnection = new DBConnection();
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
	
	public void updateAccount(Account account) {
		
	}
	

}
