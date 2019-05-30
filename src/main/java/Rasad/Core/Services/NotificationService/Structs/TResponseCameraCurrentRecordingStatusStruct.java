package main.java.Rasad.Core.Services.NotificationService.Structs;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TResponseCameraCurrentRecordingStatusStruct
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TResponseCameraCurrentRecordingStatusStruct
public final class TResponseCameraCurrentRecordingStatusStruct
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(0)] private short cameraID;
	private short cameraID;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(2)] private byte serverID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [FieldOffset(2)] private byte serverID;
	private byte serverID;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(3)] private bool isRecording;
	private boolean isRecording; // single byte size??
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(4)] private bool federationRouted;
	private boolean federationRouted;

	public TResponseCameraCurrentRecordingStatusStruct()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseCameraCurrentRecordingStatusStruct(byte serverID, short cameraID, bool isRecording)
	public TResponseCameraCurrentRecordingStatusStruct(byte serverID, short cameraID, boolean isRecording)
	{
		this.cameraID = cameraID;
		this.serverID = serverID;
		this.isRecording = isRecording;
		this.federationRouted = false;
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
	public boolean getIsRecording()
	{
		return isRecording;
	}
	private void setIsRecording(boolean value)
	{
		isRecording = value;
	}
	public boolean getFederationRouted()
	{
		return federationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		federationRouted = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ResetServerAndCameraID(byte newServerID, short newCameraID)
	public void ResetServerAndCameraID(byte newServerID, short newCameraID)
	{
		this.serverID = newServerID;
		this.cameraID = newCameraID;
		this.federationRouted = true;
	}

	@Override
	public String toString()
	{
		String retVal = "TResponseCameraCurrentRecordingStatusStruct - ";
		if (getIsRecording())
		{
			retVal += getServerID() + " " + getCameraID() + " Recording Started ...";
		}
		else
		{
			retVal += getServerID() + " " + getCameraID() + " Recording Stopped !!! ";
		}
		return retVal;
	}

	public TResponseCameraCurrentRecordingStatusStruct clone()
	{
		TResponseCameraCurrentRecordingStatusStruct varCopy = new TResponseCameraCurrentRecordingStatusStruct();

		varCopy.cameraID = this.cameraID;
		varCopy.serverID = this.serverID;
		varCopy.isRecording = this.isRecording;
		varCopy.federationRouted = this.federationRouted;

		return varCopy;
	}
}