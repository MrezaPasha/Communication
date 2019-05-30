package main.java.Rasad.Core.Services.NotificationService.Motion;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyCameraMotionProcessorFailed : TMessageBase
public class TNotifyCameraMotionProcessorFailed extends TMessageBase
{
	private TNotifyCameraMotionProcessorFailed()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyCameraMotionProcessorFailed(byte serverID, short cameraID, DateTime failStartTime, DateTime failEndTime)
	public TNotifyCameraMotionProcessorFailed(byte serverID, short cameraID, LocalDateTime failStartTime, LocalDateTime failEndTime)
	{
		super(serverID, cameraID);
		setServerID(serverID);
		setCameraID(cameraID);
		setFailStartTime(failStartTime);
		setFailEndTime(failEndTime);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public short CameraID {get;private set;}
	private short CameraID;
	public final short getCameraID()
	{
		return CameraID;
	}
	private void setCameraID(short value)
	{
		CameraID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public byte ServerID {get;private set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public DateTime FailStartTime {get;private set;}
	private LocalDateTime FailStartTime = LocalDateTime.MIN;
	public final LocalDateTime getFailStartTime()
	{
		return FailStartTime;
	}
	private void setFailStartTime(LocalDateTime value)
	{
		FailStartTime = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public DateTime FailEndTime {get;private set;}
	private LocalDateTime FailEndTime = LocalDateTime.MIN;
	public final LocalDateTime getFailEndTime()
	{
		return FailEndTime;
	}
	private void setFailEndTime(LocalDateTime value)
	{
		FailEndTime = value;
	}
}