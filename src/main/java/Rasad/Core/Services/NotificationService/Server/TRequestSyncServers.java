package main.java.Rasad.Core.Services.NotificationService.Server;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(SyncRequestType))][DataContract][ProtoContract] public class TRequestSyncServers : TMessageBase
public class TRequestSyncServers extends TMessageBase
{
	private TRequestSyncServers()
	{
	}
	public TRequestSyncServers(SyncRequestType syncRequestType, String reason)
	{
		this.setSyncRequestType(syncRequestType);
		this.setReason(reason);
		this.setRelatedID(null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public SyncRequestType SyncRequestType {get;private set;}
	private SyncRequestType SyncRequestType = getSyncRequestType().values()[0];
	public final SyncRequestType getSyncRequestType()
	{
		return SyncRequestType;
	}
	private void setSyncRequestType(SyncRequestType value)
	{
		SyncRequestType = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public String Reason {get;private set;}
	private String Reason;
	public final String getReason()
	{
		return Reason;
	}
	private void setReason(String value)
	{
		Reason = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public Nullable<int> RelatedID {get;set;}
	private Integer RelatedID = null;
	public final Integer getRelatedID()
	{
		return RelatedID;
	}
	public final void setRelatedID(Integer value)
	{
		RelatedID = value;
	}

	@Override
	public String toString()
	{
		return "RequestSyncServers : " + getSyncRequestType().toString() + " Reason : " + getReason();
	}
}