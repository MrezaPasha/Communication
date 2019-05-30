package main.java.Rasad.Core.Services.NotificationService.Camera;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(CameraGroupChangeType))][DataContract][ProtoContract] public class TNotifyCameraGroupSettingsChanged : TMessageBase
public class TNotifyCameraGroupSettingsChanged extends TMessageBase
{
	private TNotifyCameraGroupSettingsChanged()
	{

	}
	public TNotifyCameraGroupSettingsChanged(int userID, Short oldParent, Short newParent, short childID, boolean isGroup, CameraGroupChangeType changeType)
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Nullable<short> OldParent {get;set;}
	private Short OldParent = null;
	public final Short getOldParent()
	{
		return OldParent;
	}
	public final void setOldParent(Short value)
	{
		OldParent = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public Nullable<short> NewParent {get;set;}
	private Short NewParent = null;
	public final Short getNewParent()
	{
		return NewParent;
	}
	public final void setNewParent(Short value)
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
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public CameraGroupChangeType ChangeType {get;set;}
	private CameraGroupChangeType ChangeType = CameraGroupChangeType.forValue(0);
	public final CameraGroupChangeType getChangeType()
	{
		return ChangeType;
	}
	public final void setChangeType(CameraGroupChangeType value)
	{
		ChangeType = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public short ChildID {get;set;}
	private short ChildID;
	public final short getChildID()
	{
		return ChildID;
	}
	public final void setChildID(short value)
	{
		ChildID = value;
	}
}