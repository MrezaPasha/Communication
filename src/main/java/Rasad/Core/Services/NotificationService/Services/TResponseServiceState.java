package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageResponse;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceState))][DataContract][ProtoContract] public class TResponseServiceState : TMessageResponse
public class TResponseServiceState extends TMessageResponse
{
	private TResponseServiceState()
	{
	}
	public TResponseServiceState(TRequestServiceState request, ServiceState serviceState)
	{
		super(request);
		this.setServiceState(serviceState);
		this.setServerID(request.getServerID());
		this.setServiceName(request.getServiceName());
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public ServiceState ServiceState {get;set;}
	private ServiceState ServiceState = getServiceState().values()[0];
	public final ServiceState getServiceState()
	{
		return ServiceState;
	}
	public final void setServiceState(ServiceState value)
	{
		ServiceState = value;
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
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public string ServiceName {get;private set;}
	private String ServiceName;
	public final String getServiceName()
	{
		return ServiceName;
	}
	private void setServiceName(String value)
	{
		ServiceName = value;
	}

	@Override
	public String toString()
	{
		return "ResponseServiceState: " + getServiceState().toString();
	}
}