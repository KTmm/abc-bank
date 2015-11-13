package com.abc;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.abc.Transaction.TransactionType;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	private final Date today = DateProvider.createDate(2015, Calendar.NOVEMBER, 11);
    	
    @Test
    public void testZeroAccount(){
        Customer oscar = new Customer("Oscar", 1);
        assertEquals(0, oscar.getNumberOfAccounts());
    }
    
	@Test
	public void testOneAccount() throws AccountExistsException{
		Customer oscar = new Customer("Oscar", 1);
		oscar.openAccount(new SavingsAccount(1));
	    assertEquals(1, oscar.getNumberOfAccounts());
	}
	
	@Test
    public void testTwoMultipleAccounts() throws AccountExistsException{
        Customer oscar = new Customer("Oscar",1);
        oscar.openAccount(new SavingsAccount(1));
        oscar.openAccount(new CheckingAccount(2));
        oscar.openAccount(new MaxiSavingsAccount(3));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test (expected = AccountExistsException.class)
    public void testAccountExistException() throws AccountExistsException {
    	Customer oscar = new Customer("Oscar",1);
        oscar.openAccount(new SavingsAccount(1));
        oscar.openAccount(new CheckingAccount(1));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
	
	
     @Test (expected = IllegalArgumentException.class)
     public void testEmptyCustomerName() throws AccountExistsException {
    	 Customer oscar = new Customer("",1);
         oscar.openAccount(new SavingsAccount(1));
    }
    
       
    @Test 
    public void testCustomerStatement() throws OverDraftException, AccountExistsException{

        Account checkingAccount = new CheckingAccount(1);
        Account savingsAccount = new SavingsAccount(2);

        Customer henry = new Customer("Henry", 1);
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.addNewTransaction(TransactionType.DEPOSIT, 500000.0, today);
        savingsAccount.addNewTransaction(TransactionType.DEPOSIT, 40000.0, today);
        savingsAccount.addNewTransaction(TransactionType.WITHDRAW, 200.0, today);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $500,000.00\n" +
                "Total $500,001.37\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $40,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $39,801.21\n" +
                "\n" +
                "Total In All Accounts $539,802.58", henry.getStatement(today));
    }

   

    
    
  
}
