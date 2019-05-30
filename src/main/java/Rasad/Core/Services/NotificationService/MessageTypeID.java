package main.java.Rasad.Core.Services.NotificationService;

import Rasad.Core.*;
import Rasad.Core.Services.*;

// ****
// ONLY add message id for struct message types
// ****
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum MessageTypeID : byte
public enum MessageTypeID 
{
	None((byte)0),
	NotifyCameraMotionDetection((byte)1), // TNotifyCameraMotionDetectionStruct
	NotifyNewRecordFileSaved((byte)2), // TNotifyNewRecordFileSavedStruct
	RequestConnectionPing((byte)3), // TRequestConnectionPingStruct
	ResponseConnectionPing((byte)4), // TResponseConnectionPingStruct
	RequestCameraCurrentRecordingStatus((byte)5), // TRequestCameraCurrentRecordingStatusStruct
	ResponseCameraCurrentRecordingStatus((byte)6), // TResponseCameraCurrentRecordingStatusStruct
	RequestCameraMotionDetectionState((byte)7), // TRequestCameraMotionDetectionStateStruct
	RequestMPAElementStatus((byte)8), // TRequestMPAElementStatusStruct
	ResponseMPAElementStatus((byte)9), // TResponseMPAElementStatusStruct
	SendMail((byte)10);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, MessageTypeID> mappings;
	private static java.util.HashMap<Byte, MessageTypeID> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MessageTypeID.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, MessageTypeID>();
				}
			}
		}
		return mappings;
	}

	private MessageTypeID(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static MessageTypeID forValue(byte value)
	{
		return getMappings().get(value);
	}
}