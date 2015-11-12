package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.abc.Transaction.TransactionType;

public class Customer {
	private long customerId; //customer id would be the different for each customer
    private String name;
    private HashMap<Long, Account> accounts;

    public Customer(String name, long customerId) {
    	this.customerId = customerId;
    	if(name.equals("")){
    		throw new IllegalArgumentException("Customer must have a name!");
    	}
    	this.name = name;
        this.accounts = new HashMap<Long, Account>();
    }

    public String getName() {
        return name;
    }
    
    public long getCustomerId() {
    	return customerId;
    }
    
    public void openAccount(Account account) throws AccountExistsException {
    	if (accounts.containsKey(account.getAccountNumber())) {
    		throw new AccountExistsException("Account already exists!");
    	} else {
    		accounts.put(account.getAccountNumber(), account);
    	}
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    public Account getAccountById(long accountNumber){
    	return accounts.get( accountNumber );
    }
    
    public void transferBetweenAccounts(long fromAccountNumber, long toAccountNumber, double amount, Date date){
    	transferBetweenAccounts(accounts.get(fromAccountNumber), accounts.get(toAccountNumber), amount, date);
   }
    
    private void transferBetweenAccounts(Account from, Account to, double amount, Date date){
    	from.addNewTransaction(TransactionType.TRANSFER_OUT, amount, date);
    	to.addNewTransaction(TransactionType.TRANSFER_IN, amount, date);
    }
    
    public double totalInterestEarned(Date date) throws OverDraftException {
        double totalInterest = 0;
        for (Account a : accounts.values()){
            a.calculateBalanceAndInterestToDate(date);
            totalInterest += a.getTotalInterestToDate();
        }
        return totalInterest;
    }
    
    public String getStatement(Date date) throws OverDraftException {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts.values()) {
        	a.calculateBalanceAndInterestToDate(date);
            statement += "\n" + statementForAccount(a, date) + "\n";
            total += a.getBalanceToDate();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        
        return statement;
    }

    private String statementForAccount(Account a, Date date) throws OverDraftException {
        String s = "";
        s += a.getAccountType().getAccountStatement() + "\n";
        
        //Now total up all the transactions
        double total = a.getBalanceToDate();
        for (Transaction t : a.getAllTransactionsForAccount(date)) {
            s += "  " + t.getLabel() + " " + toDollars(t.getAmount()) + "\n";
        }
        
        
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
