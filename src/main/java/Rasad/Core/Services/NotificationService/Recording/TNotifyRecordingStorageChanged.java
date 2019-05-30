package main.java.Rasad.Core.Services.NotificationService.Recording;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyRecordingStorageChanged : TMessageBase
public class TNotifyRecordingStorageChanged extends TMessageBase
{
	private TNotifyRecordingStorageChanged()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyRecordingStorageChanged(byte serverID, Guid recordingStorageID, RecordingStorageChangeType changeType)
	public TNotifyRecordingStorageChanged(byte serverID, UUID recordingStorageID, RecordingStorageChangeType changeType)
	{
		super(serverID);
		this.setServerID(serverID);
		this.setRecordingStorageID(recordingStorageID);
		this.setChangeType(changeType);
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
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid RecordingStorageID {get;private set;}
	private UUID RecordingStorageID;
	public final UUID getRecordingStorageID()
	{
		return RecordingStorageID;
	}
	private void setRecordingStorageID(UUID value)
	{
		RecordingStorageID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public RecordingStorageChangeType ChangeType {get;private set;}
	private RecordingStorageChangeType ChangeType = RecordingStorageChangeType.values()[0];
	public final RecordingStorageChangeType getChangeType()
	{
		return ChangeType;
	}
	private void setChangeType(RecordingStorageChangeType value)
	{
		ChangeType = value;
	}

	@Override
	public String toString()
	{
		return String.format("RecordingStorageChange: ServerID=%1$s, RecordingStorageID=%2$s, ChangeType=%3$s", this.getServerID(), this.getRecordingStorageID(), getChangeType().toString());
	}
}