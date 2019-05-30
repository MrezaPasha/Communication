package main.java.Rasad.Core.Services.NotificationService.Server;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyFailoverSettingsChanged : TMessageBase
public class TNotifyFailoverSettingsChanged extends TMessageBase
{
	private TNotifyFailoverSettingsChanged()
	{
	}
	public TNotifyFailoverSettingsChanged(int failoverServerGroupID)
	{
		this.setFailoverServerGroupID(failoverServerGroupID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int FailoverServerGroupID {get;set;}
	private int FailoverServerGroupID;
	public final int getFailoverServerGroupID()
	{
		return FailoverServerGroupID;
	}
	public final void setFailoverServerGroupID(int value)
	{
		FailoverServerGroupID = value;
	}
}