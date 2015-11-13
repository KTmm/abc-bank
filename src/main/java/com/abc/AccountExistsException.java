package com.abc;

public class AccountExistsException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String warning;
	public AccountExistsException(String warning) {
		this.warning = warning;
	}
}
