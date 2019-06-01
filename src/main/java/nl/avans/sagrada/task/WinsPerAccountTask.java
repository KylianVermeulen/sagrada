package nl.avans.sagrada.task;

import java.util.ArrayList;
import javafx.concurrent.Task;
import nl.avans.sagrada.dao.AccountDao;
import nl.avans.sagrada.model.Account;

public class WinsPerAccountTask extends Task<ArrayList<Account>> {

    @Override
    protected ArrayList<Account> call() throws Exception {
        AccountDao accountDao = new AccountDao();
        ArrayList<Account> accounts = accountDao.getWinsPerAccount();
        return accounts;
    }
}
