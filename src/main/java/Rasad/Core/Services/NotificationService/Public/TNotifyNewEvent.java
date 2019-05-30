package main.java.Rasad.Core.Services.NotificationService.Public;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyNewEvent : TMessageBase
public class TNotifyNewEvent extends TMessageBase
{
	private TNotifyNewEvent()
	{
	}

	public TNotifyNewEvent(UUID eventOwnerKey, int eventId, int eventTypeID)
	{
		this.setEventID(eventId);
		this.setEventOwnerKey(eventOwnerKey);
		this.setEventTypeID(eventTypeID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int EventID {get;set;}
	private int EventID;
	public final int getEventID()
	{
		return EventID;
	}
	public final void setEventID(int value)
	{
		EventID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid EventOwnerKey {get;set;}
	private UUID EventOwnerKey;
	public final UUID getEventOwnerKey()
	{
		return EventOwnerKey;
	}
	public final void setEventOwnerKey(UUID value)
	{
		EventOwnerKey = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public int EventTypeID {get;set;}
	private int EventTypeID;
	public final int getEventTypeID()
	{
		return EventTypeID;
	}
	public final void setEventTypeID(int value)
	{
		EventTypeID = value;
	}

	@Override
	public String toString()
	{
		return "Event " + String.valueOf(getEventID()) + " Owner Key : " + getEventOwnerKey().toString() + " TypeID : " + getEventTypeID();
	}
}