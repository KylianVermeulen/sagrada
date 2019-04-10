package nl.avans.sagrada;

import java.sql.ResultSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");
        testDb();
    }

    public static void testDb() {
        DB db = new DB();
        db.open();

//        String sql = "SELECT * FROM account WHERE username='kylian'";
        String sql = "SELECT * FROM account";
        AccountRowMapper rowMapper = new AccountRowMapper();
        ResultSet rs = db.executeQuery(sql);
        List<Account> list = rowMapper.mapRowList(rs);

        for (Account account : list) {
            System.out.println(account.getUsername());
        }

        db.close();
    }
}
