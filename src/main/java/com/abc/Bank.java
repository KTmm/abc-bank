package com.abc;

import java.util.Date;
import java.util.HashMap;

public class Bank {
	private HashMap<Long, Customer> customers;

	public Bank() {
		customers = new HashMap<Long, Customer>();
	}

	public void addCustomer(Customer customer) throws CustomerExistsException {
		if (customers.containsKey(customer.getCustomerId())) {
			throw new CustomerExistsException("Customer already exists!");
		} else {
			customers.put(customer.getCustomerId(), customer);
		}
	}

	public String customerSummary() {
		String summary = "Customer Summary";
		if (customers.isEmpty()) {
			summary += "\n " + "There are no customers yet.";
		} else {
			for (Customer c : customers.values()) {
				summary += "\n - " + c.getName() + " ("
						+ format(c.getNumberOfAccounts(), "account") + ")";
			}
		}
		return summary;
	}

	// Make sure correct plural of word is created based on the number passed
	// in:
	// If number passed in is 1 just return the word otherwise add an 's' at the
	// end
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	public double totalInterestPaid(Date date) throws OverDraftException {
		double total = 0;
		for (Customer c : customers.values())
			total += c.getTotalInterestEarned(date);
		return total;
	}

	public Customer getCustomerById(long customerId) {
		return customers.get(customerId);
	}
}
