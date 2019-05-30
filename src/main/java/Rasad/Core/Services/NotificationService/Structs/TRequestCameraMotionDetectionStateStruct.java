package main.java.Rasad.Core.Services.NotificationService.Structs;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TRequestCameraMotionDetectionStateStruct
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TRequestCameraMotionDetectionStateStruct
public final class TRequestCameraMotionDetectionStateStruct
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(0)] private short cameraID;
	private short cameraID;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(2)] private bool federationRouted;
	private boolean federationRouted;

	public TRequestCameraMotionDetectionStateStruct()
	{
	}

	public TRequestCameraMotionDetectionStateStruct(short cameraID)
	{
		this.cameraID = cameraID;
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
	public boolean getFederationRouted()
	{
		return federationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		federationRouted = value;
	}

	public void ResetCameraID(short newCameraID)
	{
		this.cameraID = newCameraID;
		this.federationRouted = true;
	}

	@Override
	public String toString()
	{
		return "TRequestCameraMotionDetectionStateStruct - Camera ID : " + getCameraID();
	}

	public TRequestCameraMotionDetectionStateStruct clone()
	{
		TRequestCameraMotionDetectionStateStruct varCopy = new TRequestCameraMotionDetectionStateStruct();

		varCopy.cameraID = this.cameraID;
		varCopy.federationRouted = this.federationRouted;

		return varCopy;
	}
}