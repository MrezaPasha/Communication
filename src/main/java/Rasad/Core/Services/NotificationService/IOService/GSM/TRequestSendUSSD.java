package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestSendUSSD : TIOMessageBase
public class TRequestSendUSSD extends TIOMessageBase
{
	private TRequestSendUSSD()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestSendUSSD(byte serverID, string clientID, string ussdCommand, string dcs)
	public TRequestSendUSSD(byte serverID, String clientID, String ussdCommand, String dcs)
	{
		super(serverID, clientID);
		setUSSDCommand(ussdCommand);
		setDCS(dcs);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string USSDCommand {get;private set;}
	private String USSDCommand;
	public final String getUSSDCommand()
	{
		return USSDCommand;
	}
	private void setUSSDCommand(String value)
	{
		USSDCommand = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string DCS {get;private set;}
	private String DCS;
	public final String getDCS()
	{
		return DCS;
	}
	private void setDCS(String value)
	{
		DCS = value;
	}
}