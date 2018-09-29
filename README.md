# pipCom
This simple communication server for PIP devices is aimed for developers who want to gather data from their PIP
solar inverter. It was created for the authors own usage and only tested with his PIP2424MSE.

This application heavily depends on [nyholku's purejavahidapi](https://github.com/nyholku/purejavahidapi) which is used for general HID communication.

Dependencies:
JNA.jar (Tested version 4.40, dependency of purejavahidapi)
purejavahidapi.jar (see link above)


Generally it has the follwing structure:

* com.Server:
	* Server class: Processes reuests on Port 13000 (change it in the code).
	* CommandReceiver class: Handles incomming commands, sends them to the solar inverter and sends the response back to the client 		  (Commander).
	
* com.client:
	* Commander class: Client that sends requests to the Server consisting of PIP commands.
	
* com.hidcom:
	* Get/SetCommands: Commands that can be sent to the PIP. You can find an example usage below.
	
* information:
	* Contains some classes that parse the server response and make further processing of the data more easy.
	
The server runs on a raspberryPi 3 which is connected via USB cable to the connector. I wrote an additional background service that is collecting data and writes them to the database and a Ruby On Rails application uses it.

I am using an intervall of 30s to send a bunch of requests to the inverter but measurements showed that much lower intervalls are possible (around 2s and 4 requests). 
	
### Some examples to get started:

This example gathers the most important actual solar parameters and puts them into an QpigsRow object that parses the responses 
and lets you access its public values for further processing, e.g. putting them into a database.
```java
public static void main(String[] args) {
	Commander c = new Commander("raspberrypi", 13000);
	GetCommand v = GetCommands.QPIGS;

	byte[] first_response = c.sendCmd(v.cmd());

	v = GetCommands.QPIGS2;
	byte[] second_response = c.sendCmd(v.cmd());

	QpigsRow q = new QpigsRow(first_response, second_response);
	try {
		q.parseValues();

		System.out.println(q.battery_Charging_C);
		System.out.println(q.pv1_Charging_P);
		System.out.println(q.pv2_Charging_P);

	} catch (PIPException e) {
		System.out.println("Wrong input parameters received from PIP!");
		e.printStackTrace();
	}
}
```
This command sets the maximum charging current in [A] for the battery. The commands are still in German but will be translated when I find the time.
```java
Commander c = new Commander();
SetCommand s = SetCommands.SETZE_MAX_LADESTROM;

byte[] response = c.sendCmd(s.cmd("25"));

System.out.println(new String(response, StandardCharsets.US_ASCII));

```
If a SetCommand was sent the server responses with 'NACK' (Not acknowleged) if the command failed or or (ACK) (Acknowledged) after successfull execution. This message can be decoded in using a String constructor using the ASCII Standard Charset.
