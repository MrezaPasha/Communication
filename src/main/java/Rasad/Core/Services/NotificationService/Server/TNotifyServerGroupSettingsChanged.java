package main.java.Rasad.Core.Services.NotificationService.Server;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServerActionType))][DataContract][ProtoContract] public class TNotifyServerGroupSettingsChanged : TMessageBase
public class TNotifyServerGroupSettingsChanged extends TMessageBase
{
	public TNotifyServerGroupSettingsChanged()
	{
	}
	public TNotifyServerGroupSettingsChanged(int serverGroupID, String serverGroupTitle, int actorUserID, String actorIPAddress, ServerActionType actionType)
	{
		this.setServerGroupID(serverGroupID);
		this.setServerGroupTitle(serverGroupTitle);
		this.setActorUserID(actorUserID);
		this.setActorIPAddress(actorIPAddress);
		this.setActionType(actionType);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int ServerGroupID {get;set;}
	private int ServerGroupID;
	public final int getServerGroupID()
	{
		return ServerGroupID;
	}
	public final void setServerGroupID(int value)
	{
		ServerGroupID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string ServerGroupTitle {get;set;}
	private String ServerGroupTitle;
	public final String getServerGroupTitle()
	{
		return ServerGroupTitle;
	}
	public final void setServerGroupTitle(String value)
	{
		ServerGroupTitle = value;
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

	@Override
	public String toString()
	{
		return "Server Group ID : " + getServerGroupID() + " Action : " + getActionType();
	}
}