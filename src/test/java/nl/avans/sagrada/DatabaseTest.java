package nl.avans.sagrada;

import java.util.ArrayList;

public class DatabaseTest {
    public static void main(String[] args) {
        System.out.println("test");

        // get Account by username
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountByUsername("kylian");

        // update account
        account.setPassword("password");
        accountDAO.updateAccount(account);

        // get all accounts and print usernames
        ArrayList<Account> list = new ArrayList<Account>();
        list.addAll(accountDAO.getAllAccounts());
        for (Account acc : list) {
            System.out.println(acc.getUsername());
        }
    }
}
