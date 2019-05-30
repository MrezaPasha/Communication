package main.java.Rasad.Core.Services.NotificationService.Map;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(MapActionType))][DataContract][ProtoContract] public class TNotifyMapAction : TMessageBase
public class TNotifyMapAction extends TMessageBase
{
	private TNotifyMapAction()
	{
	}
	public TNotifyMapAction(UUID mapID, long mapAccessID, MapRequestedAction requestedAction, UUID serverInstallationKey)
	{
		this.setMapID(mapID);
		this.setMapAccessID(mapAccessID);
		this.setRequestedAction(requestedAction);
		this.setServerInstallationKey(serverInstallationKey);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public Guid MapID {get;private set;}
	private UUID MapID;
	public final UUID getMapID()
	{
		return MapID;
	}
	private void setMapID(UUID value)
	{
		MapID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public long MapAccessID {get;private set;}
	private long MapAccessID;
	public final long getMapAccessID()
	{
		return MapAccessID;
	}
	private void setMapAccessID(long value)
	{
		MapAccessID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public MapRequestedAction RequestedAction {get;private set;}
	//TOD MRREPLACE
	//private MapRequestedAction RequestedAction = MapRequestedAction.values()[0];
	private MapRequestedAction RequestedAction = MapRequestedAction.forValue(0);
	public final MapRequestedAction getRequestedAction()
	{
		return RequestedAction;
	}
	private void setRequestedAction(MapRequestedAction value)
	{
		RequestedAction = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public Guid ServerInstallationKey {get;private set;}
	private UUID ServerInstallationKey;
	public final UUID getServerInstallationKey()
	{
		return ServerInstallationKey;
	}
	private void setServerInstallationKey(UUID value)
	{
		ServerInstallationKey = value;
	}

	@Override
	public String toString()
	{
		return "NotifyMapAction: Map ID : " + getMapID().toString() + " RequestedAction : " + getRequestedAction() + " ServerInstallationKey : " + getServerInstallationKey();
	}
}