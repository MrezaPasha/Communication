package Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public;

import ProtoBuf.*;
import Rasad.VideoSurveillance.Core.*;
import Rasad.VideoSurveillance.Core.Services.MobileNotificationService.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TMobileNotifySettingsChanged : TMobileMessageBase
public class TMobileNotifySettingsChanged extends TMobileMessageBase
{
	private TMobileNotifySettingsChanged()
	{
		setObjectID(0);
	}

	public TMobileNotifySettingsChanged(MobileSettingsChangeType changeType, Integer objectID)
	{
		this.setChangeType(changeType);
		if (objectID == null)
		{
			this.setObjectID(0);
		}
		else
		{
			this.setObjectID(objectID.intValue());
		}
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public MobileSettingsChangeType ChangeType {get;set;}
	private MobileSettingsChangeType ChangeType = MobileSettingsChangeType.values()[0];
	public final MobileSettingsChangeType getChangeType()
	{
		return ChangeType;
	}
	public final void setChangeType(MobileSettingsChangeType value)
	{
		ChangeType = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public int ObjectID {get;set;}
	private int ObjectID;
	public final int getObjectID()
	{
		return ObjectID;
	}
	public final void setObjectID(int value)
	{
		ObjectID = value;
	}

	@Override
	public String toString()
	{
		return "MobileNotifySettingsChanged, ChangeType: " + getChangeType().toString() +
			" ObjectID: " + String.valueOf(getObjectID());
	}
}