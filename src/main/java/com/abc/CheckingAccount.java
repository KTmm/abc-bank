package com.abc;

public class CheckingAccount extends Account{
	
	public CheckingAccount() {
		super();
		
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.CHECKING;
	}
	
	@Override
	public double interestEarned() throws OverDraftException{
		double amount = sumTransactions();
		return amount * 0.001;
	}
	
}
