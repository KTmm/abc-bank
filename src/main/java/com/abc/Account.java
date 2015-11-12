package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.Transaction.TransactionType;

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
    
    public void addNewTransaction( TransactionType transactionType, double amount ) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		transactions.add( new Transaction( transactionType, amount * transactionType.getAmountMultiplier()));
    	}
    }
    
    public double sumTransactions() throws OverDraftException {
    	double amount = 0.0;
        for (Transaction t: transactions){
            amount += t.getAmount();
            if (amount < 0) {
            	throw new OverDraftException("Account has been overdraft!");
            }
        }    
        return amount;
    }

	public double interestEarned() throws OverDraftException {
		return -1;
	}

	public AccountType getAccountType() {
		return null;
	}

	
}
