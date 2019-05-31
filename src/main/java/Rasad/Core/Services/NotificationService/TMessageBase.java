package main.java.Rasad.Core.Services.NotificationService;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.MessageTypeID;
import main.java.Rasad._core.GenericProtobuf;

import java.io.*;

//[ProtoInclude(107, typeof(Rasad.Core.Services.NotificationService.Camera.TNotifyCameraMotionDetection))]
//[ProtoInclude(111, typeof(Rasad.Core.Services.NotificationService.Camera.TRequestCameraMotionDetectionState))]

//[ProtoInclude(114, typeof(Rasad.Core.Services.NotificationService.Recording.TNotifyCameraRecordingStatusChanged))]
//[ProtoInclude(115, typeof(Rasad.Core.Services.NotificationService.Recording.TNotifyNewRecordFileSaved))]
//[ProtoInclude(131, typeof(Rasad.Core.Services.NotificationService.Public.TRequestConnectionPing))]
//[ProtoInclude(132, typeof(Rasad.Core.Services.NotificationService.Public.TResponseConnectionPing))]
//[ProtoInclude(133, typeof(Rasad.Core.Services.NotificationService.Camera.TRequestCameraCurrentRecordingStatus))]
//[ProtoInclude(134, typeof(Rasad.Core.Services.NotificationService.Camera.TResponseCameraCurrentRecordingStatus))]
//[ProtoInclude(138, typeof(Rasad.Core.Services.NotificationService.MPA.TRequestMPAElementStatus))]
//[ProtoInclude(139, typeof(Rasad.Core.Services.NotificationService.MPA.TResponseMPAElementStatus))]
//[ProtoInclude(141, typeof(Rasad.Core.Services.NotificationService.GuardTour.TNotifyGuardTourSuspend))]











//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoContract][DataContract][Serializable][ProtoInclude(100, typeof(Rasad.Core.Services.NotificationService.Services.TNotifyServiceStateChanged))][ProtoInclude(101, typeof(Rasad.Core.Services.BackupRestore.TNotifyActionProgressed))][ProtoInclude(102, typeof(Rasad.Core.Services.BackupRestore.TRequestBackupFileList))][ProtoInclude(103, typeof(Rasad.Core.Services.BackupRestore.TRequestDeleteBackupFile))][ProtoInclude(104, typeof(Rasad.Core.Services.BackupRestore.TRequestStartBackup))][ProtoInclude(105, typeof(Rasad.Core.Services.BackupRestore.TRequestStartRestore))][ProtoInclude(106, typeof(Rasad.Core.Services.BackupRestore.TResponseBackupFileList))][ProtoInclude(108, typeof(Rasad.Core.Services.NotificationService.Camera.TNotifyCameraNetworkAvailabilityChanged))][ProtoInclude(109, typeof(Rasad.Core.Services.NotificationService.Camera.TNotifyCameraRecordingStateChanged))][ProtoInclude(110, typeof(Rasad.Core.Services.NotificationService.Camera.TNotifyCameraSettingsChanged))][ProtoInclude(112, typeof(Rasad.Core.Services.NotificationService.TNotifyGuardTourChanged))][ProtoInclude(113, typeof(Rasad.Core.Services.NotificationService.Public.TShowPopupInClient))][ProtoInclude(116, typeof(Rasad.Core.Services.NotificationService.Recording.TRequestRecordingStatus))][ProtoInclude(117, typeof(Rasad.Core.Services.NotificationService.Recording.TRequestStopRecordingProcess))][ProtoInclude(118, typeof(Rasad.Core.Services.NotificationService.Recording.TResponseRecordingStatus))][ProtoInclude(119, typeof(Rasad.Core.Services.NotificationService.Server.TNotifyServerSettingsChanged))][ProtoInclude(120, typeof(Rasad.Core.Services.NotificationService.Server.TRequestServerDateTime))][ProtoInclude(121, typeof(Rasad.Core.Services.NotificationService.Server.TResponseServerDateTime))][ProtoInclude(122, typeof(Rasad.Core.Services.NotificationService.Services.TRequestInstallService))][ProtoInclude(124, typeof(Rasad.Core.Services.NotificationService.Services.TRequestUninstallService))][ProtoInclude(125, typeof(Rasad.Core.Services.NotificationService.Services.TRequestUpdateDriveSizes))][ProtoInclude(126, typeof(Rasad.Core.Services.NotificationService.Services.TResponseUpdateDriveSizes))][ProtoInclude(127, typeof(Rasad.Core.Services.NotificationService.TNotificationServerChanged))][ProtoInclude(128, typeof(Rasad.Core.Services.NotificationService.WorkflowService.TEventBehaviorItemsChanged))][ProtoInclude(129, typeof(Rasad.Core.Services.NotificationService.WorkflowService.TEventBehaviorPlaySoundInClient))][ProtoInclude(130, typeof(Rasad.Core.Services.NotificationService.Public.TNotifyRemotePartyIdentity))][ProtoInclude(135, typeof(Rasad.Core.Services.NotificationService.Public.TRequestServerRuntimeInfomation))][ProtoInclude(136, typeof(Rasad.Core.Services.NotificationService.Public.TResponseServerRuntimeInfomation))][ProtoInclude(137, typeof(Rasad.Core.Services.NotificationService.MPA.TRequestStopMPAElementProcess))][ProtoInclude(140, typeof(Rasad.Core.Services.NotificationService.Public.TNotifyNewEvent))][ProtoInclude(142, typeof(Rasad.Core.Services.NotificationService.Services.TRequestLicenseMessage))][ProtoInclude(143, typeof(Rasad.Core.Services.NotificationService.Services.TLicenseContentMessage))][ProtoInclude(144, typeof(Rasad.Core.Services.NotificationService.Public.TShowLivePopupInClient))][ProtoInclude(145, typeof(Rasad.Core.Services.NotificationService.Public.TRequestOnlineUsers))][ProtoInclude(146, typeof(Rasad.Core.Services.NotificationService.Public.TNotifyOnlineUser))][ProtoInclude(147, typeof(Rasad.Core.Services.NotificationService.Plugin.TPluginMessage))][ProtoInclude(148, typeof(Rasad.Core.Services.NotificationService.Streamer.TNotifyCameraStreamerAddressChange))][ProtoInclude(149, typeof(Rasad.Core.Services.NotificationService.Public.TNotifyGlobalSettingsChanged))][ProtoInclude(150, typeof(Rasad.Core.Services.NotificationService.Camera.TNotifyCommandPTZ))][ProtoInclude(151, typeof(Rasad.Core.Services.NotificationService.Security.TNotifySecuritySettingsChanged))][ProtoInclude(152, typeof(Rasad.Core.Services.NotificationService.Public.TNotifyChatMessage))][ProtoInclude(153, typeof(Rasad.Core.Services.NotificationService.DesktopCollabration.TResponseDesktopCollabration))][ProtoInclude(154, typeof(Rasad.Core.Services.NotificationService.DesktopCollabration.TRequestDesktopCollabration))][ProtoInclude(155, typeof(Rasad.Core.Services.NotificationService.Public.TNotifyLogoutUser))][ProtoInclude(156, typeof(Rasad.Core.Services.NotificationService.DesktopCollabration.TRequestCloseRemoteServer))][ProtoInclude(157, typeof(Rasad.Core.Services.NotificationService.DesktopCollabration.TRequestCloseRemoteClient))][ProtoInclude(158, typeof(Rasad.Core.Services.NotificationService.Services.TRequestOpenLicenseContent))][ProtoInclude(159, typeof(Rasad.Core.Services.NotificationService.Services.TResponseReadLicenseContent))][ProtoInclude(160, typeof(Rasad.Core.Services.NotificationService.Services.TResponseReadLicenseFailed))][ProtoInclude(161, typeof(Rasad.Core.Services.NotificationService.Services.TRequestInstallNewLicense))][ProtoInclude(162, typeof(Rasad.Core.Services.NotificationService.Services.TRequestRestartService))][ProtoInclude(163, typeof(Rasad.Core.Services.NotificationService.Services.TResponseLicenseInstalled))][ProtoInclude(164, typeof(TMessageRequest))][ProtoInclude(165, typeof(TMessageResponse))][ProtoInclude(166, typeof(Rasad.Core.Services.NotificationService.Security.SecurityActionType))][ProtoInclude(167, typeof(Rasad.Core.Services.NotificationService.Server.ServerActionType))][ProtoInclude(168, typeof(Rasad.Core.Services.NotificationService.Public.TNotifyServiceEditedByClient))][ProtoInclude(169, typeof(Rasad.Core.Services.NotificationService.Public.ServiceActionTypeEnum))][ProtoInclude(170, typeof(Rasad.Core.Services.NotificationService.IOService.TIOMessageBase))][ProtoInclude(171, typeof(Rasad.Core.Services.NotificationService.Public.TUserLoginedToSystem))][ProtoInclude(172, typeof(Rasad.Core.Services.NotificationService.IOService.GSM.TSMSReceivedMessage))][ProtoInclude(173, typeof(Rasad.Core.Services.NotificationService.Services.TRequestLockSerialNumber))][ProtoInclude(174, typeof(Rasad.Core.Services.NotificationService.Services.TResponseLockSerialNumber))][ProtoInclude(175, typeof(Rasad.Core.Services.NotificationService.TNotifyRecordedTourChanged))][ProtoInclude(176, typeof(Rasad.Core.Services.NotificationService.Camera.TNotifyCameraGroupSettingsChanged))][ProtoInclude(177, typeof(Rasad.Core.Services.NotificationService.Server.TRequestChangeServerDateTime))][ProtoInclude(178, typeof(Rasad.Core.Services.NotificationService.Public.TResponseRemotePartyIdentity))][ProtoInclude(179, typeof(Rasad.Core.Services.NotificationService.Streamer.TNotifyCameraProfileAccess))][ProtoInclude(180, typeof(Rasad.Core.Services.NotificationService.Streamer.TNotifyCameraStreamerReset))][ProtoInclude(181, typeof(Rasad.Core.Services.NotificationService.Motion.TNotifyCameraMotionProcessorFailed))][ProtoInclude(182, typeof(Rasad.Core.Services.NotificationService.Server.TNotifyServerGroupSettingsChanged))][ProtoInclude(183, typeof(Rasad.Core.Services.NotificationService.Map.TNotifyMapEdit))][ProtoInclude(184, typeof(Rasad.Core.Services.NotificationService.Map.TNotifyMapAction))][ProtoInclude(185, typeof(Rasad.Core.Services.NotificationService.Server.TRequestSyncServers))][ProtoInclude(186, typeof(Rasad.Core.Services.NotificationService.Recording.TRequestRecordingCleanup))][ProtoInclude(187, typeof(Rasad.Core.Services.NotificationService.Recording.TNotifyFailoverRecordingsChange))][ProtoInclude(188, typeof(Rasad.Core.Services.NotificationService.Server.TNotifyFailoverSettingsChanged))][ProtoInclude(189, typeof(Rasad.Core.Services.NotificationService.Recording.TNotifyCameraStreamOperation))][ProtoInclude(190, typeof(Rasad.Core.Services.NotificationService.Camera.TNotifyPTZStatus))][ProtoInclude(191, typeof(Rasad.VideoSurveillance.Core.Services.NotificationService.NetworkStorage.TNotifyNetworkStorageChange))][ProtoInclude(192, typeof(Rasad.VideoSurveillance.Core.Services.NotificationService.Investigations.TNotifyInvestigationCommand))][ProtoInclude(193, typeof(Rasad.VideoSurveillance.Core.Services.NotificationService.EdgeStorage.TNotifyEdgeStorageCommand))][ProtoInclude(194, typeof(Rasad.Core.Services.NotificationService.Public.TNotifyCameraViewGroupSettingsChanged))][ProtoInclude(195, typeof(Rasad.Core.Services.NotificationService.Recording.TNotifyRecordingStorageChanged))][ProtoInclude(196, typeof(Rasad.Core.Services.NotificationService.Recording.TQueryRecordingStorageStats))][ProtoInclude(197, typeof(Rasad.Core.Services.NotificationService.Recording.TResponseRecordingStorageStats))][ProtoInclude(198, typeof(Rasad.Core.Services.NotificationService.Failover.TNotifyFailoverState))] public class TMessageBase
public class TMessageBase  extends GenericProtobuf<TMessageBase> implements Serializable
{
	public TMessageBase()
	{
		set_MessageTypeID(MessageTypeID.None);
		set_ServerID((byte)0);
		set_CameraID((short)-1);
		set_NotDeserialized(false);
	}

	public TMessageBase(short cameraId)
	{
		this();
		set_CameraID(cameraId);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMessageBase(byte serverId)
	public TMessageBase(byte serverId)
	{
		this();
		set_ServerID(serverId);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMessageBase(byte serverId, short cameraId)
	public TMessageBase(byte serverId, short cameraId)
	{
		this();
		set_ServerID(serverId);
		set_CameraID(cameraId);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMessageBase(MessageTypeID messageTypeId, byte serverId, short cameraId, byte[] deserializedRawData)
	public TMessageBase(MessageTypeID messageTypeId, byte serverId, short cameraId, byte[] deserializedRawData)
	{
		this.SetMetaData(messageTypeId, serverId, cameraId);
		this.setDeserializedRawData(deserializedRawData);
		this.set_NotDeserialized(true);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetMetaData(MessageTypeID messageTypeId, byte serverId, short cameraId)
	public final void SetMetaData(MessageTypeID messageTypeId, byte serverId, short cameraId)
	{
		this.set_MessageTypeID(messageTypeId);
		this.set_ServerID(serverId);
		this.set_CameraID(cameraId);
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
}