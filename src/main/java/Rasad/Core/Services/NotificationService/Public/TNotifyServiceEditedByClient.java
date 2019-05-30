package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceActionTypeEnum))][DataContract][ProtoContract] public class TNotifyServiceEditedByClient : TMessageBase
public class TNotifyServiceEditedByClient extends TMessageBase
{
	private TNotifyServiceEditedByClient()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyServiceEditedByClient(int userID, byte serverID, Guid serviceKey, string serviceTitle, ServiceActionTypeEnum actionType, String userInfo)
	public TNotifyServiceEditedByClient(int userID, byte serverID, UUID serviceKey, String serviceTitle, ServiceActionTypeEnum actionType, String userInfo)
	{
		this.setUserID(userID);
		this.setServerID(serverID);
		this.setServiceKey(serviceKey);
		this.setServiceTitle(serviceTitle);
		this.setActionType(actionType);
		this.setUserInfo(userInfo);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public byte ServerID {get;private set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte ServerID;
	private byte ServerID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getServerID()
	public final byte getServerID()
	{
		return ServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setServerID(byte value)
	private void setServerID(byte value)
	{
		ServerID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public Guid ServiceKey {get;private set;}
	private UUID ServiceKey;
	public final UUID getServiceKey()
	{
		return ServiceKey;
	}
	private void setServiceKey(UUID value)
	{
		ServiceKey = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string ServiceTitle {get;private set;}
	private String ServiceTitle;
	public final String getServiceTitle()
	{
		return ServiceTitle;
	}
	private void setServiceTitle(String value)
	{
		ServiceTitle = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public ServiceActionTypeEnum ActionType {get;private set;}
	private ServiceActionTypeEnum ActionType = ServiceActionTypeEnum.values()[0];
	public final ServiceActionTypeEnum getActionType()
	{
		return ActionType;
	}
	private void setActionType(ServiceActionTypeEnum value)
	{
		ActionType = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public String UserInfo {get;private set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(7)] public bool FederationRouted {get;private set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ResetUserIDAndServerID(int newUserID, byte newServerID)
	public final void ResetUserIDAndServerID(int newUserID, byte newServerID)
	{
		this.setUserID(newUserID);
		this.set_ServerID(newServerID);
		this.setServerID(newServerID);
		this.setFederationRouted(true);
	}

	@Override
	public String toString()
	{
		return String.format("ServiceEditedByClient, ServerID: %1$s, Service: %2$s, ActionType: %3$s, User: %4$s", getServerID(), getServiceTitle(), getActionType(), getUserInfo());
	}
}