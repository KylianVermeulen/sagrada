package nl.avans.sagrada;

import java.util.List;

/**
 * Test file to test db conn, query and update
 */
public class DatabaseTest {

    public static void main(String[] args) {
//        DBConnection db =  new DBConnection(devDatabaseUrl, sqlProperties);
//        db.addQueryToQueue(new Query("select * from account"));
//        db.addQueryToQueue(new Query("select * from account where username=?", new QueryParameter(123, "kylian")));
//        List<Query> executeQueue = db.executeQueue();
//        for (int i = 0; i < executeQueue.size(); i++) {
//            Query executedQuery = executeQueue.get(i);
//            try {
//                while (executedQuery.getResultSet().next()) {
//                    System.out.println(executedQuery.getResultSet().getString("username"));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

        // DAO with the sql queries
        AccountDAO accountDAO = new AccountDAO();

        // Get account from db
        Account account = accountDAO.getAccountByUsername("kylian");
        System.out.println(account.getUsername() + "   " + account.getPassword() + "   " + account.isActive());

        System.out.println();

        // Change account password and update db
        account.setPassword("new_password");
        account.setActive(true);
        accountDAO.updateAccount(account);

        Account new_account = new Account();
        new_account.setUsername("insert into_2");
        new_account.setPassword("password");
        new_account.setActive(true);
        accountDAO.addAccount(new_account);

        // Get all accounts from db
        List<Account> list = accountDAO.getAllAccounts();
        for (Account acc : list) {
            System.out.println(acc.getUsername() + "   " + acc.getPassword() + "   " + acc.isActive());
        }
    }
}
