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
    	dbConnection = new DBConnection();
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

    public void updateAccount(Account account) {

    }

    public void addAccount(Account account) {

    }
    

}
