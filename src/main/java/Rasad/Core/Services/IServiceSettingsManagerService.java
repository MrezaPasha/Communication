package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.CameraService.CameraDesignTimeProfileSelectionPolicy;

public interface IServiceSettingsManagerService
{
	Rasad.Core.Services.CameraService.CameraProfileAccessPolicy getProfileAccessPolicy();
	CameraDesignTimeProfileSelectionPolicy getDesignTimeProfileSelectionPolicy();
	void Reload();
}