package com.abc;

import java.util.Date;

public class Transaction {
	
	public enum TransactionType {
		DEPOSIT(1D), 
		WITHDRAW(-1D);
		
		private double amountMultiplier;
		TransactionType(Double amountMultiplier){
			this.amountMultiplier = amountMultiplier;
		}
		
		public double getAmountMultiplier(){
			return amountMultiplier;
		}
	}
	
    private final double amount;

    private Date transactionDate;
    private TransactionType transactionType;

    public Transaction(TransactionType transactionType, double amount) {
    	this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public TransactionType getTransactionType() {
    	return transactionType;
    }
    
    public Date getTransactionDate() {
    	return transactionDate;
    }
    
    public double getAmount() {
    	return amount;
    }
}
