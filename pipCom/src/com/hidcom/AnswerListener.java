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
	 * This InputReportListener reads the answer of the PIP and writes the into an a Blocking array
	 * queue which can be read by the 'CommandReceiver' which sends the answer back to the client.
	 * 
	 * @version 1.0
	 *
	 */
	
	private static final Logger logger = Logger.getLogger("com.server");
	public static final BlockingQueue<byte[]> queue = new ArrayBlockingQueue<byte[]>(50);

	public AnswerListener() {}

	/* (non-Javadoc)
	 * @see purejavahidapi.InputReportListener#onInputReport(purejavahidapi.HidDevice, byte, byte[], int)
	 */
	@Override
	public void onInputReport(HidDevice source, byte reportID, byte[] reportData, int reportLength) {
		
		try {
			logger.log(Level.FINEST, "Size of queue (AnwerListener): " + queue.size());
			final byte[] result = new byte[reportLength];
			
			for (int i = 0; i < reportLength; i++) {
				result[i] = reportData[i];								
			}
			queue.put(result);
			logger.logp(Level.FINE, 
					this.getClass().getName(), 
					"onInputReport(HidDevice source, byte reportID, byte[] reportData, int reportLength)", 
					"Message receive from PIP: " + new String(result, StandardCharsets.US_ASCII), result);
		} catch (InterruptedException e1) {
			logger.logp(Level.SEVERE,
					this.getClass().getName(), 
					"onInputReport(HidDevice source, byte reportID, byte[] reportData, int reportLength)", 
					"Message from PIP could not be put into the queue!!", 
					e1);
		}
	}

}
