package nl.avans.sagrada;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");

        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountByUsername("kylian");
        System.out.println(account.getUsername());

        account.setPassword("password");
        accountDAO.updateAccount(account);
    }
}
