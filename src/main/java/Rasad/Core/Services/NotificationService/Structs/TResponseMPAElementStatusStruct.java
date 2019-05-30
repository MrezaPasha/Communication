package main.java.Rasad.Core.Services.NotificationService.Structs;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TResponseMPAElementStatusStruct
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TResponseMPAElementStatusStruct
public final class TResponseMPAElementStatusStruct
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
//ORIGINAL LINE: [FieldOffset(3)] private bool isWorkingProperly;
	private boolean isWorkingProperly;

	public TResponseMPAElementStatusStruct()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseMPAElementStatusStruct(byte serverID, short cameraID, bool isWorkingProperly)
	public TResponseMPAElementStatusStruct(byte serverID, short cameraID, boolean isWorkingProperly)
	{
		this.serverID = serverID;
		this.cameraID = cameraID;
		this.isWorkingProperly = isWorkingProperly;
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
	public boolean getIsWorkingProperly()
	{
		return isWorkingProperly;
	}
	private void setIsWorkingProperly(boolean value)
	{
		isWorkingProperly = value;
	}

	@Override
	public String toString()
	{
		return "ResponseMPAElementStatus - ServerID : " + String.valueOf(getServerID()) + ", CameraID : " + String.valueOf(getCameraID()) + " (" + String.valueOf(getIsWorkingProperly()) + ")";
	}

	public TResponseMPAElementStatusStruct clone()
	{
		TResponseMPAElementStatusStruct varCopy = new TResponseMPAElementStatusStruct();

		varCopy.cameraID = this.cameraID;
		varCopy.serverID = this.serverID;
		varCopy.isWorkingProperly = this.isWorkingProperly;

		return varCopy;
	}
}