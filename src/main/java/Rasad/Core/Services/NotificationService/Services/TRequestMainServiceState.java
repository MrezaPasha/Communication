package main.java.Rasad.Core.Services.NotificationService.Services;


import main.java.Rasad.Core.Services.NotificationService.TMessageRequest;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestMainServiceState : TMessageRequest
public class TRequestMainServiceState extends TMessageRequest
{
	private TRequestMainServiceState()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestMainServiceState(byte serverID)
	public TRequestMainServiceState(byte serverID)
	{
		this.setServerID(serverID);
		this.setFederationRouted(false);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public byte ServerID {get;set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte ServerID;
	private byte ServerID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getServerID()
	public final byte getServerID()
	{
		return ServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setServerID(byte value)
	public final void setServerID(byte value)
	{
		ServerID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public bool FederationRouted {get;set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	public final void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ResetServerID(byte newServerID)
	public final void ResetServerID(byte newServerID)
	{
		this.setServerID(newServerID);
		this.setFederationRouted(true);
	}
}