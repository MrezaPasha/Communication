package main.java.Rasad.Core.Services.NotificationService.DesktopCollabration;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestCloseRemoteClient : TMessageBase
public class TRequestCloseRemoteClient extends TMessageBase
{
	private TRequestCloseRemoteClient()
	{

	}
	public TRequestCloseRemoteClient(String message, int senderID, int receiver)
	{
		this.setMessage(message);
		this.setSenderID(senderID);
		this.setReceiver(receiver);
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

}