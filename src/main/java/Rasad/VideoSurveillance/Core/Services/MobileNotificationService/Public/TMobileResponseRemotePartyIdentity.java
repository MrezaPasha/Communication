package Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public;

import ProtoBuf.*;
import Rasad.Communication.Core.*;
import Rasad.Core.*;
import Rasad.Core.Services.NotificationService.Public.*;
import Rasad.VideoSurveillance.Core.*;
import Rasad.VideoSurveillance.Core.Services.MobileNotificationService.*;
import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable][ProtoContract] public class TMobileResponseRemotePartyIdentity : TMobileMessageBase
public class TMobileResponseRemotePartyIdentity extends TMobileMessageBase implements Serializable
{
	private TMobileResponseRemotePartyIdentity()
	{
	}
	public TMobileResponseRemotePartyIdentity(EntityKeyAcceptStatus status, SystemEntity systemEntity, UUID entityKey)
	{
		this.setStatus(status);
		setReceivedSystemEntity(systemEntity);
		setReceivedEntityKey(entityKey);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(1)][DataMember] public EntityKeyAcceptStatus Status {get;private set;}
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
//ORIGINAL LINE: [ProtoMember(2)][DataMember] public SystemEntity ReceivedSystemEntity {get;private set;}
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
//ORIGINAL LINE: [ProtoMember(3)][DataMember] public Guid ReceivedEntityKey {get;private set;}
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
		return ("MobileResponseRemotePartyIdentity" + TSerializationHelper.JsonSerializeString(AnonymousType(getStatus().toString(), getReceivedSystemEntity().toString(), getReceivedEntityKey())));
	}
}