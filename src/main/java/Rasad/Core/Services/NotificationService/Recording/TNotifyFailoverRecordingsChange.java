package main.java.Rasad.Core.Services.NotificationService.Recording;


import main.java.Rasad.Core.Services.NotificationService.Recording.RecordingChangeNotifyType;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyFailoverRecordingsChange : TMessageBase
public class TNotifyFailoverRecordingsChange extends TMessageBase
{
	private TNotifyFailoverRecordingsChange()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyFailoverRecordingsChange(byte serverID, RecordingChangeNotifyType changeType, long recordFileID, long fileSize, long durationMilliseconds)
	public TNotifyFailoverRecordingsChange(byte serverID, RecordingChangeNotifyType changeType, long recordFileID, long fileSize, long durationMilliseconds)
	{
		super(serverID);
		this.setServerID(serverID);
		this.setChangeType(changeType);
		this.setRecordFileID(recordFileID);
		this.setFileSize(fileSize);
		this.setDurationMilliseconds(durationMilliseconds);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public RecordingChangeNotifyType ChangeType {get;private set;}
	private RecordingChangeNotifyType ChangeType = RecordingChangeNotifyType.values()[0];
	public final RecordingChangeNotifyType getChangeType()
	{
		return ChangeType;
	}
	private void setChangeType(RecordingChangeNotifyType value)
	{
		ChangeType = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public long RecordFileID {get;private set;}
	private long RecordFileID;
	public final long getRecordFileID()
	{
		return RecordFileID;
	}
	private void setRecordFileID(long value)
	{
		RecordFileID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public long FileSize {get;private set;}
	private long FileSize;
	public final long getFileSize()
	{
		return FileSize;
	}
	private void setFileSize(long value)
	{
		FileSize = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public long DurationMilliseconds {get;private set;}
	private long DurationMilliseconds;
	public final long getDurationMilliseconds()
	{
		return DurationMilliseconds;
	}
	private void setDurationMilliseconds(long value)
	{
		DurationMilliseconds = value;
	}
}