package nl.avans.sagrada;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static nl.avans.sagrada.DatabaseTest.devDatabaseUrl;
import static nl.avans.sagrada.DatabaseTest.sqlProperties;

public class AccountDAO {

    public Account getAccountByUsername(String username) {
        DBConnection dbConnection = new DBConnection(devDatabaseUrl, sqlProperties);
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
        DBConnection dbConnection = new DBConnection(devDatabaseUrl, sqlProperties);
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
        DBConnection dbConnection = new DBConnection(devDatabaseUrl, sqlProperties);
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new HelperQuery("UPDATE account SET password=? WHERE username=?", "update"),
                    new QueryParameter(QueryParameter.STRING, account.getPassword()),
                    new QueryParameter(QueryParameter.STRING, account.getUsername()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
