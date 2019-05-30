package main.java.Rasad.Core.Services.NotificationService;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyGuardTourChanged : TMessageBase
public class TNotifyGuardTourChanged extends TMessageBase
{
	private TNotifyGuardTourChanged()
	{
	}
	public TNotifyGuardTourChanged(int guardTourID)
	{
		setGuardTourID(guardTourID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int GuardTourID {get;private set;}
	private int GuardTourID;
	public final int getGuardTourID()
	{
		return GuardTourID;
	}
	private void setGuardTourID(int value)
	{
		GuardTourID = value;
	}

	@Override
	public String toString()
	{
		return "GuardTourID : " + String.valueOf(getGuardTourID());
	}
}