package information;

import java.util.Arrays;

import com.server.PIPException;
import com.sun.istack.internal.logging.Logger;

/**
 * @version 1.0
 *
 */
public class DeviceSettingInfo implements Info {

	public float GRID_RATING_VOLTAGE;
	public float GRID_RATING_CURRENT;
	public float AC_OUTPUT_RATING_VOLTAGE;
	public float AC_OUTPUT_RATING_FREQUENCY;
	public float AC_OUTPUT_RATING_CURRENT;
	public int AC_OUTPUT_RATING_APPARENT_POWER;
	public int AC_OUTPUT_RATING_ACTIVE_POWER;
	public float BATTERY_RATING_VOLTAGE;
	public float BATTERY_RECHARGING_VOLTAGE;
	public float BATTERY_UNDER_VOLTAGE;
	public float BATTERY_BULK_VOLTAGE;
	public float BATTERY_FLOAT_VOLTAGE;
	/**
	 * 0:AGM 1:FLOODED 2:USER
	 */
	public int BATTERY_TYPE;
	public int AC_MAX_CHARGING_CURRENT;
	public int DEVICE_MAX_CHARGING_CURRENT;
	/**
	 * 0:APPLIANCE 1:UPS
	 */
	public int AC_INPUT_VOLTAGE_RANGE;
	/**
	 * 0:UTILITY FIRST 1:SOLAR FIRST 2:SBU
	 */
	public int DEVICE_OUTPUT_SOURCE_PRIORITY;
	/**
	 * 0:UTILITY FIRST 1:SOLAR FIRST 2:SOLAR + UTILITY 3:SOLAR ONLY
	 */
	public int BATTERY_CHARGING_SOURCE_PRIORITY;
	/**
	 * For KS and Plus Duo Series:   
	 * 0: Utility first 1: Solar first 2: Solar + Utility 3: Only solar charging 
	 * permitted 
	 * 
	 * For MKS Series 1K~3K: 0: Utility first 1: Solar first 2: Solar + Utility 
	 * 3: Only solar charging permitted 
	 */
	public int DEVICE_PARALLEL_MAX_NUM;
	/**
	 * 00:GRID TIE 01:OFF GRID 10:HYBRID 11:OFF GRID WITH 2 TRACKERS 20:OFF GRID
	 * WITH 3 TRACKERS
	 */
	public int DEVICE_MACHINE_TYPE;
	/**
	 * 0:TRANSFORMERLESS 1:TRANSFORMER
	 */
	public int DEVICE_TOPOLOGY;
	/**
	 * 00:SINGLE MACHINE OUTPUT 01:PATALLEL OUTPUT 02:PHASE 1/3 OUTPUT, 03:PHASE 2/3
	 * OUTPUT 04: PHASE 3/3 OUTPUT
	 */
	
	public float BATTERY_REDISCHARGE_VOLTAGE;
	/**
	 * 0:As long as one unit of inverters has connect PV, parallel system will
	 * consider PV OK 1:Only if all of inverters have conected PV, parallel system
	 * will consider PV OK
	 */
	
	public int DEVICE_OUTPUT_MODE;
	/**
	 * Only for 'KS' and 'MKS' 4k-5k:
	 * 
	 * 00: single machine output 01: parallel output 02: Phase 1 of 3 Phase output 
	 * 03: Phase 2 of 3 Phase output 04: Phase 3 of 3 Phase output 
	 */
	public int DEVICE_PV_OK_CONDITION_FOR_PARALLEL;
	/**
	 * 0:PV input max current will be the max charged current 1:PV input max power
	 * will be the sum of the max charged power and loads power
	 */
	public int DEVICE_PV_POWER_BALANCE;

	private String[] input;
	private static Logger logger = Logger.getLogger("information", DeviceSettingInfo.class);

	public DeviceSettingInfo(byte[] input) {
		this.input = convertBytes(input);
		logger.finest("Input: " + Arrays.toString(input));
	}


	public void parseValues() throws PIPException{

		for (int z = 0; z < 25; z++) {
			
			try {

			switch (z) {
			case 0:
				GRID_RATING_VOLTAGE = Float.parseFloat(input[z]);
				break;

			case 1:
				GRID_RATING_CURRENT = Float.parseFloat(input[z]);
				break;

			case 2:
				AC_OUTPUT_RATING_VOLTAGE = Float.parseFloat(input[z]);
				break;

			case 3:
				AC_OUTPUT_RATING_FREQUENCY = Float.parseFloat(input[z]);
				break;

			case 4:
				AC_OUTPUT_RATING_CURRENT = Float.parseFloat(input[z]);
				break;

			case 5:
				AC_OUTPUT_RATING_APPARENT_POWER = Integer.parseInt(input[z]);
				break;

			case 6:
				AC_OUTPUT_RATING_ACTIVE_POWER = Integer.parseInt(input[z]);
				break;

			case 7:
				BATTERY_RATING_VOLTAGE = Float.parseFloat(input[z]);
				break;

			case 8:
				BATTERY_RECHARGING_VOLTAGE = Float.parseFloat(input[z]);
				break;

			case 9:
				BATTERY_UNDER_VOLTAGE = Float.parseFloat(input[z]);
				break;

			case 10:
				BATTERY_BULK_VOLTAGE = Float.parseFloat(input[z]);
				break;

			case 11:
				BATTERY_FLOAT_VOLTAGE = Float.parseFloat(input[z]);
				break;

			case 12:
				BATTERY_TYPE = Integer.parseInt(input[z]);
				break;

			case 13:
				AC_MAX_CHARGING_CURRENT = Integer.parseInt(input[z]);
				break;

			case 14:
				DEVICE_MAX_CHARGING_CURRENT = Integer.parseInt(input[z]);
				break;

			case 15:
				AC_INPUT_VOLTAGE_RANGE = Integer.parseInt(input[z]);
				break;

			case 16:
				DEVICE_OUTPUT_SOURCE_PRIORITY = Integer.parseInt(input[z]);
				break;

			case 17:
				BATTERY_CHARGING_SOURCE_PRIORITY = Integer.parseInt(input[z]);
				break;

			case 18:
				DEVICE_PARALLEL_MAX_NUM = Integer.parseInt(input[z]);
				break;

			case 19:
				DEVICE_MACHINE_TYPE = Integer.parseInt(input[z]);
				break;

			case 20:
				DEVICE_TOPOLOGY = Integer.parseInt(input[z]);
				break;

			case 21:
				DEVICE_OUTPUT_MODE = Integer.parseInt(input[z]); 
				break;

			case 22:
				BATTERY_REDISCHARGE_VOLTAGE = Float.parseFloat(input[z]);
				break;

			case 23:
				DEVICE_PV_OK_CONDITION_FOR_PARALLEL = Integer.parseInt(input[z]);
				break;

			case 24:
				DEVICE_PV_POWER_BALANCE = Integer.parseInt(input[z]);
				break;

			default:
				logger.severe(
						"Something strange happended in parsing information values! Index " + z + " is not available.");
				break;
			}
			
			} catch (NumberFormatException ex) {
				throw new PIPException("Invalid input received!", this.getClass().getName(), input[z], input);
			}
		}
	}
}
