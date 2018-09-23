package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hidcom.Communicator;

/**
 * This server processes requests on port 13000 and puts them into a queue. From this queue only
 * one request will be processed at the same time.
 * 
 * @version 1.0
 * 
 */
public class Server implements Runnable {
	
	private static int port = 13000;
	private static Logger logger = Logger.getLogger("com");
	public static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20);
	
	/**
	 * IDs to identify your PIP inverter as HID device. In this case it is am PIP2424MSE
	 */	
	private static final int VENDOR_ID = (short) 0x0665;
	private static final int PRODUCT_ID = (short) 0x5161;
	private static final Communicator com = new Communicator(VENDOR_ID, PRODUCT_ID);

	/**
	 * Server is listening on port 13000 and takes requests in an endless loop.
	 */

	@SuppressWarnings("resource")
	public void waitForClients() {

		// Bind on port 13000
		try {
			final ServerSocket serverSocket = new ServerSocket(port);
			com.open();
			
			// Wait for requests in an endless loop
			while (true) {
				try {
					// Block until request comes in
					logger.fine("ServerSocket - accepting");
					Socket clientSocket = serverSocket.accept();

					logger.fine("ServerSocket - accept done");
					
					// The further processing Thread uses a SingleThreadExecutor containing
					// a BlockingQueue to prevent simultaneously requests on the PIP
					try {
						queue.put(new CommandReceiver(clientSocket, com));
					} catch (InterruptedException e) {
						logger.logp(Level.SEVERE,
								Server.class.getName(),
								"void waitForClients()",
								"Putting thread into queue failed!", 
								e);
					}
					
					logger.info("ServerSocket - Thread started, next client please...");
					
				} catch (IOException e) {
					
					logger.logp(Level.SEVERE, Server.class.getName(), "waitForClient", 
							"'accept' on port 13000 failed! : " + e.getMessage(), e);
				}
			}
			
		} catch (IOException e) {
			logger.logp(Level.SEVERE, Server.class.getName(), "waitForClient", 
					"Binding to port 13000 failed: " + e.getMessage(), e);
			System.exit(-1);
		}
	}
	
	private static void executeQueue() {
		
		final Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				final ExecutorService ex = Executors.newSingleThreadExecutor();
				
				while (true) {
					try {
						ex.execute(queue.take());
						logger.logp(Level.FINE,
									this.getClass().getName(),
									"static void executeQueue()", 
									"Thread from queue started");
						
					} catch (InterruptedException e) {
						logger.logp(Level.SEVERE, 
								Server.class.getName(),
								"void ececuteQueue()", 
								"Taking thread from queue interrupted!",
								e);
					}	
				}
			}
		});
		
		thread.start();
	}

	@Override
	public void run() {
		executeQueue();
		waitForClients();	
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new Server());
		t.setName("PIP Communication Server");
		t.start();
	}
}
