package main.java.Rasad.Core.Services;

import Rasad.Core.Services.*;
import Rasad.Core.*;

public interface IJoystickService
{
	ICameraInformation getActiveCamera();
	void setActiveCamera(ICameraInformation value);
//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event EventHandler ActiveCameraChanged;
}