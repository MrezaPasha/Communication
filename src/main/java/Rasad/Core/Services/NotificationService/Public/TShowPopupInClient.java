package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TShowPopupInClient : TMessageBase
public class TShowPopupInClient extends TMessageBase
{
	private TShowPopupInClient()
	{
	}
	/** 
	 
	 
	 @param popupHeader
	 @param popupMessage
	 //@param duration interval for show message in miliseconds, if Zero should close by user
	*/

	public TShowPopupInClient(java.util.UUID ownerKey, String popupHeader, String popupMessage, int[] specificReceiverUsers)
	{
		this(ownerKey, popupHeader, popupMessage, specificReceiverUsers, 0);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TShowPopupInClient(Guid ownerKey, string popupHeader, string popupMessage, int[] specificReceiverUsers, double duration = 0)
	public TShowPopupInClient(UUID ownerKey, String popupHeader, String popupMessage, int[] specificReceiverUsers, double duration)
	{
		this.setOwnerKey(ownerKey);
		this.setPopupHeader(popupHeader);
		this.setPopupMessage(popupMessage);
		this.setSpecificReceiverUsers(specificReceiverUsers);
		this.setDuration(Math.max(duration, 0));
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string PopupHeader {get;set;}
	private String PopupHeader;
	public final String getPopupHeader()
	{
		return PopupHeader;
	}
	public final void setPopupHeader(String value)
	{
		PopupHeader = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string PopupMessage {get;set;}
	private String PopupMessage;
	public final String getPopupMessage()
	{
		return PopupMessage;
	}
	public final void setPopupMessage(String value)
	{
		PopupMessage = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public double Duration {get;set;}
	private double Duration;
	public final double getDuration()
	{
		return Duration;
	}
	public final void setDuration(double value)
	{
		Duration = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public Guid OwnerKey {get;set;}
	private UUID OwnerKey;
	public final UUID getOwnerKey()
	{
		return OwnerKey;
	}
	public final void setOwnerKey(UUID value)
	{
		OwnerKey = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public Int32[] SpecificReceiverUsers {get;set;}
	private int[] SpecificReceiverUsers;
	public final int[] getSpecificReceiverUsers()
	{
		return SpecificReceiverUsers;
	}
	public final void setSpecificReceiverUsers(int[] value)
	{
		SpecificReceiverUsers = value;
	}

	@Override
	public String toString()
	{
		return getPopupMessage();
	}
}