package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TGSMSettingsChanged : TIOMessageBase
public class TGSMSettingsChanged extends TIOMessageBase
{
	private TGSMSettingsChanged()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TGSMSettingsChanged(byte serverID, string clientID)
	public TGSMSettingsChanged(byte serverID, String clientID)
	{
		super(serverID, clientID);

	}
}