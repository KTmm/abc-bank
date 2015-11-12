package com.abc;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.abc.Transaction.TransactionType;

public class AccountTest {
	private Date today = DateProvider.createDate(2015, Calendar.NOVEMBER, 10);
    private Date twoDaysLater = DateProvider.createDate(2015, Calendar.NOVEMBER, 12);
	
	@Test
    public void testAddTransactions() throws OverDraftException, ParseException {
         CheckingAccount checking = new CheckingAccount(1L);
         checking.addNewTransaction( TransactionType.DEPOSIT, 100000, today);
         checking.calculateBalanceAndInterestToDate(today);
         assertEquals( 100000*(1+0.001/365), checking.getBalanceToDate(), 0.00001);
    }
	
	@Test (expected = IllegalArgumentException.class)
	public void testIllegalArgumentException() throws OverDraftException, ParseException {
		CheckingAccount checking = new CheckingAccount(2L);
        checking.addNewTransaction( TransactionType.DEPOSIT, -1, today);
        checking.calculateBalanceAndInterestToDate(today);
    }
	
	@Test 
	public void testMulitpleTransactionsInCheckingAccount() throws OverDraftException, ParseException {
		CheckingAccount checking = new CheckingAccount(3L);
        checking.addNewTransaction( TransactionType.DEPOSIT, 1000000.0, today);
        checking.calculateBalanceAndInterestToDate(today);
        assertEquals( 1000000.0*(1+0.001/365), checking.getBalanceToDate(), 0.00001);
        checking.addNewTransaction( TransactionType.WITHDRAW, 500000.0, twoDaysLater);
        checking.calculateBalanceAndInterestToDate(twoDaysLater);
        double expectedTotal = (1000000*(1+0.001/365)* (1+0.001/365)-500000)* (1 + 0.001/365);
        assertEquals(expectedTotal, checking.getBalanceToDate(), 0.00001);
   }
	
	@Test 
	public void testMulitpleTransactionsInSavingsAccount() throws OverDraftException, ParseException {
		SavingsAccount saving = new SavingsAccount(3);
        saving.addNewTransaction( TransactionType.DEPOSIT, 900.0, today);
        saving.calculateBalanceAndInterestToDate(today);
        assertEquals( 900.0*(1+0.001/365), saving.getBalanceToDate(), 0.00001);
        saving.addNewTransaction( TransactionType.TRANSFER_IN, 500000.0, twoDaysLater);
        saving.calculateBalanceAndInterestToDate(twoDaysLater);
        double expectedTotal = (900*(1+0.001/365)* (1+0.001/365)+ 500000 - 1000)* (1 + 0.002/365) + 1001;
        assertEquals(expectedTotal, saving.getBalanceToDate(), 0.00001);
   }
	
	@Test 
	public void testMulitpleTransactionsInMaxiSavingsAccount() throws OverDraftException, ParseException {
		MaxiSavingsAccount maxiSavings = new MaxiSavingsAccount(4);
        maxiSavings.addNewTransaction( TransactionType.DEPOSIT, 10000.0, today);
        maxiSavings.calculateBalanceAndInterestToDate(today);
        assertEquals( 10000.0*(1+0.05/365), maxiSavings.getBalanceToDate(), 0.00001);
        maxiSavings.addNewTransaction( TransactionType.WITHDRAW, 500.0, twoDaysLater);
        maxiSavings.calculateBalanceAndInterestToDate(twoDaysLater);
        double expectedTotal = (10000*(1+0.05/365)* (1+0.05/365)+ -500)* (1 + 0.001/365);
        assertEquals(expectedTotal, maxiSavings.getBalanceToDate(), 0.00001);
    }
	
		
	@Test 
	public void testTransferBetweenAccounts() throws OverDraftException, ParseException, AccountExistsException {
		Customer bill = new Customer("Bill", 5);
		CheckingAccount checking = new CheckingAccount(5L);
		SavingsAccount saving = new SavingsAccount(6L);
		bill.openAccount(checking);
		bill.openAccount(saving);
		checking.addNewTransaction( TransactionType.DEPOSIT, 1000000, today);
        saving.addNewTransaction( TransactionType.DEPOSIT, 5000000, today);
        bill.transferBetweenAccounts(6, 5, 2500000, today);
        checking.calculateBalanceAndInterestToDate(today);
        saving.calculateBalanceAndInterestToDate(today);
        assertEquals( 3500000 *(1 + 0.001/365), checking.getBalanceToDate(), 0.001);
        assertEquals( (2500000-1000) * (1 + 0.002/365) + 1001, saving.getBalanceToDate(), 0.001);
	}
	
	@Test (expected = OverDraftException.class)
	public void testOverDraftException() throws OverDraftException, ParseException {
		CheckingAccount checking = new CheckingAccount(4L);
        checking.addNewTransaction( TransactionType.DEPOSIT, 100.0, today);
        checking.addNewTransaction( TransactionType.WITHDRAW, 101.0, today);
        checking.calculateBalanceAndInterestToDate(today);
        //assertEquals( -1.0, checking.getBalanceToDate(), 0.001);
	}
}
