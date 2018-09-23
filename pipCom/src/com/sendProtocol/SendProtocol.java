package com.sendProtocol;

public class SendProtocol {

	private static final int last = 4;
	
	public static int sendLast() {
		return last;
	}

	public static boolean isLast(int num) {
		return num == last;
	}
}