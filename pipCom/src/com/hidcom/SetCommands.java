package com.hidcom;

public enum SetCommands implements SetCommand {

	SETZE_KONTROLL_PARAMETER_AUF_STANDART {

		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_CONTROL_PARAMS_DEFAULT);
		}
	},

	SETZE_MAX_LADESTROM {

		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_MAX_CHARGING_CURRENT + wert);
		}
	},

	SETZE_MAX_LADESTROM_GROESSER_100A {

		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_MAX_CHARGING_CURRENT_OVER_100 + wert);
		}
	},

	SETZE_MAX_UTILITY_LADESTROM {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_MAX_UTILITY_CHARGING_CURRENT + wert);
		}
	},

	SETZE_MAX_SOLAR_LADESTROM {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_MAX_SOLAR_CHARGING_CURRENT + wert);
		}
	},

	SETZE_GERAET_AUSGANGS_STROMFREQUENZ_60Hz {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_OUTPUT_RATING_FREQ + "60");
		}
	},

	SETZE_GERAET_AUSGANGS_STROMFREQUENZ_50Hz {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_OUTPUT_RATING_FREQ + "50");
		}
	},

	SETZE_GERAET_AUSGANGS_STROMQUELLEN_PRIORITAET_UTILITY_FIRST {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_OUTPUT_SOURCE_PRIORITY + "00");
		}
	},

	SETZE_GERAET_AUSGANGS_STROMQUELLEN_PRIORITAET_SOLAR_FIRST {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_OUTPUT_SOURCE_PRIORITY + "01");
		}
	},

	SETZE_GERAET_AUSGANGS_STROMQUELLEN_PRIORITAET_SBU {
		@Override
		public byte[] cmd(String wert) {
			
			byte[] befehl_b = (PipCommandGenerator.SET_DEVICE_OUTPUT_SOURCE_PRIORITY + "02").getBytes();
			byte[] befehl_b2 = new byte[befehl_b.length + 3];
			
			for (int z = 0; z < befehl_b.length; z++) {
				befehl_b2[z] = befehl_b[z];
			}
			
			befehl_b2[befehl_b.length] = (byte) 0xE2;
			befehl_b2[befehl_b.length + 1] = (byte) 0x0B;
			befehl_b2[befehl_b.length + 2] = (byte) 13;

			return befehl_b2;
		}
	},

	SETZE_BATTERIE_ENTLADUNGS_SCHLUSSSPANNUNG {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_RECHARGE_VOLTAGE + wert);
		}
	},

	SETZE_BATTERIE_ENTLADUNGSBEGINN_SPANNUNG {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_REDISCHARGE_VOLTAGE + wert);
		}
	},
	
	Setze_SPANNUNG_RUEKKEHR_NETZ {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_RECHARGE_VOLTAGE + wert);
		}
	},

	SETZE_GERAET_LADUNGSQUELLEN_PRIORITAET_UTILITY_FIRST {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_CHARGER_PRIORITY + "00");
		}
	},

	SETZE_GERAET_LADUNGSQUELLEN_PRIORITAET_SOLAR_FIRST {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_CHARGER_PRIORITY + "01");
		}
	},

	SETZE_GERAET_LADUNGSQUELLEN_PRIORITAET_SOLAR_AND_UTILITY {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_CHARGER_PRIORITY + "02");
		}
	},

	SETZE_GERAET_LADUNGSQUELLEN_PRIORITAET_SOLAR_ONLY {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_CHARGER_PRIORITY + "03");
		}
	},

	SETZE_GERAET_ANWENDUNG_UPS {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_GRID_WORKING_RANGE + "01");
		}
	},

	SETZE_GERAET_ANWENDUNG_NORMAL {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_DEVICE_GRID_WORKING_RANGE + "00");
		}
	},

	SETZE_BATTERIE_TYP_AGM {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_TYPE + "00");
		}
	},

	SETZE_BATTERIE_TYP_FLOODED {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_TYPE + "01");
		}
	},
	/**
	 * Setze_BATTERIE_TYP_USER {
	 * 
	 * @Override public byte[] cmd(String wert) {
	 * 
	 *           return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_TYPE + "02"); }
	 *           
	 */

	SETZE_BATTERIE_SCHLUSSSPANNUNG {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_CUTOFF_VOLTAGE + wert);
		}
	},

	SETZE_BATTERIE_AUSGLEICHS_LADESPANNUNG {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_CONSTANT_VOLTAGE_CHARGING_VOLTAGE + wert);
		}
	},

	SETZE_BATTERIE_ERHALTUNGS_LADESPANNUNG {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_BATTERY_FLOAT_CHARGING_VOLTAGE + wert);
		}
	},

	SETZE_BATTERIE_LADE_STUFEN_2 {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_CHARGING_STAGE + "2");
		}
	},

	SETZE_BATTERIE_LADE_STUFEN_3 {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_CHARGING_STAGE + "3");
		}
	},

	SETZE_BATTERIE_KONSTANTE_SPANNUNG_LADEZEIT {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_CONSTANT_VOLTAGE_CHARGING_TIME + wert);
		}
	},

	SETZE_PV_OK_BEDINGUNG_1_OK {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_PV_OK_CONDITION + "0");
		}
	},

	SETZE_PV_OK_BEDINGUNG_ALLE_OK {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_PV_OK_CONDITION + "1");
		}
	},

	SETZE_SOLAR_LEISTUNGS_BALANCE_LADEN {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_SOLAR_POWER_BALANCE + "0");
		}
	},

	SETZE_SOLAR_LEISTUNGS_BALANCE_LADEN_UND_LAST {
		@Override
		public byte[] cmd(String wert) {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SET_SOLAR_POWER_BALANCE + "1");
		}
	};
}
