package information;

import com.server.PIPException;

import information.Info;

/**
 * @version 1.0
 *
 */
public class PowerModeInfo implements Info {
	
	private String[] values;
	public String powerMode;

	public PowerModeInfo(byte[] ergebnis) {
		this.values = convertBytes(ergebnis);
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
