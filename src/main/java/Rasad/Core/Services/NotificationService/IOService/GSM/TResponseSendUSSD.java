package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TResponseSendUSSD : TIOMessageBase
public class TResponseSendUSSD extends TIOMessageBase
{
	private TResponseSendUSSD()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseSendUSSD(byte serverID, string clientID, string message, bool isError)
	public TResponseSendUSSD(byte serverID, String clientID, String message, boolean isError)
	{
		super(serverID, clientID);
		this.setMessage(message);
		this.setIsError(isError);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string Message {get;private set;}
	private String Message;
	public final String getMessage()
	{
		return Message;
	}
	private void setMessage(String value)
	{
		Message = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public bool IsError {get;private set;}
	private boolean IsError;
	public final boolean getIsError()
	{
		return IsError;
	}
	private void setIsError(boolean value)
	{
		IsError = value;
	}
}