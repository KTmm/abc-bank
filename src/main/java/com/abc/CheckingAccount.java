package com.abc;

import java.util.Date;

public class CheckingAccount extends Account{
	
	public static final double annualRate = 0.001;
	
	public CheckingAccount(long accountNmuber) {
		super(accountNmuber);
		
	}
	
	@Override
	public AccountType getAccountType() {
		return AccountType.CHECKING;
	}
	
	@Override
	public void calculateBalanceAndInterestToDate(Date date) throws OverDraftException{
	    	Date beginDate = transactionsByDate.firstKey();
	    	balanceToDate = 0.0;
	    	totalInterestToDate = 0.0;
	    	double priorDayBalanceBalance = 0.0;
	    	while (!beginDate.after(date)){
	    		if( transactionsByDate.containsKey(beginDate)){
	    			balanceToDate += sumTransactions(transactionsByDate.get(beginDate));
	    		} else {
	    			balanceToDate = priorDayBalanceBalance;
	    		}
	    		if(balanceToDate < 0){
	    			throw new OverDraftException("Balance is small than zero! Overdraft!");
	    		}
	    		double interestForTheDay = balanceToDate * annualRate / 365;
	    		totalInterestToDate += interestForTheDay;
	    		balanceToDate += interestForTheDay;
	    		priorDayBalanceBalance = balanceToDate;
	    		beginDate = DateProvider.plusDays(beginDate, 1);
	    	}
	}

}
