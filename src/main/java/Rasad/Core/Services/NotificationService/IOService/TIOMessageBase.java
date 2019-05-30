package main.java.Rasad.Core.Services.NotificationService.IOService;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoInclude(1, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TRequestGSMConnect))][ProtoInclude(2, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TRequestGSMDisconnect))][ProtoInclude(3, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TRequestGSMStatus))][ProtoInclude(4, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TResponseGSMStatus))][ProtoInclude(5, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TResponseGSMConnectionMessage))][ProtoInclude(6, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TRequestGSMPorts))][ProtoInclude(7, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TResponseGSMPorts))][ProtoInclude(8, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TRequestSendSMS))][ProtoInclude(9, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TRequestSendUSSD))][ProtoInclude(10, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TResponseSendSMS))][ProtoInclude(11, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TResponseSendUSSD))][ProtoInclude(12, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TGSMSettingsChanged))][ProtoInclude(13, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TRequestCancelUSSD))][ProtoInclude(14, typeof(Rasad.Core.Services.NotificationService.IOService.Mail.TMailSettingsChanged))][ProtoInclude(15, typeof(Rasad.Core.Services.NotificationService.IOService.Mail.TSendMailMessage))][DataContract][ProtoContract] public class TIOMessageBase:TMessageBase
public class TIOMessageBase extends TMessageBase
{
	protected TIOMessageBase()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TIOMessageBase(byte serverID,string clientID)
	public TIOMessageBase(byte serverID, String clientID)
	{
		super(serverID);
		this.setClientID(clientID);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(9999)] public string ClientID {get;private set;}
	private String ClientID;
	public final String getClientID()
	{
		return ClientID;
	}
	private void setClientID(String value)
	{
		ClientID = value;
	}
}