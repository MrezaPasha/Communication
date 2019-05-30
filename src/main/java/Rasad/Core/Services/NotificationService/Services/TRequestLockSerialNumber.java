package main.java.Rasad.Core.Services.NotificationService.Services;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoContract][DataContract] public class TRequestLockSerialNumber: TMessageBase
public class TRequestLockSerialNumber extends TMessageBase
{
	private TRequestLockSerialNumber()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestLockSerialNumber(byte serverID)
	public TRequestLockSerialNumber(byte serverID)
	{
		super(serverID);
		this.setFederationRouted(false);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ResetServerID(byte newServerID)
	public final void ResetServerID(byte newServerID)
	{
		this.set_ServerID(newServerID);
		this.setFederationRouted(true);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public bool FederationRouted {get;private set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}
}