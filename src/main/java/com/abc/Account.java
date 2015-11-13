package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.abc.Transaction.TransactionType;

public abstract class Account {

	public enum AccountType {
		CHECKING("Checking Account"), SAVINGS("Savings Account"), MAXI_SAVINGS(
				"Maxi Savings Account");

		private String accountTypeStatement;

		AccountType(String accountTypeStatement) {
			this.accountTypeStatement = accountTypeStatement;
		}

		public String getAccountTypeStatement() {
			return accountTypeStatement;
		}
	}

	protected double balanceToDate;
	protected double totalInterestToDate;
	protected TreeMap<Date, List<Transaction>> transactionsByDate;
	protected long accountNumber; // this will be the key to distinguish each account

	/**
	 * With a Database we can generate accountNumber in DB but here I'll do it manually
	 */
	public Account(long accountNumber) {
		this.transactionsByDate = new TreeMap<Date, List<Transaction>>();
		this.accountNumber = accountNumber;
	}

	public void addNewTransaction(TransactionType transactionType, double amount, Date date) {
		if (amount <= 0) {
			throw new IllegalArgumentException(	"amount must be greater than zero");
		} else {
			Transaction t = new Transaction(transactionType, amount	* transactionType.getAmountMultiplier(), date);
			addTransactionToSortedMap(t);
		}
	}

	private void addTransactionToSortedMap(Transaction transaction) {
		Date date = transaction.getTransactionDate();
		if (transactionsByDate.containsKey(date)) {
			transactionsByDate.get(date).add(transaction);
		} else {
			List<Transaction> transactionsInOneDay = new ArrayList<Transaction>();
			transactionsInOneDay.add(transaction);
			transactionsByDate.put(date, transactionsInOneDay);
		}

	}

	public double sumTransactions(List<Transaction> transactions) {
		double amount = 0.0;
		for (Transaction t : transactions) {
			amount += t.getAmount();
		}
		return amount;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public List<Transaction> getTransactionsOnDate(Date date) {
		return transactionsByDate.get(date);
	}

	public double getBalanceToDate() {
		return balanceToDate;
	}

	public double getTotalInterestToDate() {
		return totalInterestToDate;
	}

	public List<Transaction> getAllTransactionsForAccountToDate(Date date) {
		List<Transaction> allTransactions = new ArrayList<Transaction>();
		Date beginDate = transactionsByDate.firstKey();
		while (!beginDate.after(date)) {
			if (transactionsByDate.containsKey(beginDate)){
				allTransactions.addAll(transactionsByDate.get(beginDate));
			}
			beginDate = DateProvider.plusDays(beginDate, 1);
		}
		return allTransactions;
	}
	
	public abstract AccountType getAccountType();
	
	/**Here I computed interest on daily compound interest. For each day interest will also be calculated on 
	 * newly added transactions. e.g. On Jan 01, 2015, deposit $100 and on Jan 01, 2015, this $100 will be used 
	 * to calculate Jan 01, 2015's daily interest. 
	 * I used 365 days for a year to simplify the calculation.*/
	public abstract void calculateBalanceAndInterestToDate(Date date) throws OverDraftException;
}
