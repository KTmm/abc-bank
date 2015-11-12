package com.abc;

public class CustomerExistsException extends Exception {
	private String warning;
	public CustomerExistsException(String warning) {
		this.warning = warning;
	}
}
