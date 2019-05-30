package main.java.Rasad.Core.Services.NotificationService.DesktopCollabration;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestDesktopCollabration : TMessageBase
public class TRequestDesktopCollabration extends TMessageBase
{
	private TRequestDesktopCollabration()
	{

	}
	public TRequestDesktopCollabration(String message, int senderID, String senderName, int receiver)
	{
		this.setMessage(message);
		this.setSenderID(senderID);
		this.setReceiver(receiver);
		this.setSenderName(senderName);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string Message {get;set;}
	private String Message;
	public final String getMessage()
	{
		return Message;
	}
	public final void setMessage(String value)
	{
		Message = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public int SenderID {get;private set;}
	private int SenderID;
	public final int getSenderID()
	{
		return SenderID;
	}
	private void setSenderID(int value)
	{
		SenderID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public int Receiver {get;private set;}
	private int Receiver;
	public final int getReceiver()
	{
		return Receiver;
	}
	private void setReceiver(int value)
	{
		Receiver = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string SenderName {get;set;}
	private String SenderName;
	public final String getSenderName()
	{
		return SenderName;
	}
	public final void setSenderName(String value)
	{
		SenderName = value;
	}
}