package com.abc;

public class CustomerExistsException extends Exception {
	
	private static final long serialVersionUID = 1183184815775586080L;
	private String warning;
	public CustomerExistsException(String warning) {
		this.warning = warning;
	}
}
