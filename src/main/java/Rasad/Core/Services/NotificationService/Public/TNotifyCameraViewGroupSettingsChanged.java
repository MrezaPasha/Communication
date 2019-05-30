package main.java.Rasad.Core.Services.NotificationService.Public;


//import Rasad.Core.Services.NotificationService.Public.CameraViewGroupChangeType;

import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(CameraViewGroupChangeType))][DataContract][ProtoContract] public class TNotifyCameraViewGroupSettingsChanged : TMessageBase
public class TNotifyCameraViewGroupSettingsChanged extends TMessageBase
{
	private TNotifyCameraViewGroupSettingsChanged()
	{

	}
	public TNotifyCameraViewGroupSettingsChanged(int userID, Integer oldParent, Integer newParent, int childID, boolean isGroup, CameraViewGroupChangeType changeType)
	{
		setUserID(userID);
		setOldParent(oldParent);
		setNewParent(newParent);
		setChildID(childID);
		setIsGroup(isGroup);
		setChangeType(changeType);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int UserID {get;set;}
	private int UserID;
	public final int getUserID()
	{
		return UserID;
	}
	public final void setUserID(int value)
	{
		UserID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Nullable<int> OldParent {get;set;}
	private Integer OldParent = null;
	public final Integer getOldParent()
	{
		return OldParent;
	}
	public final void setOldParent(Integer value)
	{
		OldParent = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public Nullable<int> NewParent {get;set;}
	private Integer NewParent = null;
	public final Integer getNewParent()
	{
		return NewParent;
	}
	public final void setNewParent(Integer value)
	{
		NewParent = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public bool IsGroup {get;set;}
	private boolean IsGroup;
	public final boolean getIsGroup()
	{
		return IsGroup;
	}
	public final void setIsGroup(boolean value)
	{
		IsGroup = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public CameraViewGroupChangeType ChangeType {get;set;}
	//private Rasad.Core.Services.NotificationService.Public.CameraViewGroupChangeType ChangeType = Rasad.Core.Services.NotificationService.Public.CameraViewGroupChangeType.values()[0];
	private CameraViewGroupChangeType ChangeType = CameraViewGroupChangeType.forValue(0);
	public final CameraViewGroupChangeType getChangeType()
	{
		return ChangeType;
	}
	public final void setChangeType(CameraViewGroupChangeType value)
	{
		ChangeType = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public int ChildID {get;set;}
	private int ChildID;
	public final int getChildID()
	{
		return ChildID;
	}
	public final void setChildID(int value)
	{
		ChildID = value;
	}
}