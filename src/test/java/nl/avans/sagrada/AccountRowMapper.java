package nl.avans.sagrada;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRowMapper {

    public Account mapRowOne(ResultSet rs) throws SQLException {
        Account account = new Account();
        if (rs.next()) {
            account.setUsername(rs.getString("username"));
            account.setPassword(rs.getString("password"));
            account.setActive(rs.getBoolean("active"));
        }
        return account;
    }

    public List<Account> mapRowList(ResultSet rs) throws SQLException {
        List<Account> list = new ArrayList<>();
        while (rs.next()) {
            Account account = new Account();
            account.setUsername(rs.getString("username"));
            account.setPassword(rs.getString("password"));
            account.setActive(rs.getBoolean("active"));
            list.add(account);
        }
        return list;
    }
}
