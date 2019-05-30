package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;
import org.jetbrains.annotations.Nullable;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TShowLivePopupInClient : TMessageBase
public class TShowLivePopupInClient extends TMessageBase
{
	private TShowLivePopupInClient()
	{

	}

	public TShowLivePopupInClient(java.util.UUID ownerKey, String message, String senderName, short[] camerasID, double duration, Integer senderID)
	{
		this(ownerKey, message, senderName, camerasID, duration, senderID, null);
	}

	public TShowLivePopupInClient(java.util.UUID ownerKey, String message, String senderName, short[] camerasID, double duration)
	{
		this(ownerKey, message, senderName, camerasID, duration, null, null);
	}

	public TShowLivePopupInClient(java.util.UUID ownerKey, String message, String senderName, short[] camerasID)
	{
		this(ownerKey, message, senderName, camerasID, 0, null, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TShowLivePopupInClient(Guid ownerKey, string message, string senderName, short[] camerasID, double duration = 0, Nullable<int> senderID = null, int[] receivers = null)
	public TShowLivePopupInClient(UUID ownerKey, String message, String senderName, short[] camerasID, double duration, Integer senderID, int[] receivers)
	{
		this.setMessage(message);
		this.setCamerasID(camerasID);
		this.setOwnerKey(ownerKey);
		this.setSenderName(senderName);
		this.setDuration(Math.max(duration, 0));
		this.setSenderID(senderID);
		this.setReceivers(receivers);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public short[] CamerasID {get;private set;}
	private short[] CamerasID;
	public final short[] getCamerasID()
	{
		return CamerasID;
	}
	private void setCamerasID(short[] value)
	{
		CamerasID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid OwnerKey {get;private set;}
	private UUID OwnerKey;
	public final UUID getOwnerKey()
	{
		return OwnerKey;
	}
	private void setOwnerKey(UUID value)
	{
		OwnerKey = value;
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
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string Message {get;set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public string SenderName {get;set;}
	private String SenderName;
	public final String getSenderName()
	{
		return SenderName;
	}
	public final void setSenderName(String value)
	{
		SenderName = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public Nullable<int> SenderID {get;private set;}
	private Integer SenderID = null;
	public final Integer getSenderID()
	{
		return SenderID;
	}
	private void setSenderID(Integer value)
	{
		SenderID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(7)] public int[] Receivers {get;private set;}
	private int[] Receivers;
	public final int[] getReceivers()
	{
		return Receivers;
	}
	private void setReceivers(int[] value)
	{
		Receivers = value;
	}

	public final void ResetCamerasID(short[] camerasID)
	{
		this.setCamerasID(camerasID);
	}
}