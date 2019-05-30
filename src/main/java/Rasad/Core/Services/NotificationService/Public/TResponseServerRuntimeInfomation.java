package main.java.Rasad.Core.Services.NotificationService.Public;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceType))][KnownType(typeof(ServiceState))][DataContract][Serializable][ProtoContract] public class TResponseServerRuntimeInfomation : TMessageBase
public class TResponseServerRuntimeInfomation extends TMessageBase implements Serializable
{
	private TResponseServerRuntimeInfomation()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseServerRuntimeInfomation(byte serverID, String[] ipAddressList)
	public TResponseServerRuntimeInfomation(byte serverID, String[] ipAddressList)
	{
		super(serverID);
		this.setServerID(serverID);
		this.setIPAddressList(ipAddressList);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public byte ServerID {get;private set;}
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
//ORIGINAL LINE: private void setServerID(byte value)
	private void setServerID(byte value)
	{
		ServerID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public String[] IPAddressList {get;private set;}
	private String[] IPAddressList;
	public final String[] getIPAddressList()
	{
		return IPAddressList;
	}
	private void setIPAddressList(String[] value)
	{
		IPAddressList = value;
	}

	@Override
	public String toString()
	{
		return "Server: " + String.valueOf(getServerID()) + " - " + (getIPAddressList() == null ? "" : tangible.StringHelper.join(" | ", getIPAddressList()));
	}
}