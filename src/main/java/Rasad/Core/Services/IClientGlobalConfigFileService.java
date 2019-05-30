package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public interface IClientGlobalConfigFileService
{
	String ReadConfigValue(String configKey);

	boolean getUseExternalPlayerProcess();
}