package nl.avans.sagrada.controller;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.model.Account;

public class AccountController {
	private Account account;
	
	public void login() {
		
	}
	public void register() {
		Account account = AccountDAO.getAccountByUsername(getUsername());
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
