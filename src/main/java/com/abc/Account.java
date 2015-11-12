package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

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
    
    protected double balanceToDate;
    protected double totalInterestToDate;
    protected TreeMap<Date, List<Transaction>> transactionsByDate; 
    protected long accountNumber;

    public Account(long accountNumber) {
        this.transactionsByDate = new TreeMap<Date, List<Transaction>>();
        this.accountNumber = accountNumber;
    }
    
     
    public void addNewTransaction( TransactionType transactionType, double amount, Date date ) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		Transaction t = new Transaction( transactionType, amount * transactionType.getAmountMultiplier(), date);
    		addTransactionToSortedMap(t);
       	}
    }
    
    private void addTransactionToSortedMap(Transaction transaction) {
		Date date = transaction.getTransactionDate();
		if ( transactionsByDate.containsKey(date)){
			transactionsByDate.get(date).add(transaction);
		} else {
			List<Transaction> transactionsInOneDay = new ArrayList<Transaction>();
			transactionsInOneDay.add(transaction);
			transactionsByDate.put(date, transactionsInOneDay);
		}
		
	}
  
	public double sumTransactions(List<Transaction> transactions) throws OverDraftException {
    	double amount = 0.0;
        for (Transaction t: transactions){
            amount += t.getAmount();
        }    
        return amount;
    }
	
	public long getAccountNumber(){
		return accountNumber;
	}
	
	public double interestEarned() throws OverDraftException {
		return -1;
	}
	
	public abstract AccountType getAccountType(); 
	
	public List<Transaction> getTransactionsForOneDay(Date date){
		return transactionsByDate.get(date);
	}

	public abstract void calculateBalanceAndInterestToDate(Date date) throws OverDraftException;
	
	public double getBalanceToDate(){
		return balanceToDate;
	}
	
	public double getTotalInterestToDate(){
		return totalInterestToDate;
	}

	public List<Transaction> getAllTransactionsForAccount(Date date) {
		List<Transaction> allTransactions = new ArrayList<Transaction>();
		Date beginDate = transactionsByDate.firstKey();
		while( !beginDate.after(date) ){
			allTransactions.addAll( transactionsByDate.get(beginDate));
			beginDate = DateProvider.plusDays(beginDate, 1);
		}
		return allTransactions;
	}
	
	

	
}
