package main.java.Rasad.Core.Services.NotificationService.IOService.Mail;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;
import main.java.Rasad.Core.Services.NotificationService.MessageTypeID;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TSendMailMessage:TIOMessageBase
public class TSendMailMessage extends TIOMessageBase
{
	private TSendMailMessage()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TSendMailMessage(byte serverID, string clientID, string to, string subject, string body, bool isHtmlBody)
	public TSendMailMessage(byte serverID, String clientID, String to, String subject, String body, boolean isHtmlBody)
	{
		super(serverID, clientID);
		this.set_MessageTypeID(MessageTypeID.SendMail);
		this.setSubject(subject);
		this.setTo(to);
		this.setBody(body);
		this.setIsHtmlBody(isHtmlBody);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TSendMailMessage(byte serverID, string clientID, byte[] fullMessage)
	public TSendMailMessage(byte serverID, String clientID, byte[] fullMessage)
	{
		super(serverID, clientID);
		this.set_MessageTypeID(MessageTypeID.SendMail);
		this.setFullMessage(fullMessage);
		this.setIsHtmlBody(true);
		this.setIsFullMessage(true);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string To {get;private set;}
	private String To;
	public final String getTo()
	{
		return To;
	}
	private void setTo(String value)
	{
		To = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string Subject {get;private set;}
	private String Subject;
	public final String getSubject()
	{
		return Subject;
	}
	private void setSubject(String value)
	{
		Subject = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public bool IsHtmlBody {get;private set;}
	private boolean IsHtmlBody;
	public final boolean getIsHtmlBody()
	{
		return IsHtmlBody;
	}
	private void setIsHtmlBody(boolean value)
	{
		IsHtmlBody = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string Body {get;private set;}
	private String Body;
	public final String getBody()
	{
		return Body;
	}
	private void setBody(String value)
	{
		Body = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public byte[] FullMessage {get;private set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] FullMessage;
	private byte[] FullMessage;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getFullMessage()
	public final byte[] getFullMessage()
	{
		return FullMessage;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setFullMessage(byte[] value)
	private void setFullMessage(byte[] value)
	{
		FullMessage = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public bool IsFullMessage {get;private set;}
	private boolean IsFullMessage;
	public final boolean getIsFullMessage()
	{
		return IsFullMessage;
	}
	private void setIsFullMessage(boolean value)
	{
		IsFullMessage = value;
	}

}