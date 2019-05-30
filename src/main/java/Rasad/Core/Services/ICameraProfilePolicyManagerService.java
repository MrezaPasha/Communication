package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.ICameraInformation;
import main.java.Rasad.Core.Services.ICameraProfile;

public interface ICameraProfilePolicyManagerService
{
	//String GetProfileUrl(ICameraProfile profile, short cameraId);
	String GetProfileUrl(ICameraProfile profile, ICameraInformation cameraInfo);

	String GetProfileUrl(ICameraProfile profile, ICameraInformation cameraInfo, Rasad.Core.Services.CameraService.CameraProfileAccessPolicy
			accessPolicy);
}