package main.java.Rasad.Core.Services.NotificationService.Security;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(SecurityActionType))][DataContract][ProtoContract] public class TNotifySecuritySettingsChanged : TMessageBase
public class TNotifySecuritySettingsChanged extends TMessageBase
{
	public TNotifySecuritySettingsChanged()
	{

	}
	public TNotifySecuritySettingsChanged(int actorUserID, String actorIPAddress, SecurityActionType actionType, int targetUserOrRoleID, String targetUserOrRoleInfo, String actorUserInfo, UUID serverInstallationKey)
	{
		this.setActorUserID(actorUserID);
		this.setActorIPAddress(actorIPAddress);
		this.setActionType(actionType);
		this.setTargetUserOrRoleID(targetUserOrRoleID);
		this.setTargetUserOrRoleInfo(targetUserOrRoleInfo);
		this.setActorUserInfo(actorUserInfo);
		this.setServerInstallationKey(serverInstallationKey);
		this.setFederationRouted(false);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public Int32 ActorUserID {get;set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string ActorIPAddress {get;set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public SecurityActionType ActionType {get;set;}
	private SecurityActionType ActionType = SecurityActionType.values()[0];
	public final SecurityActionType getActionType()
	{
		return ActionType;
	}
	public final void setActionType(SecurityActionType value)
	{
		ActionType = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public int TargetUserOrRoleID {get;set;}
	private int TargetUserOrRoleID;
	public final int getTargetUserOrRoleID()
	{
		return TargetUserOrRoleID;
	}
	public final void setTargetUserOrRoleID(int value)
	{
		TargetUserOrRoleID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public String TargetUserOrRoleInfo {get;private set;}
	private String TargetUserOrRoleInfo;
	public final String getTargetUserOrRoleInfo()
	{
		return TargetUserOrRoleInfo;
	}
	private void setTargetUserOrRoleInfo(String value)
	{
		TargetUserOrRoleInfo = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public String ActorUserInfo {get;private set;}
	private String ActorUserInfo;
	public final String getActorUserInfo()
	{
		return ActorUserInfo;
	}
	private void setActorUserInfo(String value)
	{
		ActorUserInfo = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(7)] public Guid ServerInstallationKey {get;private set;}
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
//ORIGINAL LINE: [DataMember][ProtoMember(8)] public bool FederationRouted {get;private set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	private void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

	public final void ResetActorUserIDAndTargetUserOrRoleID(int newActorUserID, int newTargetUserOrRoleID)
	{
		this.setActorUserID(newActorUserID);
		this.setTargetUserOrRoleID(newTargetUserOrRoleID);
		this.setFederationRouted(true);
	}

	@Override
	public String toString()
	{
		return "Notify Security Settings Changed";
	}
}