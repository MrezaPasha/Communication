package main.java.Rasad.Core.Services.NotificationService.Camera;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract][KnownType(typeof(PTZCommand))] public class TNotifyPTZStatus : TMessageBase
public class TNotifyPTZStatus extends TMessageBase
{
	private TNotifyPTZStatus()
	{
	}
	public TNotifyPTZStatus(UUID ptzCommandGUID, UUID serverInstallationKey, int userID, UUID userGUID, String userInfo)
	{
		this.setPTZCommandGUID(ptzCommandGUID);
		this.setServerInstallationKey(serverInstallationKey);
		this.setUserID(userID);
		this.setUserGUID(userGUID);
		this.setUserInfo(userInfo);
		this.setFederationRouted(false);
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public Guid PTZCommandGUID {get;private set;}
	private UUID PTZCommandGUID;
	public final UUID getPTZCommandGUID()
	{
		return PTZCommandGUID;
	}
	private void setPTZCommandGUID(UUID value)
	{
		PTZCommandGUID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid ServerInstallationKey {get;private set;}
	private UUID ServerInstallationKey;
	public final UUID getServerInstallationKey()
	{
		return ServerInstallationKey;
	}
	private void setServerInstallationKey(UUID value)
	{
		ServerInstallationKey = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public int UserID {get;private set;}
	private int UserID;
	public final int getUserID()
	{
		return UserID;
	}
	private void setUserID(int value)
	{
		UserID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public String UserInfo {get;private set;}
	private String UserInfo;
	public final String getUserInfo()
	{
		return UserInfo;
	}
	private void setUserInfo(String value)
	{
		UserInfo = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public bool FederationRouted {get;set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	public final void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public Guid UserGUID {get;private set;}
	private UUID UserGUID;
	public final UUID getUserGUID()
	{
		return UserGUID;
	}
	private void setUserGUID(UUID value)
	{
		UserGUID = value;
	}

	@Override
	public String toString()
	{
		return "TNotifyPTZStatus: " + getUserInfo();
	}

}