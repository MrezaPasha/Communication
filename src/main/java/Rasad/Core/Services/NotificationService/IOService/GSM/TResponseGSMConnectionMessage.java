package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TResponseGSMConnectionMessage : TIOMessageBase
public class TResponseGSMConnectionMessage extends TIOMessageBase
{
	private TResponseGSMConnectionMessage()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseGSMConnectionMessage(byte serverID, string clientID, string message, bool isError)
	public TResponseGSMConnectionMessage(byte serverID, String clientID, String message, boolean isError)
	{
		super(serverID, clientID);
		setMessage(message);
		setIsError(isError);
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
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public string Smsc {get;set;}
	private String Smsc;
	public final String getSmsc()
	{
		return Smsc;
	}
	public final void setSmsc(String value)
	{
		Smsc = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public int SendRetries {get;set;}
	private int SendRetries;
	public final int getSendRetries()
	{
		return SendRetries;
	}
	public final void setSendRetries(int value)
	{
		SendRetries = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public int PollingInterval {get;set;}
	private int PollingInterval;
	public final int getPollingInterval()
	{
		return PollingInterval;
	}
	public final void setPollingInterval(int value)
	{
		PollingInterval = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public int SendWaitInterval {get;set;}
	private int SendWaitInterval;
	public final int getSendWaitInterval()
	{
		return SendWaitInterval;
	}
	public final void setSendWaitInterval(int value)
	{
		SendWaitInterval = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(7)] public string PhoneNumber {get;set;}
	private String PhoneNumber;
	public final String getPhoneNumber()
	{
		return PhoneNumber;
	}
	public final void setPhoneNumber(String value)
	{
		PhoneNumber = value;
	}
}