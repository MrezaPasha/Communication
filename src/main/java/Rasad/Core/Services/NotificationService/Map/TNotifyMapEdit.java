package main.java.Rasad.Core.Services.NotificationService.Map;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(MapActionType))][DataContract][ProtoContract] public class TNotifyMapEdit : TMessageBase
public class TNotifyMapEdit extends TMessageBase
{
	private TNotifyMapEdit()
	{
	}
	public TNotifyMapEdit(String mapTitle, UUID mapID, MapActionType actionType, int actorUserID, String actorIPAddress, String actorUserInfo, UUID serverInstallationKey, boolean isSystemMessage)
	{
		super();
		this.setMapTitle(mapTitle);
		this.setMapID(mapID);
		this.setActionType(actionType);
		this.setActorUserID(actorUserID);
		this.setActorIPAddress(actorIPAddress);
		this.setActorUserInfo(actorUserInfo);
		this.setServerInstallationKey(serverInstallationKey);
		this.setIsSystemMessage(isSystemMessage);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public String MapTitle {get;set;}
	private String MapTitle;
	public final String getMapTitle()
	{
		return MapTitle;
	}
	public final void setMapTitle(String value)
	{
		MapTitle = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid MapID {get;set;}
	private UUID MapID;
	public final UUID getMapID()
	{
		return MapID;
	}
	public final void setMapID(UUID value)
	{
		MapID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public MapActionType ActionType {get;set;}
	private MapActionType ActionType = MapActionType.values()[0];
	public final MapActionType getActionType()
	{
		return ActionType;
	}
	public final void setActionType(MapActionType value)
	{
		ActionType = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public int ActorUserID {get;set;}
	private int ActorUserID;
	public final int getActorUserID()
	{
		return ActorUserID;
	}
	public final void setActorUserID(int value)
	{
		ActorUserID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public string ActorIPAddress {get;set;}
	private String ActorIPAddress;
	public final String getActorIPAddress()
	{
		return ActorIPAddress;
	}
	public final void setActorIPAddress(String value)
	{
		ActorIPAddress = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public String ActorUserInfo {get;set;}
	private String ActorUserInfo;
	public final String getActorUserInfo()
	{
		return ActorUserInfo;
	}
	public final void setActorUserInfo(String value)
	{
		ActorUserInfo = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(7)] public bool IsSystemMessage {get;set;}
	private boolean IsSystemMessage;
	public final boolean getIsSystemMessage()
	{
		return IsSystemMessage;
	}
	public final void setIsSystemMessage(boolean value)
	{
		IsSystemMessage = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(8)] public Guid ServerInstallationKey {get;private set;}
	private UUID ServerInstallationKey;
	public final UUID getServerInstallationKey()
	{
		return ServerInstallationKey;
	}
	private void setServerInstallationKey(UUID value)
	{
		ServerInstallationKey = value;
	}

	@Override
	public String toString()
	{
		return "NotifyMapEdit: Map ID : " + getMapID().toString() + " Action : " + getActionType() + " User : " + getActorUserInfo();
	}
}