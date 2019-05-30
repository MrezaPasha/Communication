package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import Rasad.Core.Services.IOService.TGSMModemStatus;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TResponseGSMStatus : TIOMessageBase
public class TResponseGSMStatus extends TIOMessageBase
{
	private TResponseGSMStatus()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseGSMStatus(byte serverID,string clientID,TGSMModemStatus status)
	public TResponseGSMStatus(byte serverID, String clientID, Rasad.Core.Services.IOService.TGSMModemStatus status)
	{
		super(serverID, clientID);
		setModemStatus(status);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public TGSMModemStatus ModemStatus {get;private set;}
	private Rasad.Core.Services.IOService.TGSMModemStatus ModemStatus = TGSMModemStatus.values()[0];
	public final TGSMModemStatus getModemStatus()
	{
		return ModemStatus;
	}
	private void setModemStatus(TGSMModemStatus value)
	{
		ModemStatus = value;
	}
}