package main.java.Rasad.Core.Services;

import Rasad.Core.Services.*;
import Rasad.Core.Services.TPluginCameraItem;
import Rasad.Core.*;
import main.java.Rasad.Core.Services.PluginService.TPluginDefination;
import main.java.Rasad.Core.Services.PluginService.TPluginWindowsServiceDefination;

public abstract class TPluginBase
{
	public final TPluginDefination getPluginInfo()
	{
		return this.getClass().Assembly.GetCustomAttributes(TPluginDefination.class, false).<TPluginDefination>OfType().FirstOrDefault();
	}
	public final TPluginWindowsServiceDefination[] getWindowsServices()
	{
		return this.getClass().Assembly.GetCustomAttributes(TPluginWindowsServiceDefination.class, false).<TPluginWindowsServiceDefination>OfType().ToArray();
	}

	public abstract void OnLoad();
	public abstract void OnUnload();
	public abstract void OnInstalling();
	public abstract void OnUninstalling();

	public abstract boolean getHasCameraAssignments();
	public abstract void SetCameraAssignment(TCameraAssignmentParams assignment);
	public abstract TPluginCameraItem[] GetCameraAssignments();
}