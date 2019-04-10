package nl.avans.sagrada;

import java.util.List;
import java.util.Properties;

public class DatabaseTest {
    public static String devDatabaseUrl = "jdbc:mysql://sagrada.samebestserver.nl:100/kylian.sagrada?serverTimezone=Europe/Amsterdam";
    public static String dbPassword = "sagrada";
    public static String dbUser = "sagrada";
    public static Properties sqlProperties;

    public static void main(String[] args) {
        sqlProperties = new Properties();
        sqlProperties.put("user", dbUser);
        sqlProperties.put("password", dbPassword);

//        DBConnection db =  new DBConnection(devDatabaseUrl, sqlProperties);
//        db.addQueryToQueue(new HelperQuery("select * from account"));
//        db.addQueryToQueue(new HelperQuery("select * from account where username=?", new QueryParameter(123, "kylian")));
//        List<HelperQuery> executeQueue = db.executeQueue();
//        for (int i = 0; i < executeQueue.size(); i++) {
//            HelperQuery executedQuery = executeQueue.get(i);
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
        System.out.println(account.getUsername() + "   " + account.getPassword());

        System.out.println();

        // Change account password and update db
        account.setPassword("new_password");
        accountDAO.updateAccount(account);

        // Get all accounts from db
        List<Account> list = accountDAO.getAllAccounts();
        for (Account acc : list) {
            System.out.println(acc.getUsername() + "   " + acc.getPassword());
        }
    }
}
