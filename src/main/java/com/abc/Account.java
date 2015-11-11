package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public enum AccountType {
    	CHECKING("Checking Account"),
    	SAVINGS("Savings Account"), 
    	MAXI_SAVINGS("Maxi Savings Account");
    	
    	private String accountStatement;
    	AccountType( String accountStatement ){
    		this.accountStatement = accountStatement;
    	}
    	
    	public String getAccountStatement(){
    		return accountStatement;
    	}
    }
    
    public AccountType accountType;
    public List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }
    
   
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		transactions.add(new Transaction(-amount));
    	}
}

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

	public double interestEarned() {
		return -1;
	}

	public AccountType getAccountType() {
		return null;
	}

	
}
