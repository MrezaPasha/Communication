package main.java.Rasad.Core.Services.NotificationService.Recording;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TQueryRecordingStorageStats : TMessageBase
public class TQueryRecordingStorageStats extends TMessageBase
{
	private TQueryRecordingStorageStats()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TQueryRecordingStorageStats(byte serverID)
	public TQueryRecordingStorageStats(byte serverID)
	{
		super(serverID);
		this.setServerID(serverID);
		setRecordingStorageID(null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public byte ServerID {get;private set;}
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
//ORIGINAL LINE: private void setServerID(byte value)
	private void setServerID(byte value)
	{
		ServerID = value;
	}
	// set to specific recording storage id to only query that storage
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Nullable<Guid> RecordingStorageID {get;set;}
	private UUID RecordingStorageID = null;
	public final UUID getRecordingStorageID()
	{
		return RecordingStorageID;
	}
	public final void setRecordingStorageID(UUID value)
	{
		RecordingStorageID = value;
	}
}