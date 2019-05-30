package main.java.Rasad.Core.Services.NotificationService;


import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoInclude(1, typeof(Rasad.Core.Services.NotificationService.Services.TRequestMainServiceState))][ProtoInclude(2, typeof(Rasad.Core.Services.NotificationService.Services.TRequestServiceState))][ProtoInclude(3, typeof(Rasad.Core.Services.NotificationService.Services.TRequestLicenseServiceState))][ProtoContract][DataContract] public abstract class TMessageRequest : TMessageBase
public abstract class TMessageRequest extends TMessageBase
{
	public TMessageRequest()
	{
		setRequestKey(UUID.randomUUID());
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(9999)][DataMember] public Guid RequestKey {get;private set;}
	private UUID RequestKey;
	public final UUID getRequestKey()
	{
		return RequestKey;
	}
	private void setRequestKey(UUID value)
	{
		RequestKey = value;
	}



}