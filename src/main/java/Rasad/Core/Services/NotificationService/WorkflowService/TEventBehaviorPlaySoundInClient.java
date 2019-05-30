package main.java.Rasad.Core.Services.NotificationService.WorkflowService;


import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TEventBehaviorPlaySoundInClient : TMessageBase
public class TEventBehaviorPlaySoundInClient extends TMessageBase
{
	private TEventBehaviorPlaySoundInClient()
	{
	}
	public TEventBehaviorPlaySoundInClient(int workFlowID, UUID actionKey)
	{
		this.setEventBehaviorID(workFlowID);
		this.setActionKey(actionKey);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int EventBehaviorID {get;set;}
	private int EventBehaviorID;
	public final int getEventBehaviorID()
	{
		return EventBehaviorID;
	}
	public final void setEventBehaviorID(int value)
	{
		EventBehaviorID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid ActionKey {get;set;}
	private UUID ActionKey;
	public final UUID getActionKey()
	{
		return ActionKey;
	}
	public final void setActionKey(UUID value)
	{
		ActionKey = value;
	}

}