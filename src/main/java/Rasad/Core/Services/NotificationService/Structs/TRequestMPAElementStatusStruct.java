package main.java.Rasad.Core.Services.NotificationService.Structs;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TRequestMPAElementStatusStruct
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TRequestMPAElementStatusStruct
public final class TRequestMPAElementStatusStruct
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(0)] private short cameraID;
	private short cameraID;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(2)] private byte serverID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [FieldOffset(2)] private byte serverID;
	private byte serverID;

	public TRequestMPAElementStatusStruct()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestMPAElementStatusStruct(byte serverID, short cameraID)
	public TRequestMPAElementStatusStruct(byte serverID, short cameraID)
	{
		this.serverID = serverID;
		this.cameraID = cameraID;
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

	@Override
	public String toString()
	{
		return "RequestMPAElementStatus - ServerID : " + String.valueOf(getServerID()) + ", CameraID : " + String.valueOf(getCameraID());
	}

	public TRequestMPAElementStatusStruct clone()
	{
		TRequestMPAElementStatusStruct varCopy = new TRequestMPAElementStatusStruct();

		varCopy.cameraID = this.cameraID;
		varCopy.serverID = this.serverID;

		return varCopy;
	}
}