package com.abc;

import org.junit.Test;

import com.abc.Account.AccountType;
import com.abc.Transaction.TransactionType;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() throws OverDraftException {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount();
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.addNewTransaction(TransactionType.DEPOSIT, 100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() throws OverDraftException {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer( bill );
        bill.openAccount(checkingAccount);
        checkingAccount.addNewTransaction(TransactionType.DEPOSIT,1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() throws OverDraftException {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openAccount(checkingAccount);

        checkingAccount.addNewTransaction( TransactionType.DEPOSIT, 3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
