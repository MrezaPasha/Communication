package main.java.Rasad.Core.Services.NotificationService.Camera;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyCameraRecordingStateChanged : TMessageBase
public class TNotifyCameraRecordingStateChanged extends TMessageBase
{

	private TNotifyCameraRecordingStateChanged()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyCameraRecordingStateChanged(byte serverID, short cameraID, bool isRecording)
	public TNotifyCameraRecordingStateChanged(byte serverID, short cameraID, boolean isRecording)
	{
		super(serverID, cameraID);
		setCameraID(cameraID);
		setServerID(serverID);
		setIsRecording(isRecording);
		this.setFederationRouted(false);
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
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public bool IsRecording {get;private set;}
	private boolean IsRecording;
	public final boolean getIsRecording()
	{
		return IsRecording;
	}
	private void setIsRecording(boolean value)
	{
		IsRecording = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public bool FederationRouted {get;private set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ResetServerAndCameraID(byte newServerID, short newCameraID)
	public final void ResetServerAndCameraID(byte newServerID, short newCameraID)
	{
		this.set_ServerID(newServerID);
		this.setServerID(newServerID);

		this.set_CameraID(newCameraID);
		this.setCameraID(newCameraID);

		this.setFederationRouted(true);
	}

	@Override
	public String toString()
	{
		if (getIsRecording())
		{
			return "NotifyCameraRecordingStateChanged - ServerID : " + getServerID() + ", CameraID : " + getCameraID() + " Recording Started ...";
		}
		else
		{
			return "NotifyCameraRecordingStateChanged - ServerID : " + getServerID() + ", CameraID : " + getCameraID() + " Recording Stopped !!! ";
		}

	}
}