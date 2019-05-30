package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoContract][DataContract] public class TResponseLockSerialNumber: TMessageBase
public class TResponseLockSerialNumber extends TMessageBase
{
	private TResponseLockSerialNumber()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseLockSerialNumber(byte serverID,string serialNumber)
	public TResponseLockSerialNumber(byte serverID, String serialNumber)
	{
		super(serverID);
		this.setSerialNumber(serialNumber);
		this.setFederationRouted(false);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string SerialNumber {get;private set;}
	private String SerialNumber;
	public final String getSerialNumber()
	{
		return SerialNumber;
	}
	private void setSerialNumber(String value)
	{
		SerialNumber = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public bool FederationRouted {get;private set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ResetServerID(byte serverID)
	public final void ResetServerID(byte serverID)
	{
		this.set_ServerID(serverID);
		this.setFederationRouted(true);
	}
}