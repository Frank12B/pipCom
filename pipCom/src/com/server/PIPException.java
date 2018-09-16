package com.server;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PIPException extends Exception {
	
	/**
	 * 
	 * @version 1.0
	 */
	private static final long serialVersionUID = -1613724430923509347L;

	public PIPException(String description, byte[] command) {
		super("Communication with PIP failed!" + 
			  " Command: " + showCommand(command) + 
			  " Description: " + description);
	}
	
	public PIPException(String description, String infoClass, String value, String[] input) {
		super("Parsing of PIP response failed!" + 
			  " Causing class: " + infoClass + 
			  " Array of input values: " + Arrays.toString(input) +
			  " Description: " + description);
	}
	
	private static String showCommand(byte[] c) {
		
		return new String(c, StandardCharsets.US_ASCII);		
	}

}
