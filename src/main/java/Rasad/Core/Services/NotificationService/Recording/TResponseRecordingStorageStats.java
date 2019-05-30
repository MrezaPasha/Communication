package main.java.Rasad.Core.Services.NotificationService.Recording;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;
import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TResponseRecordingStorageStats : TMessageBase
public class TResponseRecordingStorageStats extends TMessageBase
{
	private TResponseRecordingStorageStats()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseRecordingStorageStats(byte serverID, Guid recordingStorageID)
	public TResponseRecordingStorageStats(byte serverID, UUID recordingStorageID)
	{
		super(serverID);
		this.setServerID(serverID);
		this.setRecordingStorageID(recordingStorageID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public byte ServerID {get;set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte ServerID;
	private byte ServerID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getServerID()
	public final byte getServerID()
	{
		return ServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setServerID(byte value)
	public final void setServerID(byte value)
	{
		ServerID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid RecordingStorageID {get;set;}
	private UUID RecordingStorageID;
	public final UUID getRecordingStorageID()
	{
		return RecordingStorageID;
	}
	public final void setRecordingStorageID(UUID value)
	{
		RecordingStorageID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public bool IsAvailble {get;set;}
	private boolean IsAvailble;
	public final boolean getIsAvailble()
	{
		return IsAvailble;
	}
	public final void setIsAvailble(boolean value)
	{
		IsAvailble = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public long UsedSpaceMB {get;set;}
	private long UsedSpaceMB;
	public final long getUsedSpaceMB()
	{
		return UsedSpaceMB;
	}
	public final void setUsedSpaceMB(long value)
	{
		UsedSpaceMB = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public Nullable<DateTime> MinRecordTime {get;set;}
	private LocalDateTime MinRecordTime = null;
	public final LocalDateTime getMinRecordTime()
	{
		return MinRecordTime;
	}
	public final void setMinRecordTime(LocalDateTime value)
	{
		MinRecordTime = value;
	}
}