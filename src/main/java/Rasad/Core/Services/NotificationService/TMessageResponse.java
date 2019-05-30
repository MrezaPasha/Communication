package main.java.Rasad.Core.Services.NotificationService;


import java.util.*;

//[ProtoInclude(1, typeof(Rasad.Core.Services.NotificationService.Services.TResponseMainServiceState))]
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoInclude(2, typeof(Rasad.Core.Services.NotificationService.Services.TResponseServiceState))][ProtoInclude(3, typeof(Rasad.Core.Services.NotificationService.Services.TResponseLicenseServiceState))][ProtoInclude(4, typeof(Rasad.Core.Services.NotificationService.Services.TResponseMainServiceState2))][ProtoContract][DataContract] public abstract class TMessageResponse : TMessageBase
public abstract class TMessageResponse extends TMessageBase
{
	public TMessageResponse()
	{

	}
	public TMessageResponse(TMessageRequest request)
	{
		this();
		this.setRequestKey(request.getRequestKey());
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(9998)][DataMember] public Guid RequestKey {get;private set;}
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