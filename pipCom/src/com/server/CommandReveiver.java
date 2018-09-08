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
 * Diese Handlerklasse empfängt die PIP-Befehle, die von einem Commander
 * gesendet werden und schickt sie an den PIP.
 * 
 * @author Frank Brettreich
 * @version 1.0
 */
public class CommandReveiver implements Runnable {

	private static final Logger logger = Logger.getLogger("com.server");
	/**
	 * Dies ist die Client-Verbindung.
	 */
	private Socket clientSocket;
	/**
	 * Dies ist die Verbindung zum PIP als HID-device
	 */
	private Communicator com;

	/**
	 * Konstruktor, dem der Client-Socket und ein Communicator-Objekt mit dem
	 * PIP-HID-device übergeben wird.
	 * 
	 * @param clientSocket
	 *            Socket-Verbindung zum Client.
	 */
	public CommandReveiver(Socket clientSocket, Communicator com) {
		this.clientSocket = clientSocket;
		this.com = com;
	}

	/**
	 * Thread wird ausgeführt: PIP-Befehle werden vom Commander an diesen Thread
	 * gesendet und anschließend zum PIP gesendet. Ein InputReportListener schreibt
	 * die Antwort des PIP in den OutputStream. Der wird vom Befehlsgeber gelesen
	 * und zurückgegeben.
	 */
	@Override
	public void run() {
		logger.fine("Thread " + Thread.currentThread().getId() + " für Clientanfrage gestartet !");
		
		final long duration = System.currentTimeMillis();
		
		DataInputStream in = null;

		try {			
			in = new DataInputStream(clientSocket.getInputStream());

			final int length = in.readInt();

			logger.fine("Thread " + Thread.currentThread().getId() + ": " + "Länge des zu übergebenden Byte-Arrays: "
					+ length);

			final byte[] input = new byte[length];

			in.readFully(input);

			logger.fine("Thread " + Thread.currentThread().getId() + ": Eingabe: "
					+ new String(input, StandardCharsets.US_ASCII));
			
			final ExecutorService service = Executors.newSingleThreadExecutor();
			final Future<?> f = service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					sendeErgebnis(input);
					return null;
				}
				
			});
			
			try {
				f.get(1000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				logger.logp(Level.SEVERE,
						"com.server.CommandReveiver", 
						"call()", 
						"FutureTask interrupted!",
						e);
				
			} catch (ExecutionException e) {
				logger.logp(Level.SEVERE,
						"com.server.CommandReveiver", 
						"call()", 
						"FutureTask execution interrupted!",
						e);
				
			} catch (TimeoutException e) {
				logger.logp(Level.SEVERE,
						"com.server.CommandReveiver", 
						"call()", 
						"FutureTask timed out!",
						e);
			}

		} catch (IOException e2) {
			logger.logp(Level.SEVERE, "CommandReveiver", "run()", "Allgemeine IOException im PipClient!", e2);
		}
		
		logger.log(Level.INFO, "Dauer in Millisekunden: " + Long.toString((System.currentTimeMillis() - duration)));
	}

	private void sendeErgebnis(byte[] input) {
		
		try {
			com.sendeOutputReport(input);
			
			logger.finest("Outputreport wurde gesendet!");
			
			DataOutputStream out = null;
			
			try {
				
				out = new DataOutputStream(clientSocket.getOutputStream());
				
				boolean eof = false;

				while (!eof) {
					
					final byte[] ergebnis = AnswerListener.queue.take();
					
					for (byte b : ergebnis) {
						out.writeByte(b);
						logger.finest("Schreibe byte an Commander: " + b);
						
						if (b == 13)
							eof = true;
					}
				}

				out.writeByte(SendProtocol.sendeEnde());
				logger.finest("Übertrage Abschlussbyte an Commander");

				logger.fine("Ausgabe erfolgreich!");
				
			} catch (SocketTimeoutException e) {
				logger.warning("Client Socket Timeout wurde erreicht! Leere DataOutputStream....");
				
			} catch (IOException e) {
				logger.logp(Level.SEVERE, 
						"CommandReveiver",
						"sendeErgebnis(byte[] input)", 
						"DataOutputStream konnte nicht geöffnet werden!", 
						e);
				
			} catch (InterruptedException e) {
				logger.logp(Level.SEVERE, 
						"CommandReveiver",
						"sendeErgebnis(byte[] input)", 
						"Das Warten auf die Eingabe einer Antwort in die Queue wurde unterbrochen!", 
						e);
			}
		} catch (PIPException e1) {
			logger.logp(Level.SEVERE, 
					this.getClass().getName(), 
					"void sendeErgebnis(byte[] input)", 
					"Output report konnte nicht gesendet werden!",
					e1);
		}
	}
}