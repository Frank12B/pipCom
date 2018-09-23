package information;

import com.server.PIPException;

import information.Info;

/**
 * Parses data for device mode inquiry.
 * 
 * @version 1.0
 */
public class PowerModeInfo implements Info {
	
	private String[] values;
	public String powerMode;

	/**
	 * Instantiates an object by taking a valid PIP response.
	 *  
	 * Method parseValues() must always be executed before the values are set!
	 * 
	 * @param result A valid answer from the PIP.
	 */
	public PowerModeInfo(byte[] result) {
		this.values = convertBytes(result);
	}

	@Override
	public void parseValues() throws PIPException {
		
		if (values != null) {
			powerMode = values[0].substring(0,1);
		} else {
			throw new PIPException("Input values of PowerModeInfo null!", 
									this.getClass().getName(), 
									powerMode, values);
		}
	}

}
