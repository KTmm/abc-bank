package com.abc;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.abc.Transaction.TransactionType;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Date today = DateProvider.createDate(2015, Calendar.NOVEMBER, 11);
    private Date tomorrow = DateProvider.createDate(2015, Calendar.NOVEMBER, 12);
    
    @Test
    public void customerSummaryZeroCustomer() throws AccountExistsException {
        Bank bank = new Bank();
        assertEquals("Customer Summary\n There are no customers yet.", bank.customerSummary());
    }
    
    @Test
    public void customerSummaryOneCustomer() throws AccountExistsException, CustomerExistsException {
        Bank bank = new Bank();
        Customer john = new Customer("John", 1);
        john.openAccount(new CheckingAccount(1));
        bank.addCustomer(john);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummaryMultipleCustomers() throws AccountExistsException, CustomerExistsException {
        Bank bank = new Bank();
        Customer john = new Customer("John", 1);
        john.openAccount(new CheckingAccount(1));
        bank.addCustomer(john);
        Customer bill = new Customer("Bill", 2);
        bill.openAccount(new CheckingAccount(2));
        bill.openAccount(new SavingsAccount(3));
        bank.addCustomer(bill);
        assertEquals("Customer Summary\n - John (1 account)\n - Bill (2 accounts)", bank.customerSummary());
    }
    
    @Test (expected = CustomerExistsException.class)
    public void customerSummaryCustomerExistException() throws AccountExistsException, CustomerExistsException {
        Bank bank = new Bank();
        Customer john = new Customer("John", 1);
        bank.addCustomer(john);
        Customer bill = new Customer("Bill", 1);
        bank.addCustomer(bill);
        assertEquals("Customer Summary\n - John (0 account)\n - Bill (0 account)", bank.customerSummary());
    }
    
    @Test
    public void checkingAccountWithInterest() throws OverDraftException, ParseException, AccountExistsException, CustomerExistsException {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", 1);
        Account checkingAccount = new CheckingAccount(2);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.addNewTransaction(TransactionType.DEPOSIT, 100000.0, today);
        System.out.println("checking interest = " + bank.totalInterestPaid(today));
        assertEquals(100000*0.001/365, bank.totalInterestPaid(today), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountWithInterest() throws OverDraftException, ParseException, CustomerExistsException, AccountExistsException {
        Bank bank = new Bank();
        Account savings = new SavingsAccount(1);
        Customer bill = new Customer("Bill", 1);
        bank.addCustomer( bill );
        bill.openAccount(savings);
        savings.addNewTransaction(TransactionType.DEPOSIT,800.0, today);
        assertEquals(800*0.001/365, bank.totalInterestPaid(today), DOUBLE_DELTA);
        savings.addNewTransaction(TransactionType.DEPOSIT,5000.0, tomorrow);
        double expectedInterst = 800*0.001/365 + (800*(1+ 0.001/365)+5000 -1000) * 0.002/365 + 1;
        System.out.println("saving interest = " + bank.totalInterestPaid(tomorrow));
        assertEquals(expectedInterst, bank.totalInterestPaid(tomorrow), DOUBLE_DELTA);
        
    }

    @Test
    public void maxiSavingsAccountWithInterest() throws OverDraftException, ParseException, CustomerExistsException, AccountExistsException {
        Bank bank = new Bank();
        Account maxiSaving = new MaxiSavingsAccount(3);
        Customer bill = new Customer("Bill",4);
        bank.addCustomer(bill);
        bill.openAccount(maxiSaving);
        maxiSaving.addNewTransaction( TransactionType.DEPOSIT, 3000.0, today);
        System.out.println("maxi interest = " + bank.totalInterestPaid(today));
        assertEquals(3000*0.05/365, bank.totalInterestPaid(today), DOUBLE_DELTA);
    }

    @Test
    public void MultipleAccountWithInterest() throws OverDraftException, ParseException, CustomerExistsException, AccountExistsException {
        Bank bank = new Bank();
        Account maxiSaving = new MaxiSavingsAccount(1);
        Customer bill = new Customer("Bill",1);
        bank.addCustomer(bill);
        bill.openAccount(maxiSaving);
        maxiSaving.addNewTransaction( TransactionType.DEPOSIT, 3000.0, today);
        Account savings = new SavingsAccount(2);
        bill.openAccount(savings);
        savings.addNewTransaction(TransactionType.DEPOSIT,800.0, today);
        Customer joe = new Customer("Joe",2);
        bank.addCustomer(joe);
        Account checkingAccount = new CheckingAccount(3);
        joe.openAccount(checkingAccount);
        checkingAccount.addNewTransaction(TransactionType.DEPOSIT, 100000.0, today);
        double expectedTotalInterest = 3000*0.05/365 + 800*0.001/365 + 100000*0.001/365;
        System.out.println("total interest = " + bank.totalInterestPaid(today));
        assertEquals(expectedTotalInterest, bank.totalInterestPaid(today), DOUBLE_DELTA);
    }
    
}
