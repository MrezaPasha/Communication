package main.java.Rasad.Core.Services.NotificationService.Public;


import main.java.Rasad.Communication.Core.CommunicationEntityLifetime;
import main.java.Rasad.Core.Services.NotificationService.Public.SystemEntity;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;
import java.io.*;

/** 
 When an entity connects to communication service, must introduce himself to the service (with any required information)
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceType))][KnownType(typeof(ServiceState))][DataContract][Serializable][ProtoContract][ProtoInclude(501, typeof(TNotifyCameraIdentity))] public class TNotifyRemotePartyIdentity : TMessageBase
public class TNotifyRemotePartyIdentity extends TMessageBase implements Serializable
{
	public TNotifyRemotePartyIdentity() // for protobuf, but must be public because of inheritance
	{
		setSystemEntity(SystemEntity.Other);
		setEntityKey(UUID.randomUUID());
		setLifetime(CommunicationEntityLifetime.Temporary);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyRemotePartyIdentity(SystemEntity systemEntity, Guid entityKey, CommunicationEntityLifetime lifetime, bool requestUnsentMessages, Nullable<byte> entityServerID)
	public TNotifyRemotePartyIdentity(SystemEntity systemEntity, UUID entityKey, CommunicationEntityLifetime lifetime, boolean requestUnsentMessages, Byte entityServerID)
	{
		setSystemEntity(systemEntity);
		setEntityKey(entityKey);
		this.setLifetime(lifetime);
		this.setRequestUnsentMessages(requestUnsentMessages);
		this.setEntityServerID(entityServerID);
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

	@Override
	public String toString()
	{
		//TODO MRREPLACE
		//return String.Concat("Entity: ", getSystemEntity().toString(), ", Key: ", getEntityKey(), ", ServerID: ", getEntityServerID().ToStringSafe("N/A"), ", Lifetime: ", getLifetime().toString(), ", RequestUnsentMessage: ", getRequestUnsentMessages());
		String s = "Entity: " +  getSystemEntity().toString() +  ", Key: " +  getEntityKey() +  ", ServerID: " +  getEntityServerID()+ ", Lifetime: " + getLifetime().toString() + ", RequestUnsentMessage: " + getRequestUnsentMessages();
		return s;
	}
}