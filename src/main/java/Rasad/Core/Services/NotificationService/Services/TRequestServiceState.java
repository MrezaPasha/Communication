package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageRequest;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestServiceState : TMessageRequest
public class TRequestServiceState extends TMessageRequest
{
	private TRequestServiceState()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestServiceState(byte serverID, string serviceName)
	public TRequestServiceState(byte serverID, String serviceName)
	{
		setServerID(serverID);
		setServiceName(serviceName);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestServiceState(byte serverID, ServiceType serviceType)
	public TRequestServiceState(byte serverID, ServiceType serviceType)
	{
		//TODO MRCOMMENT : must uncomment
		this(serverID, /*System.ServiceKeyHelper.GetServiceInformation(serviceType).ServiceName*/"serverName");

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

	@Override
	public String toString()
	{
		return String.format("RequestServiceState - Server ID : %1$s , Service Name : %2$s", getServerID(), getServiceName());
	}
}