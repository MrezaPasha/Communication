package Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Camera;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.VideoSurveillance.Core.*;
import Rasad.VideoSurveillance.Core.Services.MobileNotificationService.*;
import java.util.*;
import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoContract] public class TMobileNotifyCameraMotionDetection : TMobileMessageBase
public class TMobileNotifyCameraMotionDetection extends TMobileMessageBase
{
	private TMobileNotifyCameraMotionDetection()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMobileNotifyCameraMotionDetection(byte serverID, short cameraID)
	public TMobileNotifyCameraMotionDetection(byte serverID, short cameraID)
	{
		super(serverID, cameraID);
		this.setServerID(serverID);
		this.setCameraID(cameraID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(1)][DataMember] public Guid Key {get;set;}
	private UUID Key;
	public final UUID getKey()
	{
		return Key;
	}
	public final void setKey(UUID value)
	{
		Key = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(2)][DataMember] public short CameraID {get;private set;}
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
//ORIGINAL LINE: [ProtoMember(3)][DataMember] public byte ServerID {get;private set;}
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
//ORIGINAL LINE: [ProtoMember(4)][DataMember] public Nullable<DateTime> StartDateTime {get;set;}
	private LocalDateTime StartDateTime = null;
	public final LocalDateTime getStartDateTime()
	{
		return StartDateTime;
	}
	public final void setStartDateTime(LocalDateTime value)
	{
		StartDateTime = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(5)][DataMember] public Nullable<DateTime> EndDateTime {get;set;}
	private LocalDateTime EndDateTime = null;
	public final LocalDateTime getEndDateTime()
	{
		return EndDateTime;
	}
	public final void setEndDateTime(LocalDateTime value)
	{
		EndDateTime = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(6)][DataMember] public MotionDetectionMechanism Mechanism {get;set;}
	private MotionDetectionMechanism Mechanism = MotionDetectionMechanism.values()[0];
	public final MotionDetectionMechanism getMechanism()
	{
		return Mechanism;
	}
	public final void setMechanism(MotionDetectionMechanism value)
	{
		Mechanism = value;
	}

	@Override
	public String toString()
	{
		return "MobileNotifyCameraMotionDetection: " + getKey().toString();
	}
}