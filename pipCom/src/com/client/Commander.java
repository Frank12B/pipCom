package com.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
import com.sendProtocol.SendProtocol;

public class Commander implements Callable<byte[]> {
	
	private static final Logger logger = Logger.getLogger("com.server");
	private byte[] cmd;
	private static final ExecutorService service = Executors.newFixedThreadPool(4);
	
	
	public byte[] sendCmd(byte[] cmd) {
		
		this.cmd = cmd;
		
		final Future<byte[]> result = service.submit(this);
		
		try {
			
			final byte[] answer = result.get(5, TimeUnit.SECONDS);
			
			return answer;

		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.logp(Level.SEVERE, 
					this.getClass().getName(), 
					"byte[] befehlSenden(byte[] cmd)", 
					"Folgender Befehl konnte nicht innerhalb von 5 Sekunden ausgeführt werden: " 
					+ cmd.toString(),
					e);
		} 
		
		return null;
	}

	@Override
	public byte[] call() throws Exception {
		
		try (final Socket socketServer = new Socket("raspberrypi", 13000)){
			
			DataOutputStream serverInput = null;
			
			try {
				serverInput = new DataOutputStream(socketServer.getOutputStream());

				logger.fine("Client-Eingabe: " + new String(cmd, StandardCharsets.US_ASCII));

				// Ab zum Server:
				serverInput.writeInt(cmd.length);
				serverInput.write(cmd);
				serverInput.flush();

				logger.fine("Warten auf Server-Antwort...");
				DataInputStream pipInput = new DataInputStream(socketServer.getInputStream());
				logger.fine("Eingabe vom Server erfolgt...");
				final byte[] result = new byte[300];
				
				int tmp;
				int c = 0;
				
				while((tmp = pipInput.readByte())!= (byte) SendProtocol.sendeEnde()) {
					result[c] = (byte) tmp;
					c++;
				}
				
				logger.info("Antwort erhalten!");
				
				logger.fine(new String(result, StandardCharsets.US_ASCII));

				return result;
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Befehlsgeber IOException: " + e.getMessage(), e);
			}
			
		} catch (UnknownHostException ex) {
			logger.severe("UnknownHostException bei Verbindung zu Host 'localhost', Port 13000: " + ex.getMessage());
			System.exit(-1);
		} catch (IOException ex) {
			logger.severe("IOException bei Verbindung zu Host 'raspberrypie', Port 13000: " + ex.getMessage());
			System.exit(-1);
		}
		
		return null;
	}
}