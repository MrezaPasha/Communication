package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public interface ILiveViewService
{

	void ShowLivePopup(String message, String sender, short[] camerasID);

	void ShowLiveViewForClients(ICameraInformation camera);
}