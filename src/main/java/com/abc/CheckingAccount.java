package com.abc;

public class CheckingAccount extends Account{
	
	public CheckingAccount(AccountType accountType) {
		super(accountType);
		
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.CHECKING;
	}
	
	@Override
	public double interestEarned(){
		double amount = sumTransactions();
		return amount * 0.001;
	}
	
}
