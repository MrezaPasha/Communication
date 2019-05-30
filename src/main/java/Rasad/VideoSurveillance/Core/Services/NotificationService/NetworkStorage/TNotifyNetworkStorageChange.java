package Rasad.VideoSurveillance.Core.Services.NotificationService.NetworkStorage;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.VideoSurveillance.Core.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(NetworkStorageActionType))][DataContract][ProtoContract] public class TNotifyNetworkStorageChange : TMessageBase
public class TNotifyNetworkStorageChange extends TMessageBase
{
	private TNotifyNetworkStorageChange()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyNetworkStorageChange(byte serverID, int nsid, String nsTitle, int actorUserID, string actorIPAddress, NetworkStorageActionType actionType, String actorUserInfo)
	public TNotifyNetworkStorageChange(byte serverID, int nsid, String nsTitle, int actorUserID, String actorIPAddress, NetworkStorageActionType actionType, String actorUserInfo)
	{
		super(serverID);
		this.setServerID(serverID);
		this.setNSID(nsid);
		this.setNSTitle(nsTitle);
		this.setActorUserID(actorUserID);
		this.setActorIPAddress(actorIPAddress);
		this.setActionType(actionType);
		this.setActorUserInfo(actorUserInfo);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public byte ServerID {get;set;}
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
//ORIGINAL LINE: public void setServerID(byte value)
	public final void setServerID(byte value)
	{
		ServerID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public int NSID {get;set;}
	private int NSID;
	public final int getNSID()
	{
		return NSID;
	}
	public final void setNSID(int value)
	{
		NSID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public String NSTitle {get;set;}
	private String NSTitle;
	public final String getNSTitle()
	{
		return NSTitle;
	}
	public final void setNSTitle(String value)
	{
		NSTitle = value;
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
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public NetworkStorageActionType ActionType {get;set;}
	private NetworkStorageActionType ActionType = NetworkStorageActionType.values()[0];
	public final NetworkStorageActionType getActionType()
	{
		return ActionType;
	}
	public final void setActionType(NetworkStorageActionType value)
	{
		ActionType = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(7)] public String ActorUserInfo {get;set;}
	private String ActorUserInfo;
	public final String getActorUserInfo()
	{
		return ActorUserInfo;
	}
	public final void setActorUserInfo(String value)
	{
		ActorUserInfo = value;
	}

	@Override
	public String toString()
	{
		return "NotifyNetworkStorageChange: ServerID=" + String.valueOf(getServerID()) + ", NSID=" + String.valueOf(getNSID()) + ", Title=" + getNSTitle() +
			", ActionType=" + getActionType().toString();
	}
}