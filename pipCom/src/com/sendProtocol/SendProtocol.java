package com.sendProtocol;

public class SendProtocol {

	private static final int last = 4;
	private static final int received = 11;
	private static final String stopServer = "--stopServer--";
	private static final String sendeLogFile = "--sendLogFile--";
	private static final String sendeHtmlFile = "--sendHtmlFile--";
	private static final String sendeQPIGS = "--sendQPIGS--";
	private static final String sendeQPIGS2 = "--sendQPIGS2--";
	private static final String sendeDeviceInfos = "--sendDeviceInfos--";
	private static final String sendeGeraeteEinstellungen = "--sendDeviceSetting--";
	private static final String setzeInselModus = "--setInselModus--";
	private static final String setzeHausstromModus = "--setHausstromModus--";
	private static final String setzeWinterModus = "--setWinterModus--";
	private static final String setzeLadungsquelleSolarFirst = "--setLadungsquelleSolarFirst--";
	private static final String setzeLadungsquelleSolarOnly = "--setLadungsquelleSolarOnly--";
	private static final String setzeBatterieEntladungsSchlussspannung = "--setzeBatterieEntladungsSchlussspannung--";
	private static final int beginneNachricht = 31;

	public static String beendeServer() {
		return stopServer;
	}

	public static int rueckMeldung() {
		return received;
	}

	public static int sendeEnde() {
		return last;
	}

	public static int beginneNachricht() {
		return beginneNachricht;
	}

	public static boolean istNachrichtBeginn(int num) {
		return num == beginneNachricht;
	}

	public static boolean istEnde(int num) {
		return num == last;
	}

	public static boolean istStopBefehl(String cmd) {
		return cmd.equals(stopServer);
	}

	public static boolean wurdeErhalten(int character) {
		return character == received;
	}

	public static boolean wirdServerGestoppt(String string) {
		return string.equals(stopServer);
	}

	public static boolean sollLogFileGesendetWerden(String string) {
		return string.equals(sendeLogFile);
	}

	public static boolean sollHtmlFileGesendetWerden(String string) {
		return string.equals(sendeHtmlFile);
	}

	public static String sendeLogFile() {
		return sendeLogFile;
	}

	public static String sendeHtmlFile() {
		return sendeHtmlFile;
	}

	/**
	 * @return true if its the sendeQPIGS command
	 */
	public static String sendeqpigs() {
		return sendeQPIGS;
	}

	/**
	 * @return true if its the sendeQPIGS command
	 */
	public static boolean istSendeqpigs(String string) {
		return string.equals(sendeQPIGS);
	}
	
	/**
	 * @return true if its the sendeQPIGS2 command
	 */
	public static String sendeqpigs2() {
		return sendeQPIGS2;
	}

	/**
	 * @return true if its the sendeQPIGS2 command
	 */
	public static boolean istSendeqpigs2(String string) {
		return string.equals(sendeQPIGS2);
	}

	/**
	 * @return the sendeDeviceInfo command
	 */
	public static String sendedeviceinfos() {
		return sendeDeviceInfos;
	}

	/**
	 * @return true if its the sendeDeviceInfo command
	 */
	public static boolean istSendedeviceinfos(String string) {
		return string.equals(sendeDeviceInfos);
	}
	
	/**
	 * @return the sendeDeviceInfo command
	 */
	public static String sendeGeraeteEinstellungen() {
		return sendeGeraeteEinstellungen;
	}

	/**
	 * @return true if its the sendeDeviceInfo command
	 */
	public static boolean istSendeGeraeteEinstellungen(String string) {
		return string.equals(sendeGeraeteEinstellungen);
	}
	
	/**
	 * @return the setzeInselModus command
	 */
	public static String setzeInselModus() {
		return setzeInselModus;
	}

	/**
	 * @return true if its the setzeInselModus command
	 */
	public static boolean istSetzeInselModus(String string) {
		return string.equals(setzeInselModus);
	}
	
	/**
	 * @return the setzeHausstromModus command
	 */
	public static String setzeHausstromModus() {
		return setzeHausstromModus;
	}

	/**
	 * @return true if its the setzeHausstromModus command
	 */
	public static boolean istSetzeHausstromModus(String string) {
		return string.equals(setzeHausstromModus);
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
	
	/**
	 * @return the setzeLadungsquelleSolarFirst command
	 */
	public static String setzeLadungsquelleSolarFirst() {
		return setzeLadungsquelleSolarFirst;
	}

	/**
	 * @return true if its the setzeLadungsquelleSolarFirst command
	 */
	public static boolean istSetzeLadungsquelleSolarFirst(String string) {
		return string.equals(setzeLadungsquelleSolarFirst);
	}
	
	/**
	 * @return the setzeLadungsquelleSolarOnly command
	 */
	public static String setzeLadungsquelleSolarOnly() {
		return setzeLadungsquelleSolarOnly;
	}

	/**
	 * @return true if its the setzeLadungsquelleSolarOnly command
	 */
	public static boolean istSetzeLadungsquelleSolarOnly(String string) {
		return string.equals(setzeLadungsquelleSolarOnly);
	}
	
	/**
	 * @return the setzeBatterieEntladungsSchlussspannung command
	 */
	public static String setzeBatterieEntladungsSchlussspannung() {
		return setzeBatterieEntladungsSchlussspannung;
	}

	/**
	 * @return true if its the setzeBatterieEntladungsSchlussspannung command
	 */
	public static boolean istSetzeBatterieEntladungsSchlussspannung(String string) {
		return string.equals(setzeBatterieEntladungsSchlussspannung);
	}
}
