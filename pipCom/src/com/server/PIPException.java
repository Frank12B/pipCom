package com.server;

public class PIPException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1613724430923509347L;

	public PIPException(String message) {
		super("Verbindungsproblem zum PIP! " + message);
	}

}
