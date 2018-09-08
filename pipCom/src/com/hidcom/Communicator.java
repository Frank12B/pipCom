package com.hidcom;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.server.PIPException;
import com.server.Server;

import purejavahidapi.HidDevice;
import purejavahidapi.HidDeviceInfo;
import purejavahidapi.InputReportListener;
import purejavahidapi.PureJavaHidApi;

public class Communicator {

	private static HidDevice dev = null;	
	private static final Logger logger = Logger.getLogger("com.hidcom");
	private int vendor_id;
	private int product_id;

	public Communicator(int vendor_id, int product_id) {	
		this.vendor_id = vendor_id;	
		this.product_id = product_id;
	}
	
	public void open() {
		openHidDevice(vendor_id, product_id);
		setInputListener(new AnswerListener());		
	}

	private boolean openHidDevice(int VENDOR_ID, int PRODUCT_ID) {

		final List<HidDeviceInfo> devList = PureJavaHidApi.enumerateDevices();
		HidDeviceInfo devInfo = null;

		for (HidDeviceInfo info : devList) {
			if (info.getVendorId() == VENDOR_ID && info.getProductId() == PRODUCT_ID) {
				devInfo = info;
				break;
			}
		}

		try {			
			dev = PureJavaHidApi.openDevice(devInfo);
		} catch (IOException e) {
			
			logger.logp(Level.SEVERE, Server.class.getName(), "openHidDevice", 
					"'Open device' fehlgeschlagen! : " + e.getMessage(), e);
			
			return false;
		}
		return true;
	}

	private void setInputListener(InputReportListener listener) {

		dev.setInputReportListener(listener);
	}

	public boolean sendeOutputReport(byte[] input) throws PIPException {

			if (input.length > 8) {

				final byte[] tmp = new byte[8];

				for (int i = 8; i < 16; i++) {

					if (i > input.length - 1) {

						tmp[i - 8] = 0;

					} else {

						tmp[i - 8] = input[i];
					}
				}
				
				try {
					return dev.setOutputReport((byte) 0x00, input, 8) < 0 & dev.setOutputReport((byte) 0x00, tmp, 8) < 0;
					
				} catch (IllegalStateException ilex) {
					throw new PIPException("Senden des outputreports fehlgeschlagen!");					
				}
			}

			try {
				return dev.setOutputReport((byte) 0x00, input, 8) > 0;
				
			} catch (IllegalStateException ilex) {
				throw new PIPException("Senden des outputreports fehlgeschlagen!");					
			}
	}

	public boolean erfrageFeature(byte[] data, int length) {

		return dev.getFeatureReport(data, length) < 0;
	}

	public boolean setzeFeature(byte[] data, int length) {

		return dev.setFeatureReport(data, length) < 0;
	}

	public void close() {
		dev.close();
	}
}
