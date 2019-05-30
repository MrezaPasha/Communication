package main.java.Rasad.Core.Services.NotificationService.Public;


import main.java.Rasad.Communication.Core.CommunicationEntityLifetime;
import main.java.Rasad.Core.Services.NotificationService.Public.SystemEntity;

import java.util.*;
import java.io.*;

/** 
 When an entity connects to communication service, must introduce himself to the service (with any required information)
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(ServiceType))][KnownType(typeof(ServiceState))][DataContract][Serializable][ProtoContract] public class TNotifyCameraIdentity : TNotifyRemotePartyIdentity
public class TNotifyCameraIdentity extends TNotifyRemotePartyIdentity implements Serializable
{
	private TNotifyCameraIdentity()
	{
	} // for protobuf
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyCameraIdentity(SystemEntity systemEntity, Guid entityKey, CommunicationEntityLifetime lifetime, short cameraID, bool requestUnsentMessages, Nullable<byte> entityServerID)
	public TNotifyCameraIdentity(SystemEntity systemEntity, UUID entityKey, CommunicationEntityLifetime lifetime, short cameraID, boolean requestUnsentMessages, Byte entityServerID)
	{
		super(systemEntity, entityKey, lifetime, requestUnsentMessages, entityServerID);
		setCameraID(cameraID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(1)] public short CameraID {get;private set;}
	private short CameraID;
	public final short getCameraID()
	{
		return CameraID;
	}
	private void setCameraID(short value)
	{
		CameraID = value;
	}
	@Override
	public String toString()
	{
		return super.toString() + " - CameraID: " + getCameraID();
	}
}