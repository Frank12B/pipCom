package com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hidcom.AnswerListener;
import com.hidcom.Communicator;
import com.sendProtocol.SendProtocol;

/**
 * This class receives PIP commands sent by a 'Commander' instance and sends them to the PIP.
 * 
 * @version 1.0
 */
public class CommandReceiver implements Runnable {

	private static final Logger logger = Logger.getLogger("com.server");
	/**
	 * This is the connection to the server.
	 */
	private Socket clientSocket;
	/**
	 * This is the connection to the PIP as HID device.
	 */
	private Communicator com;

	/**
	 * Constructor taking two connections.
	 * 
	 * @param clientSocket Socket connection to the Server.
	 * @param com Connection to the PIP as HID device.
	 */
	public CommandReceiver(Socket clientSocket, Communicator com) {
		this.clientSocket = clientSocket;
		this.com = com;
	}

	/**
	 * Thread is executing; PIP commands are sent to this thread by a 'Commander' instance
	 * and finally sent to the PIP. An 'InputReportListener' writes its answer into the 
	 * 'OutputStream'. He will be read by the 'Commander' and returned.
	 */
	@Override
	public void run() {
		logger.fine("Thread " + Thread.currentThread().getId() + " started for client request!");
		
		final long duration = System.currentTimeMillis();
		
		DataInputStream in = null;

		try {			
			in = new DataInputStream(clientSocket.getInputStream());

			final int length = in.readInt();

			logger.fine("Thread " + Thread.currentThread().getId() + ": " + "Length of received byte array: "
					+ length);

			final byte[] input = new byte[length];

			in.readFully(input);

			logger.fine("Thread " + Thread.currentThread().getId() + ": Input: "
					+ new String(input, StandardCharsets.US_ASCII));
			
			final ExecutorService service = Executors.newSingleThreadExecutor();
			final Future<?> f = service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					sendResult(input);
					return null;
				}
				
			});
			
			try {
				f.get(1000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				logger.logp(Level.SEVERE,
						"com.server.CommandReceiver", 
						"call()", 
						"FutureTask interrupted!",
						e);
				
			} catch (ExecutionException e) {
				logger.logp(Level.SEVERE,
						"com.server.CommandReceiver", 
						"call()", 
						"FutureTask execution interrupted!",
						e);
				
			} catch (TimeoutException e) {
				logger.logp(Level.SEVERE,
						"com.server.CommandReceiver", 
						"call()", 
						"FutureTask timed out!",
						e);
			}

		} catch (IOException e2) {
			logger.logp(Level.SEVERE, 
						"CommandReceiver", 
						"run()", 
						"General IOException in CommandReceiver!", 
						e2);
		}
		
		logger.log(Level.INFO, "Execution time in Millisekunden: " + Long.toString((System.currentTimeMillis() - duration)));
	}

	private void sendResult(byte[] input) {
		
		try {
			com.sendeOutputReport(input);
			
			logger.finest("Outputreport was sent successfully!");
			
			DataOutputStream out = null;
			
			try {
				
				out = new DataOutputStream(clientSocket.getOutputStream());
				
				boolean eof = false;

				while (!eof) {
					
					final byte[] ergebnis = AnswerListener.queue.take();
					
					for (byte b : ergebnis) {
						out.writeByte(b);
						logger.finest("Write byte to Commander: " + b);
						
						if (b == 13)
							eof = true;
					}
				}

				out.writeByte(SendProtocol.sendLast());
				logger.finest("Send final byte to Commander!");

				logger.fine("Answer successfully sent!");
				
			} catch (SocketTimeoutException e) {
				logger.warning("Client socket timeout reached!");
				
			} catch (IOException e) {
				logger.logp(Level.SEVERE, 
						"CommandReceiver",
						"sendResult(byte[] input)", 
						"DataOutputStream could not be opened!", 
						e);
				
			} catch (InterruptedException e) {
				logger.logp(Level.SEVERE, 
						"CommandReceiver",
						"sendeResult(byte[] input)", 
						"Waiting for input for an answer written into the queue was interrupted!", 
						e);
			}
		} catch (PIPException e1) {
			logger.logp(Level.SEVERE, 
					this.getClass().getName(), 
					"void sendeResult(byte[] input)", 
					"Output report could not be sent!",
					e1);
		} finally {
			AnswerListener.queue.clear();
		}
	}
}