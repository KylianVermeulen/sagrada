package nl.avans.sagrada;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.HelperQuery;
import nl.avans.sagrada.database.QueryParameter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDAO {

    public Account getAccountByUsername(String username) {
        DBConnection dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new HelperQuery("select * from account where username=?", "query",
                            new QueryParameter(QueryParameter.STRING, username)));
            Account account = new AccountRowMapper().mapRowOne(rs);
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        DBConnection dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(new HelperQuery("select * from account", "query"));
            List<Account> list = new AccountRowMapper().mapRowList(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAccount(Account account) {
        DBConnection dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new HelperQuery("UPDATE account SET password=?, active=? WHERE username=?", "update"),
                    new QueryParameter(QueryParameter.STRING, account.getPassword()),
                    new QueryParameter(QueryParameter.BOOLEAN, account.isActive()),
                    new QueryParameter(QueryParameter.STRING, account.getUsername()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
