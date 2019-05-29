package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.concurrent.Task;
import nl.avans.sagrada.dao.AccountDao;
import nl.avans.sagrada.model.Account;

public class AllAccountsTask extends Task<ArrayList<Account>> {

    @Override
    protected ArrayList<Account> call() throws Exception {
        AccountDao accountDao = new AccountDao();
        ArrayList<Account> accounts = accountDao.getAllAccounts();
        return accounts;
    }

}
