package main.java.Rasad.Core.Services.NotificationService.Services;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceType))][DataContract][ProtoContract] public class TRequestInstallService : TMessageBase
public class TRequestInstallService extends TMessageBase
{
	private TRequestInstallService()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestInstallService(byte serverID, string serviceName)
	public TRequestInstallService(byte serverID, String serviceName)
	{
		super(serverID);
		setServerID(serverID);
		setServiceName(serviceName);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestInstallService(byte serverID, Nullable<Guid> pluginWindowsServiceKey)
	public TRequestInstallService(byte serverID, UUID pluginWindowsServiceKey)
	{
		super(serverID);
		this.setServerID(serverID);
		this.setPluginWindowsServiceKey(pluginWindowsServiceKey);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string ServiceName {get;set;}
	private String ServiceName;
	public final String getServiceName()
	{
		return ServiceName;
	}
	public final void setServiceName(String value)
	{
		ServiceName = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public byte ServerID {get;set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public Nullable<Guid> PluginWindowsServiceKey {get;set;}
	private UUID PluginWindowsServiceKey = null;
	public final UUID getPluginWindowsServiceKey()
	{
		return PluginWindowsServiceKey;
	}
	public final void setPluginWindowsServiceKey(UUID value)
	{
		PluginWindowsServiceKey = value;
	}

	public final boolean getIsPluginService()
	{
		return getPluginWindowsServiceKey() != null;
	}
	@Override
	public String toString()
	{
		if (getIsPluginService())
		{
			return String.format("RequestInstallService - Server ID : %1$s - Plugin Windows Service Key : %2$s", getServerID(), getPluginWindowsServiceKey());
		}
		else
		{
			return String.format("RequestInstallService - Server ID : %1$s - Service Name : %2$s", getServerID(), getServiceName());
		}
	}
}