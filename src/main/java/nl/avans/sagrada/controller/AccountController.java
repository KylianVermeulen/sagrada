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
		Account accountFromDao = aDAO.getAccountByUsername(username);
		if (accountFromDao != null) {		
			String password1 = accountFromDao.getPassword();
			if (password1.equals(password)) {
				System.out.println("go to HomeScreen");
			}
		}  else {
			System.out.println("no user");
		}
	}
	

	public void goRegister() {
		System.out.println("Go to register RegisterView");
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
