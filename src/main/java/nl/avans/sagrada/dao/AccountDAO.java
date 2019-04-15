package nl.avans.sagrada.dao;

import java.sql.ResultSet;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;

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

    public void updateAccount(Account account) {

    }

    public void addAccount(Account account) {

    }

}
