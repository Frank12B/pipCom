package information;

/**
 * Parses data for setting parameters command.
 * 
 * @version 1.0 *
 */
public class DeviceOptionInfo implements Info {

	public boolean ENABLED_SILENCE_BUZZER = false;;
	public boolean ENABLED_OVERLOAD_BYPASS = false;
	public boolean ENABLED_POWER_SAVING = false;
	public boolean ENABLED_LCD_DISPLAY_ESCAPE_TO_DEFAULT_PAGE = false;
	public boolean ENABLED_OVERLOAD_RESTART = false;
	public boolean ENABLED_OVER_TEMP_RESTART = false;
	public boolean ENABLED_BACKLIGHT = false;
	public boolean ENABLED_ALARM_ON_WHEN_PRIMARY_SOURCE_DISCONNECT = false;
	public boolean ENABLED_FAULT_CODE_RECORD = false;
	public boolean ENABLED_DATA_LOG_POPUP = false;

	private String[] values;

	/**
	 * Instantiates an object by taking a valid PIP response.
	 * Method parseValues() must always be executed before the values are set!
	 * 
	 * @param result A valid answer from the PIP.
	 */
	public DeviceOptionInfo(byte[] result) {
		this.values = convertBytes(result);
	}

	/* (non-Javadoc)
	 * @see information.Info#parseValues()
	 */
	public void parseValues() {
		String enabled = values[0].substring(0, values[0].indexOf((char) 13) - 2);

		for (int x = 0; x < enabled.length(); x++) {
			switch (enabled.charAt(x)) {

			case 'a':
				this.ENABLED_SILENCE_BUZZER = true;
				break;

			case 'b':
				this.ENABLED_OVERLOAD_BYPASS = true;
				break;

			case 'j':
				this.ENABLED_POWER_SAVING = true;
				break;

			case 'k':
				this.ENABLED_LCD_DISPLAY_ESCAPE_TO_DEFAULT_PAGE = true;
				break;

			case 'u':
				this.ENABLED_OVERLOAD_RESTART = true;
				break;

			case 'v':
				this.ENABLED_OVER_TEMP_RESTART = true;
				break;

			case 'x':
				this.ENABLED_BACKLIGHT = true;
				break;

			case 'y':
				this.ENABLED_ALARM_ON_WHEN_PRIMARY_SOURCE_DISCONNECT = true;
				break;

			case 'z':
				this.ENABLED_FAULT_CODE_RECORD = true;
				break;

			case 'l':
				this.ENABLED_DATA_LOG_POPUP = true;
				break;
			}
		}
	}

}
