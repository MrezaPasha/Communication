package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import Rasad.Core.TSerializationHelper;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceType))][KnownType(typeof(ServiceState))][DataContract][Serializable][ProtoContract] public class TResponseRemotePartyIdentity : TMessageBase
public class TResponseRemotePartyIdentity extends TMessageBase implements Serializable
{
	private TResponseRemotePartyIdentity()
	{
	}
	public TResponseRemotePartyIdentity(EntityKeyAcceptStatus status, SystemEntity systemEntity, UUID entityKey)
	{
		this.setStatus(status);
		setReceivedSystemEntity(systemEntity);
		setReceivedEntityKey(entityKey);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public EntityKeyAcceptStatus Status {get;private set;}
	private EntityKeyAcceptStatus Status = EntityKeyAcceptStatus.values()[0];
	public final EntityKeyAcceptStatus getStatus()
	{
		return Status;
	}
	private void setStatus(EntityKeyAcceptStatus value)
	{
		Status = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public SystemEntity ReceivedSystemEntity {get;private set;}
	private SystemEntity ReceivedSystemEntity = SystemEntity.values()[0];
	public final SystemEntity getReceivedSystemEntity()
	{
		return ReceivedSystemEntity;
	}
	private void setReceivedSystemEntity(SystemEntity value)
	{
		ReceivedSystemEntity = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public Guid ReceivedEntityKey {get;private set;}
	private UUID ReceivedEntityKey;
	public final UUID getReceivedEntityKey()
	{
		return ReceivedEntityKey;
	}
	private void setReceivedEntityKey(UUID value)
	{
		ReceivedEntityKey = value;
	}

	@Override
	public String toString()
	{
		class AnonymousType
		{
			public String Status;
			public String ReceivedSystemEntity;
			public java.util.UUID ReceivedEntityKey;

			public AnonymousType(String _Status, String _ReceivedSystemEntity, java.util.UUID _ReceivedEntityKey)
			{
				Status = _Status;
				ReceivedSystemEntity = _ReceivedSystemEntity;
				ReceivedEntityKey = _ReceivedEntityKey;
			}
		}
		return ("ResponseRemotePartyIdentity" + TSerializationHelper.JsonSerializeString(new AnonymousType(getStatus().toString(), getReceivedSystemEntity().toString(), getReceivedEntityKey())));
	}
}