package main.java.Rasad.Core.Services.NotificationService.WorkflowService;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TEventBehaviorItemsChanged : TMessageBase
public class TEventBehaviorItemsChanged extends TMessageBase
{
	private TEventBehaviorItemsChanged()
	{
	}
	public TEventBehaviorItemsChanged(int eventBehaviorID)
	{
		this.setEventBehaviorID(eventBehaviorID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(1)][DataMember] public int EventBehaviorID {get;set;}
	private int EventBehaviorID;
	public final int getEventBehaviorID()
	{
		return EventBehaviorID;
	}
	public final void setEventBehaviorID(int value)
	{
		EventBehaviorID = value;
	}
}