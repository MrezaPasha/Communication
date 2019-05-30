package main.java.Rasad.Core.Services.NotificationService.IOService.Mail;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TMailSettingsChanged : TIOMessageBase
public class TMailSettingsChanged extends TIOMessageBase
{
	private TMailSettingsChanged()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMailSettingsChanged(byte serverID, string clientID)
	public TMailSettingsChanged(byte serverID, String clientID)
	{
		super(serverID, clientID);

	}
}