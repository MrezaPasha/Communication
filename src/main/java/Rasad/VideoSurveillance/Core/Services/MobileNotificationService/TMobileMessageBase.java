package Rasad.VideoSurveillance.Core.Services.MobileNotificationService;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.VideoSurveillance.Core.*;
import java.io.*;

//[ProtoInclude(100, typeof(Rasad.Core.Services.NotificationService.Services.TNotifyServiceStateChanged))]

// ** For Each New Message: **
// 1. Add as protocol buffer message
// 2. Define packet type ID, same as protocol buffer id
// 3. Add serialization of packet in TMobileNotificationInitializer
// 4. Add deserialization of packet in TMobileNotificationInitializer
// 5. Add to mobile communication bridge in TMobileServiceCommunicationBridge
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoContract][DataContract][Serializable][ProtoInclude(TMobileMessageBase.PacketType_MobileResponseRemotePartyIdentity, typeof(Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public.TMobileResponseRemotePartyIdentity))][ProtoInclude(TMobileMessageBase.PacketType_MobileRequestConnectionPing, typeof(Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public.TMobileRequestConnectionPing))][ProtoInclude(TMobileMessageBase.PacketType_MobileNotifyRemotePartyIdentity, typeof(Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public.TMobileNotifyRemotePartyIdentity))][ProtoInclude(TMobileMessageBase.PacketType_MobileNotifyCameraMotionDetection, typeof(Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Camera.TMobileNotifyCameraMotionDetection))][ProtoInclude(TMobileMessageBase.PacketType_MobileShowPopupInClient, typeof(Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public.TMobileShowPopupInClient))][ProtoInclude(TMobileMessageBase.PacketType_MobileNotifySettingsChanged, typeof(Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public.TMobileNotifySettingsChanged))] public class TMobileMessageBase
public class TMobileMessageBase implements Serializable
{
	public TMobileMessageBase()
	{
		set_MessageTypeID(MessageTypeID.None);
		set_ServerID((byte)0);
		set_CameraID((short)-1);
		set_TargetUserID(0);
		set_NotDeserialized(false);
	}

	public TMobileMessageBase(short cameraId)
	{
		this();
		set_CameraID(cameraId);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMobileMessageBase(byte serverId)
	public TMobileMessageBase(byte serverId)
	{
		this();
		set_ServerID(serverId);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMobileMessageBase(byte serverId, short cameraId)
	public TMobileMessageBase(byte serverId, short cameraId)
	{
		this();
		set_ServerID(serverId);
		set_CameraID(cameraId);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMobileMessageBase(MessageTypeID messageTypeId, byte serverId, short cameraId, Int32 targetUserID, byte[] deserializedRawData)
	public TMobileMessageBase(MessageTypeID messageTypeId, byte serverId, short cameraId, int targetUserID, byte[] deserializedRawData)
	{
		this.SetMetaData(messageTypeId, serverId, cameraId, targetUserID);
		this.setDeserializedRawData(deserializedRawData);
		this.set_NotDeserialized(true);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetMetaData(MessageTypeID messageTypeId, byte serverId, short cameraId, Int32 targetUserID)
	public final void SetMetaData(MessageTypeID messageTypeId, byte serverId, short cameraId, int targetUserID)
	{
		this.set_MessageTypeID(messageTypeId);
		this.set_ServerID(serverId);
		this.set_CameraID(cameraId);
		this.set_TargetUserID(targetUserID);
	}

	// These 3 properties are not serialized and are not data members
	private boolean _NotDeserialized;
	public final boolean get_NotDeserialized()
	{
		return _NotDeserialized;
	}
	private void set_NotDeserialized(boolean value)
	{
		_NotDeserialized = value;
	}
	private MessageTypeID _MessageTypeID = MessageTypeID.values()[0];
	public final MessageTypeID get_MessageTypeID()
	{
		return _MessageTypeID;
	}
	protected final void set_MessageTypeID(MessageTypeID value)
	{
		_MessageTypeID = value;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte _ServerID;
	private byte _ServerID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte get_ServerID()
	public final byte get_ServerID()
	{
		return _ServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected void set_ServerID(byte value)
	protected final void set_ServerID(byte value)
	{
		_ServerID = value;
	}
	private short _CameraID;
	public final short get_CameraID()
	{
		return _CameraID;
	}
	protected final void set_CameraID(short value)
	{
		_CameraID = value;
	}
	private int _TargetUserID;
	public final int get_TargetUserID()
	{
		return _TargetUserID;
	}
	protected final void set_TargetUserID(int value)
	{
		_TargetUserID = value;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] DeserializedRawData;
	private byte[] DeserializedRawData;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getDeserializedRawData()
	public final byte[] getDeserializedRawData()
	{
		return DeserializedRawData;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setDeserializedRawData(byte[] value)
	private void setDeserializedRawData(byte[] value)
	{
		DeserializedRawData = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public int PacketType {get;set;}
	private int PacketType;
	public final int getPacketType()
	{
		return PacketType;
	}
	public final void setPacketType(int value)
	{
		PacketType = value;
	}

	// Packet Types used in json serialization/deserialization
	public static final int PacketType_MobileResponseRemotePartyIdentity = 100;
	public static final int PacketType_MobileRequestConnectionPing = 101;
	public static final int PacketType_MobileNotifyRemotePartyIdentity = 102;
	public static final int PacketType_MobileNotifyCameraMotionDetection = 103;
	public static final int PacketType_MobileShowPopupInClient = 104;
	public static final int PacketType_MobileNotifySettingsChanged = 105;
}