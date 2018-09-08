# pipCom
This simple communucation server for PIP devices is aimed for developers which want to gather data from their
solar inverter. It was created for the authors own usage and only tested with his PIP2424MSE.

This application heavily depends on nyholku's purejavahidapi which is used for general HID communication.

Generally it has the follwing structure:

com.Server:
	Server.class: Processes reuests on Port 13000 (change it in the code).
	CommandReceiver: Handles incomming commands, sends them to the solar inverter and sends the response back to the client (Commander).
	
com.client:
	Commander: Client that sends requests to the Server consisting of PIP commands.
	
information:
	Contains some classes that parse the server response and make further processing of the data more easy.
	
An example to get started:

This example gathers the most important actual solar parameters and puts them into an QpigsRow object that parses the responses 
and lets you access its public values for further processing, e.g. putting them into a database.

Commander c = new Commander();
GetCommand v = GetCommands.QPIGS;

byte[] first_response = c.sendCmd(v.cmd());

v = GetCommands.QPIGS2;
byte[] second_response = c.sendCmd(v.cmd());

QpigsRow q = new QpigsRow(first_response, second_response);
//-----------------------------------------------------------------------//

This command sets the maximum charging current in [A] for the battery. The commands are still in German but will be translated when I find the time.

Commander c = new Commander();
SetCommand s = SetCommands.SETZE_MAX_LADESTROM;

byte[] response = c.sendCmd(s.cmd("25"));

System.out.println(new String(response, StandardCharsets.US_ASCII));
//-----------------------------------------------------------------------//

