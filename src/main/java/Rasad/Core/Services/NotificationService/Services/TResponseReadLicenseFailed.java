package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

public class TResponseReadLicenseFailed extends TMessageBase
{
	private TResponseReadLicenseFailed()
	{

	}
	public TResponseReadLicenseFailed(String error)
	{
		setErrorMessage(error);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string ErrorMessage {get;private set;}
	private String ErrorMessage;
	public final String getErrorMessage()
	{
		return ErrorMessage;
	}
	private void setErrorMessage(String value)
	{
		ErrorMessage = value;
	}
}