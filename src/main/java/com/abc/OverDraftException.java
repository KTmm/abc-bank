package com.abc;

public class OverDraftException extends Exception {
	
	private static final long serialVersionUID = -271754060360720959L;
	private String warning;
	public OverDraftException(String warning) {
		this.warning = warning;
	}
	
}
