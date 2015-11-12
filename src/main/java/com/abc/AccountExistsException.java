package com.abc;

public class AccountExistsException extends Exception {
	private String warning;
	public AccountExistsException(String warning) {
		this.warning = warning;
	}
}
