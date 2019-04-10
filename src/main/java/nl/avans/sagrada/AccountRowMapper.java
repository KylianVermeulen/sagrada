package nl.avans.sagrada;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRowMapper {
    public Account mapRowOne(ResultSet rs) {
        Account account = new Account();
        try {
            account.setUsername(rs.getString("username"));
            account.setPassword(rs.getString("password"));
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> mapRowList(ResultSet rs) {
        List<Account> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(mapRowOne(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
