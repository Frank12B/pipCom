package com.hidcom;

public enum GetCommands implements GetCommand {

	STATUSWERTE {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.DEVICE_GENERAL_STATUS_PARAMETER);
		}
	},

	STATUSWERTE2 {

		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.DEVICE_GENERAL_STATUS_PARAMETER2);
		}
	},

	ANZEIGE_BETRIEBSKONSTANTEN {

		@Override
		public byte[] cmd() {
			return PipCommandGenerator.createBefehl(PipCommandGenerator.DEVICE_INFO);
		}

		
	},

	ANZEIGE_GERAET_EINSTELLUNGEN {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.DEVICE_FLAG_STATUS);
		}
	},

	ANZEIGE_GERAETE_MODI {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.DEVICE_MODE_STATUS);
		}
	},

	ANZEIGE_GERAET_WARNUNG_STATUS {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.DEVICE_WARNING_STATUS);
		}
	},

	ANZEIGE_STANDARD_EINSTELLUNGSWERTE {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.DEVICE_DEFAULT_SETTING);
		}
	},

	ANZEIGE_MOEGLICHE_WERTE_MAX_LADESTROM {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SELECTABLE_VALUES_MAX_CHARGE_CURRENT);
		}
	},

	ANZEIGE_MOEGLICHE_WERTE_MAX_UTILITY_LADESTROM {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SELECTABLE_VALUES_MAX_UTILITY_CHARGE_CURRENT);
		}
	},

	ANZEIGE_MOEGLICHE_WERTE_MAX_SOLAR_LADESTROM {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.SELECTABLE_VALUES_MAX_SOLAR_CHARGE_CURRENT);
		}
	},

	ANZEIGE_DISPLAY_BOOTSTRAP {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.ASK_IF_DEVICE_HAS_BOOTSTRAP);
		}
	},

	ANZEIGE_LADESTUFEN {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.ASK_CHARGING_STAGE);
		}
	},

	ANZEIGE_ZEIT_AUSGLEICHSLADUNG {

		@Override
		public byte[] cmd() {

			return PipCommandGenerator.createBefehl(PipCommandGenerator.ASK_CV_CHARGING_TIME);
		}
	};
}
