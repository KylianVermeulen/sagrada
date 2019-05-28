package nl.avans.sagrada.task;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import nl.avans.sagrada.dao.AccountDao;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.AccountOverviewView;

public class AllAccountsTask extends Task<ArrayList<Account>> {
    private AccountOverviewView accountOverview;

    /**
     * Constructor for the task to get all accounts
     * @param accountOverview
     */
    public AllAccountsTask(AccountOverviewView accountOverview) {
        this.accountOverview = accountOverview;
    }

    @Override
    protected ArrayList<Account> call() throws Exception {
        AccountDao accountDao = new AccountDao();
        ArrayList<Account> accounts = accountDao.getAllAccounts();
        return accounts;
    }

}
