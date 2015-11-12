package com.abc;

import java.util.Date;
import java.util.List;

import com.abc.Transaction.TransactionType;

public class MaxiSavingsAccount extends Account{
	public static final double defaultRate = 0.001;
	public static final double highRate = 0.05;
	public static final int daysToReachHighRate = 10;
		
	public MaxiSavingsAccount(long accountNumber) {
		super(accountNumber);
		
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.MAXI_SAVINGS;
	}
	
	@Override
	public void calculateBalanceAndInterestToDate(Date date) throws OverDraftException{
	    	Date beginDate = transactionsByDate.firstKey();
	    	balanceToDate = 0.0;
	    	totalInterestToDate = 0.0;
	    	double priorDayBalance = 0.0;
	    	while (!beginDate.after(date)){
	    		if ( transactionsByDate.containsKey(beginDate)){
	    			balanceToDate += sumTransactions(transactionsByDate.get(beginDate));
	    		} else {
	    			balanceToDate = priorDayBalance;
	    		}
	    		if(balanceToDate < 0){
	    			throw new OverDraftException("Balance is small than zero! Overdraft!");
	    		}
	    		double interestForTheDay = interestEarnedForTheDay(beginDate, balanceToDate);
	    		totalInterestToDate += interestForTheDay;
	    		balanceToDate += interestForTheDay;
	    		priorDayBalance = balanceToDate;
	    		beginDate = DateProvider.plusDays(beginDate, 1);
	    	}
	}
	
	private double interestEarnedForTheDay(Date date, double balance) throws OverDraftException{
		if ( accountHasWithdrawInAPeriod(date, daysToReachHighRate)){
			return balance * defaultRate / 365;
		} else {
			return balance * highRate / 365;
		}
    }

	private boolean accountHasWithdrawInAPeriod(Date date, int dateToReachHighRate) {
		Date beginDate = DateProvider.plusDays(date, (daysToReachHighRate - 1) * (-1));
		while (!beginDate.after(date)){
			if (todayHasSpecificTransaction( beginDate, TransactionType.WITHDRAW)) {
				return true;
			}
			beginDate = DateProvider.plusDays(beginDate, 1);
		}
		return false;
	}

	private boolean todayHasSpecificTransaction(Date date, TransactionType transactionType) {
		if (!transactionsByDate.containsKey(date)) {
			return false;
		} else {
			List<Transaction> transactions = transactionsByDate.get(date);
				for(Transaction t : transactions){
						if (t.getTransactionType().equals(transactionType.WITHDRAW)){
							return true;
						}
				} 
			return false;
		}			
		
	}
	
	
}
