package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.Transaction.TransactionType;

public class AccountTest {
	
	@Test
    public void testAddTransactions() throws OverDraftException {
         CheckingAccount checking = new CheckingAccount();
         checking.addNewTransaction( TransactionType.DEPOSIT, 10);
         assertEquals( 10.0, checking.sumTransactions(), 0.001);
    }
	
	@Test (expected = IllegalArgumentException.class)
	public void testIllegalArgumentException() throws OverDraftException {
		CheckingAccount checking = new CheckingAccount();
        checking.addNewTransaction( TransactionType.DEPOSIT, -1);
        assertEquals( 10.0, checking.sumTransactions(), 0.001);
	}
	
	@Test 
	public void testSumForMulitpleTransactions() throws OverDraftException {
		CheckingAccount checking = new CheckingAccount();
        checking.addNewTransaction( TransactionType.DEPOSIT, 100);
        checking.addNewTransaction( TransactionType.WITHDRAW, 50);
        assertEquals( 50.0, checking.sumTransactions(), 0.001);
	}
	
	@Test (expected = OverDraftException.class)
	public void testOverDraftException() throws OverDraftException {
		CheckingAccount checking = new CheckingAccount();
        checking.addNewTransaction( TransactionType.DEPOSIT, 100);
        checking.addNewTransaction( TransactionType.WITHDRAW, 101);
        assertEquals( -1.0, checking.sumTransactions(), 0.001);
	}
}
