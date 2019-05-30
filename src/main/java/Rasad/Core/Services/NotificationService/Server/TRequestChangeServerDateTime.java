package main.java.Rasad.Core.Services.NotificationService.Server;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(DateTime))][DataContract][ProtoContract] public class TRequestChangeServerDateTime : TMessageBase
public class TRequestChangeServerDateTime extends TMessageBase
{

	private TRequestChangeServerDateTime()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestChangeServerDateTime(byte serverID,DateTime changingDateTime)
	public TRequestChangeServerDateTime(byte serverID, LocalDateTime changingDateTime)
	{
		super(serverID);
		this.setChangingDateTime(changingDateTime);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public DateTime ChangingDateTime {get;private set;}
	private LocalDateTime ChangingDateTime = LocalDateTime.MIN;
	public final LocalDateTime getChangingDateTime()
	{
		return ChangingDateTime;
	}
	private void setChangingDateTime(LocalDateTime value)
	{
		ChangingDateTime = value;
	}
	@Override
	public String toString()
	{
		return getChangingDateTime().toString();
	}
}