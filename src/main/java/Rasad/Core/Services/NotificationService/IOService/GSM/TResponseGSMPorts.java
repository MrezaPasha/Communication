package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TResponseGSMPorts : TIOMessageBase
public class TResponseGSMPorts extends TIOMessageBase
{
	private TResponseGSMPorts()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseGSMPorts(byte serverID, string clientID, string[] ports)
	public TResponseGSMPorts(byte serverID, String clientID, String[] ports)
	{
		super(serverID, clientID);
		this.setPorts(ports);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string[] Ports {get;private set;}
	private String[] Ports;
	public final String[] getPorts()
	{
		return Ports;
	}
	private void setPorts(String[] value)
	{
		Ports = value;
	}
}