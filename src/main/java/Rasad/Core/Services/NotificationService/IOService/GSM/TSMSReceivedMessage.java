package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TSMSReceivedMessage:TMessageBase
public class TSMSReceivedMessage extends TMessageBase
{
	private TSMSReceivedMessage()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TSMSReceivedMessage(byte serverID, string sourceNumber, string smsText)
	public TSMSReceivedMessage(byte serverID, String sourceNumber, String smsText)
	{
		super(serverID);
		this.setSourceNumber(sourceNumber);
		this.setSmsText(smsText);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string SourceNumber {get;private set;}
	private String SourceNumber;
	public final String getSourceNumber()
	{
		return SourceNumber;
	}
	private void setSourceNumber(String value)
	{
		SourceNumber = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string SmsText {get;private set;}
	private String SmsText;
	public final String getSmsText()
	{
		return SmsText;
	}
	private void setSmsText(String value)
	{
		SmsText = value;
	}
}