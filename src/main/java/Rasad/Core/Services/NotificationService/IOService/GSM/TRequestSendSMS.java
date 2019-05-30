package main.java.Rasad.Core.Services.NotificationService.IOService.GSM;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.IOService.TIOMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestSendSMS : TIOMessageBase
public class TRequestSendSMS extends TIOMessageBase
{
	private TRequestSendSMS()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestSendSMS(byte serverID, string clientID, string destinationNumber, string smsText, bool isAlertMessage, string smsOperator, string operatorType, bool useOriginalTest)
	public TRequestSendSMS(byte serverID, String clientID, String destinationNumber, String smsText, boolean isAlertMessage, String smsOperator, String operatorType, boolean useOriginalTest)
	{
		super(serverID, clientID);
		this.setDestinationNumber(destinationNumber);
		this.setSmsText(smsText);
		this.setIsAlertMessage(isAlertMessage);
		this.setSMSOperator(smsOperator);
		this.setOperatorType(operatorType);
		this.setUseOriginalTest(useOriginalTest);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string DestinationNumber {get;private set;}
	private String DestinationNumber;
	public final String getDestinationNumber()
	{
		return DestinationNumber;
	}
	private void setDestinationNumber(String value)
	{
		DestinationNumber = value;
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

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public bool IsAlertMessage {get;private set;}
	private boolean IsAlertMessage;
	public final boolean getIsAlertMessage()
	{
		return IsAlertMessage;
	}
	private void setIsAlertMessage(boolean value)
	{
		IsAlertMessage = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string SMSOperator {get;set;}
	private String SMSOperator;
	public final String getSMSOperator()
	{
		return SMSOperator;
	}
	public final void setSMSOperator(String value)
	{
		SMSOperator = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public string OperatorType {get;set;}
	private String OperatorType;
	public final String getOperatorType()
	{
		return OperatorType;
	}
	public final void setOperatorType(String value)
	{
		OperatorType = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public bool UseOriginalTest {get;set;}
	private boolean UseOriginalTest;
	public final boolean getUseOriginalTest()
	{
		return UseOriginalTest;
	}
	public final void setUseOriginalTest(boolean value)
	{
		UseOriginalTest = value;
	}
}