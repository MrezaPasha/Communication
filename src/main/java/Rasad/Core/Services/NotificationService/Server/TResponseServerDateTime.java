package main.java.Rasad.Core.Services.NotificationService.Server;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(DateTime))][DataContract][ProtoContract] public class TResponseServerDateTime : TMessageBase
public class TResponseServerDateTime extends TMessageBase
{
	private TResponseServerDateTime()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TResponseServerDateTime(byte serverId)
	public TResponseServerDateTime(byte serverId)
	{
		super(serverId);
		setServerDateTime(LocalDateTime.now());
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public DateTime ServerDateTime {get;private set;}
	private LocalDateTime ServerDateTime = LocalDateTime.MIN;
	public final LocalDateTime getServerDateTime()
	{
		return ServerDateTime;
	}
	private void setServerDateTime(LocalDateTime value)
	{
		ServerDateTime = value;
	}

	@Override
	public String toString()
	{
		return getServerDateTime().toString();
	}
}