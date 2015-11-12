package com.abc;

import org.junit.Test;

import com.abc.Transaction.TransactionType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction deposit = new Transaction(TransactionType.DEPOSIT, 5, DateProvider.getInstance().now());
        Transaction withdraw = new Transaction(TransactionType.WITHDRAW, 5, DateProvider.getInstance().now());
        assertTrue( deposit instanceof Transaction);
        assertTrue( withdraw instanceof Transaction);
        assertEquals(TransactionType.DEPOSIT, deposit.getTransactionType());
        assertEquals( TransactionType.WITHDRAW, withdraw.getTransactionType());
    }
}
