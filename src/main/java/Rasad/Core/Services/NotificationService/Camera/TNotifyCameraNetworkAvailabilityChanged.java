package main.java.Rasad.Core.Services.NotificationService.Camera;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyCameraNetworkAvailabilityChanged : TMessageBase
public class TNotifyCameraNetworkAvailabilityChanged extends TMessageBase
{
	private TNotifyCameraNetworkAvailabilityChanged()
	{
	}
	public TNotifyCameraNetworkAvailabilityChanged(short cameraID, boolean isAvailable)
	{
		super(cameraID);
		this.setCameraID(cameraID);
		this.setIsAvailable(isAvailable);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public bool IsAvailable {get;private set;}
	private boolean IsAvailable;
	public final boolean getIsAvailable()
	{
		return IsAvailable;
	}
	private void setIsAvailable(boolean value)
	{
		IsAvailable = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public bool FederationRouted {get;private set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

	public final void ResetCameraID(short newCameraID)
	{
		this.set_CameraID(newCameraID);
		this.setCameraID(newCameraID);
		this.setFederationRouted(true);
	}

	@Override
	public String toString()
	{
		return "Camera : " + getCameraID() + " => Is Available : " + getIsAvailable();
	}
}