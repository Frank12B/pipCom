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

import com.hidcom.GetCommand;
import com.hidcom.GetCommands;
import com.sendProtocol.SendProtocol;
import com.server.PIPException;

import information.DeviceSettingInfo;

public class Commander implements Callable<byte[]> {
	
	private static final Logger logger = Logger.getLogger("com.server");
	private byte[] cmd;
	private String host;
	private int port;
	
	public Commander(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public byte[] sendCmd(byte[] cmd) {
		
		this.cmd = cmd;
		
		final ExecutorService service = Executors.newSingleThreadExecutor();
		
		final Future<byte[]> result = service.submit(this);
		
		try {
			
			final byte[] answer = result.get(5, TimeUnit.SECONDS);
			
			return answer;

		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.logp(Level.SEVERE, 
					this.getClass().getName(), 
					"byte[] sendCommand(byte[] cmd)", 
					"Following command could not have been executed during 5s and reached the timeout: " 
					+ new String(cmd, StandardCharsets.US_ASCII),
					e);
		} finally {
			service.shutdownNow();
		}
		
		return null;
	}

	@Override
	public byte[] call() throws Exception {
		
		try (final Socket socketServer = new Socket(host, port)){
			
			DataOutputStream serverInput = null;
			
			try {
				serverInput = new DataOutputStream(socketServer.getOutputStream());

				logger.fine("Client-input: " + new String(cmd, StandardCharsets.US_ASCII));

				// To the server:
				serverInput.writeInt(cmd.length);
				serverInput.write(cmd);
				serverInput.flush();

				logger.fine("Waiting for server answer...");
				DataInputStream pipInput = new DataInputStream(socketServer.getInputStream());
				logger.fine("Input from server...");
				final byte[] result = new byte[300];
				
				int tmp;
				int c = 0;
				
				while(!SendProtocol.isLast(tmp = pipInput.readByte())) {
					result[c] = (byte) tmp;
					c++;
				}
				
				logger.info("Answer received!");
				
				logger.fine(new String(result, StandardCharsets.US_ASCII));

				return result;
			} catch (IOException e) {
				logger.log(Level.SEVERE, "IOException in Commander: " + e.getMessage(), e);
			}
			
		} catch (UnknownHostException ex) {
			logger.severe("The host you want to connect to is not known! " + ex.getMessage());
			System.exit(-1);
		} catch (IOException ex) {
			logger.severe("IOException during connection: " + ex.getMessage());
			System.exit(-1);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Commander c = new Commander("raspberrypi", 13000);
		GetCommand g = GetCommands.ANZEIGE_BETRIEBSKONSTANTEN;
		
		DeviceSettingInfo ds = new DeviceSettingInfo(c.sendCmd(g.cmd()));
		try {
			ds.parseValues();
		} catch (PIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ds.DEVICE_OUTPUT_SOURCE_PRIORITY);	
	}
}