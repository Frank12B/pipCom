package com.hidcom;

import com.crcccitt.CRCUtil;

public class PipCommandGenerator {

	// -------------------INFO-GETTERS------------------------------------------------------------//
	public static final String PROTOKOLL_ID = "QPI";
	public static final String DEVICE_GENERAL_STATUS_PARAMETER = "QPIGS";
	public static final String DEVICE_SerialNum = "QID";
	public static final String FIRMWARE_VERSION = "QVFW";
	public static final String FIRMWARE_CPU_SCC1 = "QVFW2";
	public static final String FIRMWARE_CPU_SCC2 = "QVFW3";
	public static final String FIRMWARE_CPU_SCC3 = "QVFW4";

	/**
	 * Anzeige der Betriebskonstanten
	 */
	public static final String DEVICE_INFO = "QPIRI";
	public static final String DEVICE_FLAG_STATUS = "QFLAG"; // silence buzzer, power saving, overload restart, etc
	public static final String DEVICE_GENERAL_STATUS_PARAMETER2 = "QPIGS2";
	public static final String DEVICE_MODE_STATUS = "QMOD";
	public static final String DEVICE_WARNING_STATUS = "QPIWS";
	public static final String DEVICE_DEFAULT_SETTING = "QDI";
	public static final String SELECTABLE_VALUES_MAX_CHARGE_CURRENT = "QMCHGCR";
	public static final String SELECTABLE_VALUES_MAX_UTILITY_CHARGE_CURRENT = "QMUCHGCR";
	public static final String SELECTABLE_VALUES_MAX_SOLAR_CHARGE_CURRENT = "QMSCHGCR";
	public static final String ASK_IF_DEVICE_HAS_BOOTSTRAP = "QBOOT";
	public static final String ASK_CHARGING_STAGE = "QCST";
	public static final String ASK_CV_CHARGING_TIME = "QCVT";
	// -------------SETTING
	// parameters-PE=ENABLE-PD=DISABLE---------------------------------------//
	public static final String SET_ENABLE_PREFIX = "PE";
	public static final String SET_DISABLE_PREFIX = "PD";
	// --------------------------------------------------------------------------------------------//
	public static final String SET_SILENCE_BUZZER = "AAA";
	public static final String SET_OVERLOAD_BYPASS = "BBB";
	public static final String SET_POWER_SAVING = "JJJ";
	public static final String SET_ESCAPE_TO_DEFAULT_SCREEN = "KKK";
	public static final String SET_OVERLOAD_RESTART = "UUU";
	public static final String SET_OVER_TEMP_RESTART = "VVV";
	public static final String SET_BACKLIGHT = "XXX";
	public static final String SET_INTERRUPT_ALARM = "YYY";
	public static final String SET_FAULT_CODE_RECORD = "ZZZ";
	// ------------SET CONTROL PARAMS TO
	// DEFAULT--------------------------------------------------//
	public static final String SET_CONTROL_PARAMS_DEFAULT = "PF";
	// -------------------------------------------------------------------------------------------//
	public static final String SET_MAX_CHARGING_CURRENT_OVER_100 = "MNCHGC";
	public static final String SET_MAX_CHARGING_CURRENT = "MCHGC";
	public static final String SET_MAX_UTILITY_CHARGING_CURRENT = "MUCHGC";
	public static final String SET_MAX_SOLAR_CHARGING_CURRENT = "MSCHGC";
	public static final String SET_DEVICE_OUTPUT_RATING_FREQ = "F"; // 50 or 60 Hz
	public static final String SET_DEVICE_OUTPUT_SOURCE_PRIORITY = "POP"; // 00 ut first, 01 solar first, 02 sbu

	/**
	 * Dieses Feld setzt die Batteriespannung, bei der der PIP auf Netzversorgung
	 * zur�ckgreift. M�gliche Werte sind:
	 * 
	 * 12V unit: 11V/11.3V/11.5V/11.8V/12V/12.3V/12.5V/12.8V 24V unit:
	 * 22V/22.5V/23V/23.5V/24V/24.5V/25V/25.5V 48V unit:
	 * 44V/45V/46V/47V/48V/49V/50V/51V
	 */
	public static final String SET_BATTERY_RECHARGE_VOLTAGE = "PBCV"; // When to recharge battery

	/**
	 * Dieses Feld setzt die Batteriespannung, ab der die Batterie wieder entladen
	 * werden darf. M�gliche Werte sind:
	 * 
	 * 12V unit: 00.0V12V/12.3V/12.5V/12.8V/13V/13.3V/13.5V/13.8V/14V/14.3V/14.5 24V
	 * unit: 00.0V/24V/24.5V/25V/25.5V/26V/26.5V/27V/27.5V/28V/28.5V/29V 48V unit:
	 * 00.0V48V/49V/50V/51V/52V/53V/54V/55V/56V/57V/58V 00.0V means battery is
	 * full(charging in float mode).
	 */
	public static final String SET_BATTERY_REDISCHARGE_VOLTAGE = "PBDV";
	public static final String SET_DEVICE_CHARGER_PRIORITY = "PCP"; // 00 utility first, 01 sol first, 02 sol and ut, 03
																	// sol only
	public static final String SET_DEVICE_GRID_WORKING_RANGE = "PGR"; // 00 appliance, 01 UPS
	public static final String SET_BATTERY_TYPE = "PBT"; // 00 AGM, 01 flooded
	public static final String SET_BATTERY_CUTOFF_VOLTAGE = "PSDV";
	public static final String SET_BATTERY_CONSTANT_VOLTAGE_CHARGING_VOLTAGE = "PCVV";
	public static final String SET_BATTERY_FLOAT_CHARGING_VOLTAGE = "PBFT";
	public static final String SET_CHARGING_STAGE = "PCST"; // 00 auto determined, 01 2-stage, 02 3-stage
	public static final String SET_CONSTANT_VOLTAGE_CHARGING_TIME = "PCVT"; // 0 - 240 min (int tenth), 255 auto
																			// determined
	public static final String SET_PV_OK_CONDITION = "PPVOKC"; // 0: as long as 1 unit is connected PV is OK, 1: all
																// inverters have to be connected
	public static final String SET_SOLAR_POWER_BALANCE = "PSPB"; // 0: PV input = max charge current, 1: PV input = max
																	// charge + load current

	public static byte[] createBefehl(String befehl) {

		int wordLen = befehl.length();

		String crcStr = getCrc16Xmodem(befehl);

		long[] crcHex = new long[crcStr.length() / 2];

		int z = 0;
		for (int i = 0; i < crcStr.length(); i++) {
			if (i % 2 == 0) {
				crcHex[z] = Long.parseLong("" + crcStr.charAt(i) + crcStr.charAt(i + 1), 16);
				z++;
			}
		}

		byte[] cmd = befehl.getBytes();

		byte[] whole = new byte[wordLen + crcHex.length + 1];

		for (int j = 0; j < whole.length - 1; j++) {

			if (j < wordLen) {
				whole[j] = cmd[j];
			} else {
				whole[j] = (byte) crcHex[j - wordLen];
			}
		}

		whole[whole.length - 1] = (byte) 0x0D;
		
		if (whole.length < 8) {
			byte[] resized = new byte[8];
			System.arraycopy(whole, 0, resized, 0, whole.length);
			return resized;
		}

		return whole;
	}

	private static String getCrc16Xmodem(String str) {

		return CRCUtil.getCRC16CCITT(str, 0x1021, 0x0000, false);
	}
}
