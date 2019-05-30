package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

/** 
 When a service on one host is stopping or starting notifies to clients !
*/
//[Serializable]
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceType))][KnownType(typeof(ServiceState))][DataContract][ProtoContract] public class TNotifyServiceStateChanged : TMessageBase
public class TNotifyServiceStateChanged extends TMessageBase
{

	private TNotifyServiceStateChanged()
	{
	} // for protobuf

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyServiceStateChanged(byte serverID, string serviceName, ServiceState serviceState)
	public TNotifyServiceStateChanged(byte serverID, String serviceName, ServiceState serviceState)
	{
		super(serverID);
		setServerID(serverID);
		setServiceState(serviceState);
		setServiceName(serviceName);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyServiceStateChanged(byte serverID, ServiceType serviceType, ServiceState serviceState)
	public TNotifyServiceStateChanged(byte serverID, ServiceType serviceType, ServiceState serviceState)
	{
		//TODO MRCOMMENT : must uncomment
		this(serverID, /*GetServiceInformation(serviceType).ServiceName*/"serverName", serviceState);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string ServiceName {get;private set;}
	private String ServiceName;
	public final String getServiceName()
	{
		return ServiceName;
	}
	private void setServiceName(String value)
	{
		ServiceName = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public ServiceState ServiceState {get;private set;}
	private ServiceState ServiceState = getServiceState().values()[0];
	public final ServiceState getServiceState()
	{
		return ServiceState;
	}
	private void setServiceState(ServiceState value)
	{
		ServiceState = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public byte ServerID {get;private set;}
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

	@Override
	public String toString()
	{
		return getServerID() + " : " + getServiceName() + " " + getServiceState();
	}
}