package Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public;

import ProtoBuf.*;
import Rasad.VideoSurveillance.Core.*;
import Rasad.VideoSurveillance.Core.Services.MobileNotificationService.*;
import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TMobileShowPopupInClient : TMobileMessageBase
public class TMobileShowPopupInClient extends TMobileMessageBase
{
	private TMobileShowPopupInClient()
	{
	}


	public TMobileShowPopupInClient(java.util.UUID ownerKey, String popupHeader, String popupMessage, Nullable<Integer> targetUserID)
	{
		this(ownerKey, popupHeader, popupMessage, targetUserID, 0);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TMobileShowPopupInClient(Guid ownerKey, string popupHeader, string popupMessage, Nullable<Int32> targetUserID, double duration = 0)
	public TMobileShowPopupInClient(UUID ownerKey, String popupHeader, String popupMessage, Integer targetUserID, double duration)
	{
		this.setOwnerKey(ownerKey);
		this.setPopupHeader(popupHeader);
		this.setPopupMessage(popupMessage);
		if (targetUserID != null)
		{
			this.set_TargetUserID(targetUserID.intValue());
		}
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

	@Override
	public String toString()
	{
		return getPopupMessage();
	}
}