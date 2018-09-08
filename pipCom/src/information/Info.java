package information;

import com.server.PIPException;

public interface Info {

	void parseValues() throws PIPException;
	
	default String[] convertBytes(byte[] response) {
		String[] x = new String[50];
		String h = "";
		int i = 0;
		char v;
		
		for (int u = 1; u < response.length; u++) {
			v = ((char) ((int) response[u]));
			if (v != ' ') {
				h+= ((char) ((int) response[u]));					
			} else {
				x[i] = h;
				i++;
				h = "";
			}						
		}
		
		x[i] = h;
		
		return x;			
	}
}
