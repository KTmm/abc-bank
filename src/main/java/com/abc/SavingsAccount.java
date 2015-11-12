package com.abc;

import java.util.Date;

public class SavingsAccount extends Account{
	public static final double defaultRate = 0.001;
	public static final double secondTireRate = 0.002;
	public static final double amountToReachSecondTireRate = 1000;
	
	public SavingsAccount(long accountNumber) {
		super(accountNumber);
		
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.SAVINGS;
	}
	
	
	@Override
	public void calculateBalanceAndInterestToDate(Date date) throws OverDraftException{
	    	Date beginDate = transactionsByDate.firstKey();
	    	balanceToDate = 0.0;
	    	totalInterestToDate = 0.0;
	    	double priorDayBalance = 0.0;
	    	while (!beginDate.after(date)){
	    		if (transactionsByDate.containsKey(beginDate)){
	    			balanceToDate += sumTransactions(transactionsByDate.get(beginDate));
	    		} else {
	    			balanceToDate = priorDayBalance;
	    		}
	    		if(balanceToDate < 0){
	    			throw new OverDraftException("Balance is small than zero! Overdraft!");
	    		}
	    		double interestForTheDay = interestEarnedForTheDay(balanceToDate);
	    		totalInterestToDate += interestForTheDay;
	    		balanceToDate += interestForTheDay;
	    		priorDayBalance = balanceToDate;
	    		beginDate = DateProvider.plusDays(beginDate, 1);
	    	}
	}
	
	private double interestEarnedForTheDay(double balance) throws OverDraftException{
		if (balance <= amountToReachSecondTireRate){
			return balance * defaultRate / 365;
		}
		else {
			return amountToReachSecondTireRate * defaultRate + (balance - amountToReachSecondTireRate) * secondTireRate / 365;
		}
    }
	
}
