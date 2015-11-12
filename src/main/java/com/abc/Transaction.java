package com.abc;

import java.util.Date;

public class Transaction {
	
	public enum TransactionType {
		DEPOSIT("deposit", 1D), 
		WITHDRAW("withdrawal", -1D),
		TRANSFER_IN("transfer in", 1D),
		TRANSFER_OUT("transfer out", -1D);
		
		private String label;
		private double amountMultiplier;
		TransactionType(String label, Double amountMultiplier){
			this.label = label;
			this.amountMultiplier = amountMultiplier;
		}
		
		public double getAmountMultiplier(){
			return amountMultiplier;
		}
		
		public String getLabel(){
			return label;
		}
	}
	
    private final double amount;

    private Date transactionDate;
    private TransactionType transactionType;

    public Transaction(TransactionType transactionType, double amount, Date date) {
    	this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = date;
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
    
    public String getLabel() {
    	return transactionType.getLabel();
    }
}
