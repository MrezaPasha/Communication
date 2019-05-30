package main.java.Rasad.Core.Services.NotificationService.Streamer;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract][KnownType(typeof(CameraStreamerAddressChangeType))] public class TNotifyCameraProfileAccess : TMessageBase
public class TNotifyCameraProfileAccess extends TMessageBase
{
	private TNotifyCameraProfileAccess()
	{
	}

	public TNotifyCameraProfileAccess(String profileUrl, boolean isStreamerProfile)
	{
		this.setProfileUrl(profileUrl);
		this.setIsStreamerProfile(isStreamerProfile);
		//System.Diagnostics.Process p = System.Diagnostics.Process.GetCurrentProcess();
		ProcessHandle p = ProcessHandle.current();
		this.setProcessName(String.format("%1$s (%2$s)", p.toString(), p.pid()));
		this.setFederationRouted(false);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public String ProfileUrl {get;private set;}
	private String ProfileUrl;
	public final String getProfileUrl()
	{
		return ProfileUrl;
	}
	private void setProfileUrl(String value)
	{
		ProfileUrl = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public bool IsStreamerProfile {get;private set;}
	private boolean IsStreamerProfile;
	public final boolean getIsStreamerProfile()
	{
		return IsStreamerProfile;
	}
	private void setIsStreamerProfile(boolean value)
	{
		IsStreamerProfile = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public String ProcessName {get;set;}
	private String ProcessName;
	public final String getProcessName()
	{
		return ProcessName;
	}
	public final void setProcessName(String value)
	{
		ProcessName = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public bool FederationRouted {get;set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	public final void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

	@Override
	public String toString()
	{
		return "Camera Profile Access - ProfileUrl : " + getProfileUrl() +
			", IsStreamerProfile : " + String.valueOf(getIsStreamerProfile()) +
			(!tangible.StringHelper.isNullOrWhiteSpace(getProcessName()) ? ", ProcessName : " + getProcessName() : "");
	}
}