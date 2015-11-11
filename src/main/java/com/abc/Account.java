package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public enum AccountType {
    	CHECKING, SAVINGS, MAXI_SAVINGS
    }
    
    public AccountType accountType;
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
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
