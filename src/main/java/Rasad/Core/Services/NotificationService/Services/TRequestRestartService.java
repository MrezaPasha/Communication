package main.java.Rasad.Core.Services.NotificationService.Services;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceType))][DataContract][ProtoContract] public class TRequestRestartService : TMessageBase
public class TRequestRestartService extends TMessageBase
{
	private TRequestRestartService()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestRestartService(byte serverID, Guid[] serviceKeys)
	public TRequestRestartService(byte serverID, UUID[] serviceKeys)
	{
		super(serverID);
		this.setServerID(serverID);
		this.setServiceKeys(serviceKeys);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid[] ServiceKeys {get;set;}
	private UUID[] ServiceKeys;
	public final UUID[] getServiceKeys()
	{
		return ServiceKeys;
	}
	public final void setServiceKeys(UUID[] value)
	{
		ServiceKeys = value;
	}

	@Override
	public String toString()
	{
		return String.format("Server ID : %1$s - Restart multiple services", getServerID());
	}
}