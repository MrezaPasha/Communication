package Rasad.VideoSurveillance.Core.Helpers;

import Rasad.VideoSurveillance.Core.*;

public final class TRVSRegistry
{
	public static RegistryKey GetRVSMachineLevelKey()
	{
		// always use 32-bit registry
		try (RegistryKey softwareKey = RegistryKey.OpenBaseKey(RegistryHive.LocalMachine, RegistryView.Registry32).OpenSubKey("Software", true))
		{
			try (RegistryKey rasadKey = softwareKey.CreateSubKey("Rasad"))
			{
				return rasadKey.CreateSubKey("VideoSurveillance");
			}
		}
	}
}