package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.PluginService.TPluginReportTab;
import main.java.Rasad.Core.Services.PluginService.TPluginWindowsServiceDefination;
import main.java.Rasad.Core.Services.PluginService.TSystemSettingsTab;

import java.util.*;

public interface IPluginService
{
	String GetInstallationFolder(UUID pluginKey);

	void RegisterSystemSettingdTab(TSystemSettingsTab systemSettingsTab);
	void UnRegisterSystemSettingdTab(UUID tabKey);

	boolean getIsServerMode();
	void setIsServerMode(boolean value);
	java.lang.Iterable<TSystemSettingsTab> getSystemSettingTabs();
	java.lang.Iterable<TPluginWindowsServiceDefination> getWindowsServices();

//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event EventHandler SystemSettingTabsChanged;
//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event EventHandler<TPluginRequireServiceEventArgs> PluginRequireService;

	void LoadPluginsFromDatabase();

	//TODO MRREPLACE
	//Task LoadPluginsFromDatabaseAsync(java.util.UUID pluginKey);
	public void LoadPluginsFromDatabaseAsync(java.util.UUID pluginKey);
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: Task LoadPluginsFromDatabaseAsync(Guid pluginKey, bool showProgress = false);
	//Task LoadPluginsFromDatabaseAsync(UUID pluginKey, boolean showProgress);
	//TODO MRREPLACE
	public void LoadPluginsFromDatabaseAsync(UUID pluginKey, boolean showProgress);

	java.lang.Iterable<TPluginReportTab> getPluginReportTabs();
	void RegisterPluginReportTab(TPluginReportTab pluginReportTab);
	void UnRegisterPluginReportTab(UUID pluginReportTabKey);

//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event EventHandler SystemReportTabsChanged;

	void RegisterWindowsService(TPluginWindowsServiceDefination pluginWindowsService);
	void UnRegisterWindowsService(UUID pluginWindowsServiceKey);

//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event EventHandler WindowsServicesChanged;

	boolean IsCameraCheckedForService(UUID pluginWindowsServiceKey, ICameraInformation camera);

	Object RequestService(TPluginRequestServiceParams parameters);
}