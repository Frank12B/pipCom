package information;

import com.server.PIPException;

public class QpigsRow implements Info {
	
	public float grid_V;
	public float grid_F;
	public float ac_Out_V;
	public float ac_Out_F;
	public int ac_Out_Apparent_P;
	public int ac_Out_Active_P;
	public int output_Load_Percent;
	public int bus_V;
	public float battery_V;
	public int battery_Charging_C;
	public int battery_C;
	public int invert_Heat_Sink_T;
	public int pv1_Input_C;
	public float pv1_Input_V;
	public float SCC1_Battery_V;
	public int battery_Discharge_C;
	public int battery_V_Offset_Fans_On;
	public int EEPROM_Version;
	public boolean device_sbu_option_added;
	public boolean device_configuration_status_changed;
	public boolean device_fw_version_updated;
	public boolean device_load_on;
	public boolean device_battery_charging_steady;
	public int device_battery_charing_status;
	public int pv1_Charging_P;
	public int device_Status;
	public int pv2_Input_C;
	public float pv2_Input_V;
	public float SCC2_Battery_V;
	public int pv2_Charging_P;
	public boolean SCC2_Charging_Status_On;
	public boolean SCC3_Charging_Status_On; // Folgende 5 bits reserved
	public int ac_Charging_C;
	public int ac_Charging_P;
	/**
	 * private float pv3_Input_C; private float pv3_Input_V; private float
	 * SCC3_Battery_V; private int pv3_Charging_P; private int pv_Total_Charging_P;
	 */

	private String[] qpigs;
	private String[] qpigs2;
 
	public QpigsRow(byte[] qpigs, byte[] qpigs2) {

		this.qpigs = convertBytes(qpigs);
		this.qpigs2 = convertBytes(qpigs2);
	}
	
	public void parseValues() throws PIPException {
		
		try {
			getGrid_V();
			getGrid_F();
			getAc_Out_V();
			getAc_Out_F();
			getAc_Out_App_P();
			getAc_Out_Act_P();
			getOut_Load_Percent();
			getBus_V();
			getBat_V();
			getBatCharge_C();
			getBat_C();
			getInv_Heat_Sink();
			getPv1_C();
			getPv1_V();
			getSCC1_Bat_V();
			getBat_Dis_C();
			getDeviceStati();
			getBatVOffsetFans();
			getEEPROMVersion();
			getPV1ChargeP();
			getDeviceStatus();
			getPV2InputC();
			getpv2InputV();
			getSCC2BatV();
			getPV2ChargeP();
			getSCC2ChargingOn();
			getSCC3ChargingOn();
			getACChargeC();
			getACChargeP();
			// getPV3InputC();
			// getPV3InputV();
			// getSCC3BatInputV();
			// getPV3ChargeP();
			// getPVTotalChargeP();
		} catch (NumberFormatException ex) {
			throw new PIPException("Falscher numerischer Inputstring!");
		}		
	}

	private void getGrid_V() {

		grid_V = Float.parseFloat(qpigs[0]);
	}

	private void getGrid_F() {

		grid_F = Float.parseFloat(qpigs[1]);
	}

	private void getAc_Out_V() {

		ac_Out_V = Float.parseFloat(qpigs[2]);
	}

	private void getAc_Out_F() {

		ac_Out_F = Float.parseFloat(qpigs[3]);
	}

	private void getAc_Out_App_P() {

		ac_Out_Apparent_P = Integer.parseInt(qpigs[4]);
	}

	private void getAc_Out_Act_P() {

		ac_Out_Active_P = Integer.parseInt(qpigs[5]);
	}

	private void getOut_Load_Percent() {

		output_Load_Percent = Integer.parseInt(qpigs[6]);
	}

	private void getBus_V() {

		bus_V = Integer.parseInt(qpigs[7]);
	}

	private void getBat_V() {

		battery_V = Float.parseFloat(qpigs[8]);
	}

	private void getBatCharge_C() {

		battery_Charging_C = Integer.parseInt(qpigs[9]);
	}

	private void getBat_C() {

		battery_C = Integer.parseInt(qpigs[10]);
	}

	private void getInv_Heat_Sink() {

		invert_Heat_Sink_T = Integer.parseInt(qpigs[11]);
	}

	private void getPv1_C() {

		pv1_Input_C = Integer.parseInt(qpigs[12]);
	}

	private void getPv1_V() {

		pv1_Input_V = Float.parseFloat(qpigs[13]);
	}

	private void getSCC1_Bat_V() {

		SCC1_Battery_V = Float.parseFloat(qpigs[14]);
	}

	private void getBat_Dis_C() {

		battery_Discharge_C = Integer.parseInt(qpigs[15]);
	}
	
	private void getDeviceStati() {
		//result[25] = device_Status;
		final String bytes = qpigs[16];
		//sbuPriority version; 1=>yes,0=>no
		device_sbu_option_added = Boolean.parseBoolean(bytes.substring(0, 1));
		//configuration status: 1: Change 0: unchanged
		device_configuration_status_changed = Boolean.parseBoolean(bytes.substring(1, 2));
		//SCC firmware version 1: Updated 0: unchanged
		device_fw_version_updated = Boolean.parseBoolean(bytes.substring(2, 3));
		//Load status: 0: Load off 1:Load on
		device_load_on = Boolean.parseBoolean(bytes.substring(3, 4));
		//battery voltage to steady	while charging
		device_battery_charging_steady = Boolean.parseBoolean(bytes.substring(4, 5));
		
		//000: Do nothing 
		//110: Charging on with SCC1 charge on 
		//101: Charging on with AC charge on
		//111: Charging on with SCC1
		device_battery_charing_status = Integer.parseInt(bytes.substring(5, 7));
	}

	private void getBatVOffsetFans() {

		battery_V_Offset_Fans_On = Integer.parseInt(qpigs[17]);
	}

	private void getEEPROMVersion() {

		EEPROM_Version = Integer.parseInt(qpigs[18]);
	}

	private void getPV1ChargeP() {

		pv1_Charging_P = Integer.parseInt(qpigs[19]);
	}

	private void getDeviceStatus() {

		device_Status = Integer.parseInt(qpigs[20].substring(0, 3));
	}

	// -----------------QPIGS2-----------------------------------------//

	private void getPV2InputC() {

		pv2_Input_C = Integer.parseInt(qpigs2[0]);
	}

	private void getpv2InputV() {

		pv2_Input_V = Float.parseFloat(qpigs2[1]);
	}

	private void getSCC2BatV() {

		SCC2_Battery_V = Float.parseFloat(qpigs2[2]);
	}

	private void getPV2ChargeP() {

		pv2_Charging_P = Integer.parseInt(qpigs2[3]);
	}

	private void getSCC2ChargingOn() {

		SCC2_Charging_Status_On = Boolean.parseBoolean(qpigs2[4].substring(0, 1));
	}

	private void getSCC3ChargingOn() {

		SCC3_Charging_Status_On = Boolean.parseBoolean(qpigs2[4].substring(1, 2));
	}

	private void getACChargeC() {

		ac_Charging_C = Integer.parseInt(qpigs2[5]);
	}

	private void getACChargeP() {

		ac_Charging_P = Integer.parseInt(qpigs2[6].substring(0, 4));
	}

	/**
	 * private void getPV3InputC(){
	 * 
	 * pv3_Input_C = Float.parseFloat(getCharRange2(40, 43)); result[34] =
	 * pv3_Input_C; }
	 * 
	 * private void getPV3InputV(){
	 * 
	 * pv3_Input_V = Float.parseFloat(getCharRange2(45, 49)); result[35] =
	 * pv3_Input_V; }
	 * 
	 * private void getSCC3BatInputV(){
	 * 
	 * SCC3_Battery_V = Float.parseFloat(getCharRange2(51, 55)); result[36] =
	 * SCC3_Battery_V; }
	 * 
	 * private void getPV3ChargeP(){
	 * 
	 * pv3_Charging_P = Integer.parseInt(getCharRange2(56, 59)); result[37] =
	 * pv3_Charging_P; }
	 * 
	 * private void getPVTotalChargeP(){
	 * 
	 * pv_Total_Charging_P = Integer.parseInt(getCharRange2(61, 65)); result[38]
	 * = pv_Total_Charging_P; }
	 */
}
