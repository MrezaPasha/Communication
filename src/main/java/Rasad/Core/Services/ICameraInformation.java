package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.time.*;

public interface ICameraInformation
{
	short getCameraID();
	String getCameraTitle();
	void setCameraTitle(String value);
	String getCameraTitleWithIdentity();
	String getCameraIdentity();
	void setCameraIdentity(String value);

	java.lang.Iterable<ICameraProfile> getProfiles();
	ICameraProfile getDefaultProfile();

	String getUsername();
	void setUsername(String value);

	String getPassword();
	void setPassword(String value);
	boolean getUseHttps();
	void setUseHttps(boolean value);

	ICameraProfile GetNearestResolutionProfile(int width);

	boolean getSupportPTZ();
	void setSupportPTZ(boolean value);

	Rasad.VideoSurveillance.Core.MotionDetectionMechanism getMotionDetectionMechanism();
	void setMotionDetectionMechanism(Rasad.VideoSurveillance.Core.MotionDetectionMechanism value);
	String getMotionDetectionSettings();
	void setMotionDetectionSettings(String value);
	String getCameraMotionDetectionEventSettings();
	void setCameraMotionDetectionEventSettings(String value);

	String getDisplayProfileSmall();
	void setDisplayProfileSmall(String value);
	String getDisplayProfileMedium();
	void setDisplayProfileMedium(String value);
	String getDisplayProfileLarge();
	void setDisplayProfileLarge(String value);
	String getRemoteDisplayProfileSmall();
	void setRemoteDisplayProfileSmall(String value);
	String getRemoteDisplayProfileMedium();
	void setRemoteDisplayProfileMedium(String value);
	String getRemoteDisplayProfileLarge();
	void setRemoteDisplayProfileLarge(String value);

	boolean getIsPlayingSound();
	void setIsPlayingSound(boolean value);
	boolean getIsSendingSound();
	void setIsSendingSound(boolean value);

	boolean getIsAvailable();
	void setIsAvailable(boolean value);
	LocalDateTime getIsAvailableTime();
	void setIsAvailableTime(LocalDateTime value);

	boolean getIsMotionDetecting();
	void setIsMotionDetecting(boolean value);

	boolean getIsRecording();
	void setIsRecording(boolean value);
	String getAddress();
	String getMacAddress();
	String getSerialNumber();

	TransportType getTransportType();
	void setTransportType(TransportType value);
	Rasad.VideoSurveillance.Core.StreamerMethod getStreamerMethod();
	void setStreamerMethod(Rasad.VideoSurveillance.Core.StreamerMethod value);
	TransportType getStreamerTransportType();
	void setStreamerTransportType(TransportType value);
	int getStreamerLatency();
	void setStreamerLatency(int value);
	int getStreamerTcpTimeout();
	void setStreamerTcpTimeout(int value);
	int getLiveLatency();
	void setLiveLatency(int value);
	ArrayList<TPluginSetting> getPluginSettings();

	boolean getCanViewPlayBack();

	boolean getAllowPTZ();

	boolean getSupportPreset();

	boolean getSupportFishEye();
	boolean getSupportFaceProcessing();

	boolean getIsFederated();

	short getRemoteCameraID();
	boolean getSupportPrivacyMask();
	String getPrivacyMaskSettings();
	void setPrivacyMaskSettings(String value);

	Byte getServerID();
	void setServerID(Byte value);
	boolean getLiveMulticast();
	void setLiveMulticast(boolean value);
	boolean getMulticastConnection();

	boolean getIsHttpCamera();
	Rasad.VideoSurveillance.Core.PtzOperationMode getPtzOperationMode();
	void setPtzOperationMode(Rasad.VideoSurveillance.Core.PtzOperationMode value);
}