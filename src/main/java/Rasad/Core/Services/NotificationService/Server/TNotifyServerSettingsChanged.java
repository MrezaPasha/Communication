package main.java.Rasad.Core.Services.NotificationService.Server;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

/**
 Client when changes properties of a server such as dive sizes and active state,then recording service of that server should start recording or stop recording !
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServerActionType))][DataContract][ProtoContract] public class TNotifyServerSettingsChanged : TMessageBase
public class TNotifyServerSettingsChanged extends TMessageBase
{
	private TNotifyServerSettingsChanged()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyServerSettingsChanged(byte serverID, string serverTitle, int actorUserID, string actorIPAddress, ServerActionType actionType, String actorUserInfo, bool isSystemMessage)
	public TNotifyServerSettingsChanged(byte serverID, String serverTitle, int actorUserID, String actorIPAddress, ServerActionType actionType, String actorUserInfo, boolean isSystemMessage)
	{
		super(serverID);
		this.setServerID(serverID);
		this.setServerTitle(serverTitle);
		this.setActorUserID(actorUserID);
		this.setActorIPAddress(actorIPAddress);
		this.setActionType(actionType);
		this.setActorUserInfo(actorUserInfo);
		this.setIsSystemMessage(isSystemMessage);
		this.setFederationRouted(false);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string ServerTitle {get;set;}
	private String ServerTitle;
	public final String getServerTitle()
	{
		return ServerTitle;
	}
	public final void setServerTitle(String value)
	{
		ServerTitle = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public int ActorUserID {get;set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string ActorIPAddress {get;set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public ServerActionType ActionType {get;set;}
	private ServerActionType ActionType = ServerActionType.values()[0];
	public final ServerActionType getActionType()
	{
		return ActionType;
	}
	public final void setActionType(ServerActionType value)
	{
		ActionType = value;
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
//ORIGINAL LINE: [DataMember][ProtoMember(8)] public bool FederationRouted {get;set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	public final void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void ResetUserIDAndServerID(int newUserID, byte newServerID)
	public final void ResetUserIDAndServerID(int newUserID, byte newServerID)
	{
		this.setActorUserID(newUserID);
		this.set_ServerID(newServerID);
		this.setServerID(newServerID);
		this.setFederationRouted(true);
	}

	@Override
	public String toString()
	{
		return "Server ID : " + getServerID() + " Action : " + getActionType() + " User : " + getActorUserInfo();
	}
}