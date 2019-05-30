package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyLogoutUser : TMessageBase
public class TNotifyLogoutUser extends TMessageBase
{
	private TNotifyLogoutUser()
	{

	}
	public TNotifyLogoutUser(int userID, String ipAddress, String userInfo, UUID serverInstallationKey)
	{
		this.setUserID(userID);
		this.setIpAddress(ipAddress);
		this.setUserInfo(userInfo);
		this.setServerInstallationKey(serverInstallationKey);
		this.setFederationRouted(false);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int UserID {get;private set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string IpAddress {get;set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public String UserInfo {get;private set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public Guid ServerInstallationKey {get;private set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public bool FederationRouted {get;private set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

	public final void ResetUserID(int newUserID)
	{
		this.setUserID(newUserID);
		this.setFederationRouted(true);
	}
}