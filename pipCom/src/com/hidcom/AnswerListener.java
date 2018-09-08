package com.hidcom;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import purejavahidapi.HidDevice;
import purejavahidapi.InputReportListener;

public class AnswerListener implements InputReportListener {
	
	/**
	 * Dieser InputReportListener liest die Antwort des PIP und schreibt sie in
	 * den übergebenen OutputStream.
	 * 
	 * @author Frank Brettreich
	 * @version 1.0
	 *
	 */
	
	private static final Logger logger = Logger.getLogger("com.server");
	public static final BlockingQueue<byte[]> queue = new ArrayBlockingQueue<byte[]>(50);

	public AnswerListener() {}

	@Override
	public void onInputReport(HidDevice source, byte reportID, byte[] reportData, int reportLength) {
		
		try {
			logger.log(Level.FINEST, "Füllstand der Queue: " + queue.size());
			final byte[] result = new byte[reportLength];
			
			for (int i = 0; i < reportLength; i++) {
				result[i] = reportData[i];								
			}
			queue.put(result);
			logger.logp(Level.FINE, 
					this.getClass().getName(), 
					"onInputReport(HidDevice source, byte reportID, byte[] reportData, int reportLength)", 
					"Nachricht vom PIP erhalten: " + new String(result, StandardCharsets.US_ASCII), result);
		} catch (InterruptedException e1) {
			logger.logp(Level.SEVERE,
					this.getClass().getName(), 
					"onInputReport(HidDevice source, byte reportID, byte[] reportData, int reportLength)", 
					"Nachricht vom PIP konnte nicht in der queue abgelegt werden!", 
					e1);
		}
	}

}
