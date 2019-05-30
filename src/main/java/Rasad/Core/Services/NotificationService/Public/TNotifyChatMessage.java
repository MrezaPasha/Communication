package main.java.Rasad.Core.Services.NotificationService.Public;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyChatMessage : TMessageBase
public class TNotifyChatMessage extends TMessageBase
{
	private TNotifyChatMessage()
	{

	}

	public TNotifyChatMessage(String message, int senderID, String senderName)
	{
		this(message, senderID, senderName, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TNotifyChatMessage(string message, int senderID, string senderName, int[] receivers = null)
	public TNotifyChatMessage(String message, int senderID, String senderName, int[] receivers)
	{
		this.setMessage(message);
		this.setSenderID(senderID);
		this.setReceivers(receivers);
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
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public int[] Receivers {get;private set;}
	private int[] Receivers;
	public final int[] getReceivers()
	{
		return Receivers;
	}
	private void setReceivers(int[] value)
	{
		Receivers = value;
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