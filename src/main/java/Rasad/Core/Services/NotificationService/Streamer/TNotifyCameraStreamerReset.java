package main.java.Rasad.Core.Services.NotificationService.Streamer;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyCameraStreamerReset : TMessageBase
public class TNotifyCameraStreamerReset extends TMessageBase
{
	private TNotifyCameraStreamerReset()
	{
	}

	public TNotifyCameraStreamerReset(short cameraID, boolean fullReset, String profileUrl)
	{
		super(cameraID);
		this.setCameraID(cameraID);
		this.setFullReset(fullReset);
		this.setProfileUrl(profileUrl);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public bool FullReset {get;private set;}
	private boolean FullReset;
	public final boolean getFullReset()
	{
		return FullReset;
	}
	private void setFullReset(boolean value)
	{
		FullReset = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(3)] public String ProfileUrl {get;private set;}
	private String ProfileUrl;
	public final String getProfileUrl()
	{
		return ProfileUrl;
	}
	private void setProfileUrl(String value)
	{
		ProfileUrl = value;
	}
}