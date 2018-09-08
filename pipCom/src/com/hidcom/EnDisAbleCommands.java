package com.hidcom;

public enum EnDisAbleCommands implements EnDisAbleCommand {

	ENABLE_SILENCE_BUZZER {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_SILENCE_BUZZER;
		}
	},

	DISABLE_SILENCE_BUZZER {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_SILENCE_BUZZER;
		}
	},

	ENABLE_OVERLOAD_BYPASS {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_OVERLOAD_BYPASS;
		}
	},

	DISABLE_OVERLOAD_BYPASS {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_OVERLOAD_BYPASS;
		}
	},

	ENABLE_POWER_SAVING {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_POWER_SAVING;
		}
	},

	DISABLE_POWER_SAVING {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_POWER_SAVING;
		}
	},

	ENABLE_LCDDISPLAY_RUEKKEHR_STANDARTANZEIGE {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_ESCAPE_TO_DEFAULT_SCREEN;
		}
	},

	DISABLE_LCDDISPLAY_RUEKKEHR_STANDARTANZEIGE {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_ESCAPE_TO_DEFAULT_SCREEN;

		}
	},

	ENABLE_UEBERLAST_NEUSTART {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_OVERLOAD_RESTART;
		}
	},

	DISABLE_UEBERLAST_NEUSTART {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_OVERLOAD_RESTART;

		}
	},

	ENABLE_UEBERHITZUMG_NEUSTART {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_OVERLOAD_RESTART;
		}
	},

	DISABLE_UEBERHITZUMG_NEUSTART {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_OVERLOAD_RESTART;

		}
	},

	ENABLE_BACKLIGHT {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_BACKLIGHT;
		}
	},

	DISABLE_BACKLIGHT {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_BACKLIGHT;

		}
	},

	ENABLE_ALARM_ON_WHEN_PRIMARY_SOURCE_INTERRUPT {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_INTERRUPT_ALARM;
		}
	},

	DISABLE_ALARM_ON_WHEN_PRIMARY_SOURCE_INTERRUPT {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_INTERRUPT_ALARM;

		}
	},

	ENABLE_FAULT_CODE_RECORD {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_FAULT_CODE_RECORD;
		}
	},

	DISABLE_FAULT_CODE_RECORD {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_FAULT_CODE_RECORD;

		}
	},

	ENABLE_DATA_LOG_POPUP {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_ENABLE_PREFIX + PipCommandGenerator.SET_FAULT_CODE_RECORD;
		}
	},

	DISABLE_DATA_LOG_POPUP {

		@Override
		public String cmd() {

			return PipCommandGenerator.SET_DISABLE_PREFIX + PipCommandGenerator.SET_FAULT_CODE_RECORD;

		}
	};
}
