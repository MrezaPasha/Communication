package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public interface IPluginCameraSettingEditor
{
	void LoadToEditor(ICameraInformation camera, String settingValue);
	String GetSettingXml();

}