package main.java.Rasad.Core.Services.NotificationService.Failover;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyFailoverState : TMessageBase
public class TNotifyFailoverState extends TMessageBase
{
	private TNotifyFailoverState()
	{
	}
	public TNotifyFailoverState(UUID serverInstallationKey, boolean failoverIsOnline)
	{
		this.setServerInstallationKey(serverInstallationKey);
		this.setFailoverIsOnline(failoverIsOnline);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public Guid ServerInstallationKey {get;set;}
	private UUID ServerInstallationKey;
	public final UUID getServerInstallationKey()
	{
		return ServerInstallationKey;
	}
	public final void setServerInstallationKey(UUID value)
	{
		ServerInstallationKey = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public bool FailoverIsOnline {get;private set;}
	private boolean FailoverIsOnline;
	public final boolean getFailoverIsOnline()
	{
		return FailoverIsOnline;
	}
	private void setFailoverIsOnline(boolean value)
	{
		FailoverIsOnline = value;
	}
}