package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestGSMConnect : TIOMessageBase
public class TRequestGSMConnect extends TIOMessageBase
{
	private TRequestGSMConnect()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestGSMConnect(byte serverID, string clientID)
	public TRequestGSMConnect(byte serverID, String clientID)
	{
		super(serverID, clientID);

	}
}