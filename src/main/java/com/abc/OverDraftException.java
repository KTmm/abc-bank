package com.abc;

public class OverDraftException extends Exception {
	private String warning;
	public OverDraftException(String warning) {
		this.warning = warning;
	}
	
}
