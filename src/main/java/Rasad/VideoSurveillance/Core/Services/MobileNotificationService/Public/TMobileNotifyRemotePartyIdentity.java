package Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public;

import ProtoBuf.*;
import Rasad.Communication.Core.*;
import Rasad.Core.Services.NotificationService.Public.*;
import Rasad.VideoSurveillance.Core.*;
import Rasad.VideoSurveillance.Core.Services.MobileNotificationService.*;
import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable][ProtoContract] public class TMobileNotifyRemotePartyIdentity : TMobileMessageBase
public class TMobileNotifyRemotePartyIdentity extends TMobileMessageBase implements Serializable
{
	public TMobileNotifyRemotePartyIdentity() // for protobuf, but must be public because of inheritance
	{
		setSystemEntity(SystemEntity.Other);
		setEntityKey(UUID.NewGuid());
		setLifetime(CommunicationEntityLifetime.Temporary);
		setEntityUserID(null);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMobileNotifyRemotePartyIdentity(SystemEntity systemEntity, Guid entityKey, CommunicationEntityLifetime lifetime, bool requestUnsentMessages, Nullable<byte> entityServerID, Nullable<Int32> entityUserID)
	public TMobileNotifyRemotePartyIdentity(SystemEntity systemEntity, UUID entityKey, CommunicationEntityLifetime lifetime, boolean requestUnsentMessages, Byte entityServerID, Integer entityUserID)
	{
		setSystemEntity(systemEntity);
		setEntityKey(entityKey);
		this.setLifetime(lifetime);
		this.setRequestUnsentMessages(requestUnsentMessages);
		this.setEntityServerID(entityServerID);
		this.setEntityUserID(entityUserID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public SystemEntity SystemEntity {get;private set;}
	private SystemEntity SystemEntity = getSystemEntity().values()[0];
	public final SystemEntity getSystemEntity()
	{
		return SystemEntity;
	}
	private void setSystemEntity(SystemEntity value)
	{
		SystemEntity = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Guid EntityKey {get;private set;}
	private UUID EntityKey;
	public final UUID getEntityKey()
	{
		return EntityKey;
	}
	private void setEntityKey(UUID value)
	{
		EntityKey = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public CommunicationEntityLifetime Lifetime {get;private set;}
	private CommunicationEntityLifetime Lifetime = CommunicationEntityLifetime.values()[0];
	public final CommunicationEntityLifetime getLifetime()
	{
		return Lifetime;
	}
	private void setLifetime(CommunicationEntityLifetime value)
	{
		Lifetime = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public bool RequestUnsentMessages {get;set;}
	private boolean RequestUnsentMessages;
	public final boolean getRequestUnsentMessages()
	{
		return RequestUnsentMessages;
	}
	public final void setRequestUnsentMessages(boolean value)
	{
		RequestUnsentMessages = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public Nullable<byte> EntityServerID {get;set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private Nullable<byte> EntityServerID;
	private Byte EntityServerID = null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<byte> getEntityServerID()
	public final Byte getEntityServerID()
	{
		return EntityServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setEntityServerID(Nullable<byte> value)
	public final void setEntityServerID(Byte value)
	{
		EntityServerID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public Nullable<Int32> EntityUserID {get;set;}
	private Integer EntityUserID = null;
	public final Integer getEntityUserID()
	{
		return EntityUserID;
	}
	public final void setEntityUserID(Integer value)
	{
		EntityUserID = value;
	}

	@Override
	public String toString()
	{
		return String.Concat("MobileNotifyRemotePartyIdentity - ", "Entity: ", getSystemEntity().toString(), ", Key: ", getEntityKey(), ", ServerID: ", getEntityServerID().ToStringSafe("N/A"), ", Lifetime: ", getLifetime().toString(), ", RequestUnsentMessage: ", getRequestUnsentMessages(), ", EntityUserID: ", getEntityUserID().ToStringSafe("N/A"));
	}
}