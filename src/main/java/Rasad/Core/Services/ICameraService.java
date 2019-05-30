package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;

public interface ICameraService
{
	java.lang.Iterable<ICameraInformation> getAllCameras();
	java.lang.Iterable<ICameraInformation> getActiveCameras();
	java.lang.Iterable<ICameraInformation> getActiveAndViewAccessedCameras();

//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event CameraEventHandler NetworkAvailablityChanged;
//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event CameraEventHandler MotionDetectionChanged;
//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event CameraEventHandler RecordStateChanged;

	void RegisterCameraEditorTab(Rasad.Core.Services.CameraService.TCameraEditorTab cameraEditorTab);
	void UnRegisterCameraEditorTab(UUID tabKey);
	void ShowLivePopup(String message, String sender, short[] camerasID);
	void ShowLiveViewForClients(ICameraInformation camera);

	String GetCameraPluginSetting(int cameraID, UUID pluginTabKey);

	java.lang.Iterable<Rasad.Core.Services.CameraService.TCameraEditorTab> getCameraEditorTabs();
}