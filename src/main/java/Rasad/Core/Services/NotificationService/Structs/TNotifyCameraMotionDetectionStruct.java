package main.java.Rasad.Core.Services.NotificationService.Structs;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import Rasad.Core.TSerializationHelper;
import Rasad.VideoSurveillance.Core.MotionDetectionMechanism;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TNotifyCameraMotionDetectionStruct
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TNotifyCameraMotionDetectionStruct
public final class TNotifyCameraMotionDetectionStruct
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(0)] private Guid key;
	private UUID key;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(16)] private short cameraID;
	private short cameraID;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(18)] private byte serverID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [FieldOffset(18)] private byte serverID;
	private byte serverID;


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(19)] private bool isSetStartDateTime;
	private boolean isSetStartDateTime;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(23)] private DateTime startDateTime;
	private LocalDateTime startDateTime = LocalDateTime.MIN;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(31)] private bool isSetEndDateTime;
	private boolean isSetEndDateTime;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(35)] private DateTime endDateTime;
	private LocalDateTime endDateTime = LocalDateTime.MIN;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(43)] private MotionDetectionMechanism mechanism;
	private Rasad.VideoSurveillance.Core.MotionDetectionMechanism mechanism = Rasad.VideoSurveillance.Core.MotionDetectionMechanism.values()[0];

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(44)] private bool federationRouted;
	private boolean federationRouted;

	private void SetStartDateTime(LocalDateTime value)
	{
		this.isSetStartDateTime = value != null;
		if (value == null)
		{
			this.startDateTime = LocalDateTime.MIN;
		}
		else
		{
			this.startDateTime = value;
		}
	}
	private void SetEndDateTime(LocalDateTime value)
	{
		this.isSetEndDateTime = value != null;
		if (value == null)
		{
			this.endDateTime = LocalDateTime.MIN;
		}
		else
		{
			this.endDateTime = value;
		}
	}

	public TNotifyCameraMotionDetectionStruct()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyCameraMotionDetectionStruct(byte serverID, short cameraID, bool isMotionStart, MotionDetectionMechanism mechanism)
	public TNotifyCameraMotionDetectionStruct(byte serverID, short cameraID, boolean isMotionStart, Rasad.VideoSurveillance.Core.MotionDetectionMechanism mechanism)
	{
		this.serverID = serverID;
		this.cameraID = cameraID;
		if (isMotionStart)
		{
			this.startDateTime = LocalDateTime.now();
			this.isSetStartDateTime = true;
		}
		else
		{
			this.startDateTime = LocalDateTime.MIN;
			this.isSetStartDateTime = false;
		}
		this.key = UUID.randomUUID();
		this.isSetEndDateTime = false;
		this.endDateTime = LocalDateTime.MIN;
		this.mechanism = mechanism;
		this.federationRouted = false;
	}

	public UUID getKey()
	{
		return key;
	}
	private void setKey(UUID value)
	{
		key = value;
	}
	public short getCameraID()
	{
		return cameraID;
	}
	private void setCameraID(short value)
	{
		cameraID = value;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getServerID()
	public byte getServerID()
	{
		return serverID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setServerID(byte value)
	private void setServerID(byte value)
	{
		serverID = value;
	}
	public LocalDateTime getStartDateTime()
	{
		if (isSetStartDateTime)
		{
			return startDateTime;
		}
		else
		{
			return null;
		}
	}
	public LocalDateTime getEndDateTime()
	{
		if (isSetEndDateTime)
		{
			return endDateTime;
		}
		else
		{
			return null;
		}
	}
	public MotionDetectionMechanism getMechanism()
	{
		return mechanism;
	}
	private void setMechanism(MotionDetectionMechanism value)
	{
		mechanism = value;
	}

	public boolean getFederationRouted()
	{
		return federationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		federationRouted = value;
	}

	/** 
	 This method is for debugging purposes only
	*/
	public void SetFields(UUID key, LocalDateTime startDateTime, LocalDateTime endDateTime)
	{
		this.key = key;
		SetStartDateTime(startDateTime);
		SetEndDateTime(endDateTime);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ResetServerAndCameraID(byte newServerID, short newCameraID)
	public void ResetServerAndCameraID(byte newServerID, short newCameraID)
	{
		this.serverID = newServerID;
		this.cameraID = newCameraID;
		this.federationRouted = true;
	}

	public TNotifyCameraMotionDetectionStruct CloseAndClone(LocalDateTime specifiedEndDatetime)
	{
		TNotifyCameraMotionDetectionStruct ret = this.Clone().clone();
		if (ret.getEndDateTime() == null)
		{
			ret.SetEndDateTime(specifiedEndDatetime);
		}
		return ret;
	}
	public TNotifyCameraMotionDetectionStruct CloseAndClone()
	{
		// use this method, because structs are immutable!
		return CloseAndClone(LocalDateTime.now());
	}
	//public void ApplyEndDateTimeFrom(TNotifyCameraMotionDetectionStruct ncmd)
	//{
	//    EndDateTime = ncmd.EndDateTime;
	//}
	public boolean getHasMotionData()
	{
		return getStartDateTime() != null;
	}
	public boolean getIsComplete()
	{
		return getEndDateTime() != null;
	}

	@Override
	public String toString()
	{
		//return string.Format("TNotifyCameraMotionDetectionStruct - CameraID : {0} , Start = {1} - End = {2} => Duration : {3}", CameraID, StartDateTime, EndDateTime, EndDateTime - StartDateTime);
		class AnonymousType
		{
			public java.util.UUID Key;
			public short CameraID;
			public byte ServerID;
			public LocalDateTime StartDateTime;
			public java.time.LocalDateTime EndDateTime;
			public String Mechanism;

			public AnonymousType(java.util.UUID _Key, short _CameraID, byte _ServerID, java.time.LocalDateTime _StartDateTime, java.time.LocalDateTime _EndDateTime, String _Mechanism)
			{
				Key = _Key;
				CameraID = _CameraID;
				ServerID = _ServerID;
				StartDateTime = _StartDateTime;
				EndDateTime = _EndDateTime;
				Mechanism = _Mechanism;
			}
		}
		return TSerializationHelper.JsonSerializeString(new AnonymousType(key, getCameraID(), getServerID(), getStartDateTime(), getEndDateTime(), getMechanism().toString()));
	}

	public TNotifyCameraMotionDetectionStruct Clone()
	{
		TNotifyCameraMotionDetectionStruct ret = new TNotifyCameraMotionDetectionStruct(getServerID(), getCameraID(), true, getMechanism());
		ret.key = this.key;
		ret.isSetStartDateTime = this.isSetStartDateTime;
		ret.startDateTime = this.startDateTime;
		ret.isSetEndDateTime = this.isSetEndDateTime;
		ret.endDateTime = this.endDateTime;
		return ret;
	}

	@Override
	public boolean equals(Object obj)
	{
		return this.getKey().equals(((TNotifyCameraMotionDetectionStruct)obj).getKey());
	}

	@Override
	public int hashCode()
	{
		return this.key.hashCode();
	}

	public TNotifyCameraMotionDetectionStruct clone()
	{
		TNotifyCameraMotionDetectionStruct varCopy = new TNotifyCameraMotionDetectionStruct();

		varCopy.key = this.key;
		varCopy.cameraID = this.cameraID;
		varCopy.serverID = this.serverID;
		varCopy.isSetStartDateTime = this.isSetStartDateTime;
		varCopy.startDateTime = this.startDateTime;
		varCopy.isSetEndDateTime = this.isSetEndDateTime;
		varCopy.endDateTime = this.endDateTime;
		varCopy.mechanism = this.mechanism;
		varCopy.federationRouted = this.federationRouted;

		return varCopy;
	}
}