package nl.avans.sagrada.controller;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.model.Account;

public class AccountController {
	private Account account;
	private AccountDAO aDAO;

	public AccountController() {
		aDAO = new AccountDAO();
	}

	public void login(String username, String password) {
		account.getUsername();
		account.getPassword();
		Account accountFromDao = aDAO.getAccountByUsername(username);
		if(accountFromDao != null) {
			accountFromDao.getPassword();
		}
		
	}

	public void register() {

	}

	public void acceptInvite() {

	}

	public void denyInvite() {

	}

	public void gameOverview() {

	}

	public void joinGame() {

	}

	public void setupNewGame() {

	}

	public void inviteOverview() {

	}
}
