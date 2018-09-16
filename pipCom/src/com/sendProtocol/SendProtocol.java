package com.sendProtocol;

public class SendProtocol {

	private static final int last = 4;
	private static final String sendeLogFile = "--sendLogFile--";
	private static final String setzeWinterModus = "--setWinterModus--";

	public static int sendLast() {
		return last;
	}

	public static boolean isLast(int num) {
		return num == last;
	}

	public static boolean sollLogFileGesendetWerden(String string) {
		return string.equals(sendeLogFile);
	}

	public static String sendeLogFile() {
		return sendeLogFile;
	}


	/**
	 * @return the setzeWinterModus command
	 */
	public static String setzeWinterModus() {
		return setzeWinterModus;
	}

	/**
	 * @return true if its the setzeWinterModus command
	 */
	public static boolean istSetzeWinterModus(String string) {
		return string.equals(setzeWinterModus);
	}
}