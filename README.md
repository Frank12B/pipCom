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

First of all the Server must run on the machine the PIP is connected to via USB cable and recognized as a HID device. Then you need to find out its PRODUCT_ID and VENDOR_ID. These values are preset for the PIP2424MSE in the code. Change this to your values. If you do not know how to get these values you can iterate over all connected HID devices with purejavahidapi. You can find an example in following the link above. It is explained by an example in the README.

If you found out your IDs change it in the pipCom/src/com/Server/Server.java file as shown below:

```java
/**
* IDs to identify your PIP inverter as HID device. In this case it is am PIP2424MSE
*/	
private static final int VENDOR_ID = (short) 0x0665;
private static final int PRODUCT_ID = (short) 0x5161;
```

Before exporting the project as jarfile you can configure the logger in the Server.java file (this is the "rootlogger" of the project) to define some textfiles or suppress the ConsoleLogger. How this is to be done you can be found in the internet. At the moment the communication port of the server is hardcoded as 13000. You have to change it in the code if you want to change it.

After that export your project as jar file (by using ther 'Server' class main method) and execute it. You need to run it with 'sudo' on linux machines in order to be able to read the HID device. If this is done and no Exception appears you can use the Commander class to send commands to the Server as you can see in the examples below:

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
