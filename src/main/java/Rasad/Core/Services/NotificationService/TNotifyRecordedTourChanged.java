package main.java.Rasad.Core.Services.NotificationService;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyRecordedTourChanged : TMessageBase
public class TNotifyRecordedTourChanged extends TMessageBase
{
	private TNotifyRecordedTourChanged()
	{
	}
	public TNotifyRecordedTourChanged(int recordedTourID)
	{
		setRecordedTourID(recordedTourID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int RecordedTourID {get;private set;}
	private int RecordedTourID;
	public final int getRecordedTourID()
	{
		return RecordedTourID;
	}
	private void setRecordedTourID(int value)
	{
		RecordedTourID = value;
	}

	@Override
	public String toString()
	{
		return "RecordedTourID : " + String.valueOf(getRecordedTourID());
	}
}