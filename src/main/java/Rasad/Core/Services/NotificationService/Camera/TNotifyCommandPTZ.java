package main.java.Rasad.Core.Services.NotificationService.Camera;

import Rasad.Core.*;
import Rasad.Core.Interfaces.PTZCommand;
import Rasad.Core.Services.*;
import Rasad.Core.TNetworkHelper;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract][KnownType(typeof(PTZCommand))][KnownType(typeof(string[]))][KnownType(typeof(Nullable<Single>))] public class TNotifyCommandPTZ : TMessageBase
public class TNotifyCommandPTZ extends TMessageBase
{
	private TNotifyCommandPTZ()
	{
	}
	public TNotifyCommandPTZ(Integer userId, short cameraID, UUID cameraGUID, PTZCommand ptzCommand, short cameraNavigationPriority, UUID userGUID, String userFullname, String... ptzParams)
	{
		super(cameraID);
		this.setUserID(userId);
		this.setCameraID(cameraID);
		this.setCameraGUID(cameraGUID);
		this.setPTZCommand(ptzCommand);
		this.setPTZParams((ptzParams != null) ? ptzParams : new String[] { });
		this.setIpAddress(TNetworkHelper.getCurrentIpAddress());
		this.setMacAddress(TNetworkHelper.getCurrentMacAddress());
		this.setCameraNavigationPriority(cameraNavigationPriority);
		this.setFederationRouted(false);
		this.setPTZCommandGUID(UUID.randomUUID());
		this.setUserGUID(userGUID);
		this.setUserFullname(userFullname);
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public Nullable<int> UserID {get;private set;}
	private Integer UserID = null;
	public final Integer getUserID()
	{
		return UserID;
	}
	private void setUserID(Integer value)
	{
		UserID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public short CameraID {get;private set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public PTZCommand PTZCommand {get;private set;}
	private Rasad.Core.Interfaces.PTZCommand PTZCommand;
	public final Rasad.Core.Interfaces.PTZCommand getPTZCommand()
	{
		return PTZCommand;
	}
	private void setPTZCommand(PTZCommand value)
	{
		PTZCommand = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string[] PTZParams {get;private set;}
	private String[] PTZParams;
	public final String[] getPTZParams()
	{
		return PTZParams;
	}
	private void setPTZParams(String[] value)
	{
		PTZParams = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public string IpAddress {get;set;}
	private String IpAddress;
	public final String getIpAddress()
	{
		return IpAddress;
	}
	public final void setIpAddress(String value)
	{
		IpAddress = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public string MacAddress {get;set;}
	private String MacAddress;
	public final String getMacAddress()
	{
		return MacAddress;
	}
	public final void setMacAddress(String value)
	{
		MacAddress = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(7)] public bool ForceRun {get;set;}
	private boolean ForceRun;
	public final boolean getForceRun()
	{
		return ForceRun;
	}
	public final void setForceRun(boolean value)
	{
		ForceRun = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(8)] public Guid CameraGUID {get;private set;}
	private UUID CameraGUID;
	public final UUID getCameraGUID()
	{
		return CameraGUID;
	}
	private void setCameraGUID(UUID value)
	{
		CameraGUID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(9)] public short CameraNavigationPriority {get;private set;}
	private short CameraNavigationPriority;
	public final short getCameraNavigationPriority()
	{
		return CameraNavigationPriority;
	}
	private void setCameraNavigationPriority(short value)
	{
		CameraNavigationPriority = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(10)] public bool FederationRouted {get;private set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(11)] public Guid PTZCommandGUID {get;set;}
	private UUID PTZCommandGUID;
	public final UUID getPTZCommandGUID()
	{
		return PTZCommandGUID;
	}
	public final void setPTZCommandGUID(UUID value)
	{
		PTZCommandGUID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(12)] public Nullable<Guid> UserGUID {get;private set;}
	private UUID UserGUID = null;
	public final UUID getUserGUID()
	{
		return UserGUID;
	}
	private void setUserGUID(UUID value)
	{
		UserGUID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(13)] public String UserFullname {get;private set;}
	private String UserFullname;
	public final String getUserFullname()
	{
		return UserFullname;
	}
	private void setUserFullname(String value)
	{
		UserFullname = value;
	}

	public final void ResetCameraIDAndUserIDAndPriority(short newCameraID, Integer newUserID, short newPriority)
	{
		this.setCameraID(newCameraID);
		this.setUserID(newUserID);
		this.setCameraNavigationPriority(newPriority);
		this.setFederationRouted(true);
	}
	public final void ResetPTZParams(String[] newPTZParams)
	{
		this.setPTZParams(newPTZParams);
		this.setFederationRouted(true);
	}

	@Override
	public String toString()
	{

/*
		String ret = String.Concat("PTZ: UserID=", getUserID().toString(), ", Camera=", String.valueOf(getCameraID()), ", CameraGUID=", getCameraGUID(), ", Cmd=", getPTZCommand().toString(), ", Priority=", String.valueOf(getCameraNavigationPriority()), ", FederationRouted=", String.valueOf(getFederationRouted()), ", PTZCommandGUID=", getPTZCommandGUID());
*/
		String ret = "PTZ: UserID=" +  getUserID().toString() + ", Camera=" + String.valueOf(getCameraID()) + ", CameraGUID=" +  getCameraGUID() + ", Cmd=" + getPTZCommand().toString() + ", Priority=" + String.valueOf(getCameraNavigationPriority()) + ", FederationRouted=" + String.valueOf(getFederationRouted()) + ", PTZCommandGUID="+ getPTZCommandGUID();
		if (getPTZParams() != null)
		{
			try
			{
				//TODO MRCOMMENT : must uncomment after insert hibernate enshaalah
				//ret += ", Params=" + tangible.StringHelper.join(" | ", getPTZParams().Select(s -> s.ToStringSafe()).ToArray());
				ret += ", Params=" ;
			}
			catch (java.lang.Exception e)
			{
			}
		}
		return ret;
	}

}