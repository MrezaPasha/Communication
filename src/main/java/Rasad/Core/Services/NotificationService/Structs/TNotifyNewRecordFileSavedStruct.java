package main.java.Rasad.Core.Services.NotificationService.Structs;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TNotifyNewRecordFileSavedStruct
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TNotifyNewRecordFileSavedStruct
public final class TNotifyNewRecordFileSavedStruct
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(0)] private byte serverID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [FieldOffset(0)] private byte serverID;
	private byte serverID;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(1)] private short cameraID;
	private short cameraID;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(3)] private long recordFileID;
	private long recordFileID;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(11)] private bool federationRouted;
	private boolean federationRouted;

	public TNotifyNewRecordFileSavedStruct()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyNewRecordFileSavedStruct(byte serverID, short cameraID, long recordFileID, string filePath)
	public TNotifyNewRecordFileSavedStruct(byte serverID, short cameraID, long recordFileID, String filePath)
	{
		this.serverID = serverID;
		this.cameraID = cameraID;
		this.recordFileID = recordFileID;
		this.federationRouted = false;
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
	public short getCameraID()
	{
		return cameraID;
	}
	private void setCameraID(short value)
	{
		cameraID = value;
	}
	public long getRecordFileID()
	{
		return recordFileID;
	}
	private void setRecordFileID(long value)
	{
		recordFileID = value;
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
		return String.format("TNotifyNewRecordFileSavedStruct - Server ID : %1$s , Camera : %2$s , RecordFileID : %3$s", getServerID(), getCameraID(), getRecordFileID());
	}

	public TNotifyNewRecordFileSavedStruct clone()
	{
		TNotifyNewRecordFileSavedStruct varCopy = new TNotifyNewRecordFileSavedStruct();

		varCopy.serverID = this.serverID;
		varCopy.cameraID = this.cameraID;
		varCopy.recordFileID = this.recordFileID;
		varCopy.federationRouted = this.federationRouted;

		return varCopy;
	}
}