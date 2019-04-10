package nl.avans.sagrada;

import java.sql.ResultSet;
import java.util.List;

public class AccountDAO {
    DB db = new DB();

    public Account getAccountByUsername(String username) {
        db.open();

        String sql = "SELECT * FROM account WHERE username='"+username+"'";
        AccountRowMapper rowMapper = new AccountRowMapper();
        ResultSet rs = db.executeQuery(sql);
        Account account = rowMapper.mapRowOne(rs);

        db.close();
        return account;
    }

    public void updateAccount(Account account) {
        db.open();
        String sql = "UPDATE account SET password='"+account.getPassword()+"' WHERE username='"+account.getUsername()+"'";
        db.executeUpdate(sql);

        db.close();
    }
}
