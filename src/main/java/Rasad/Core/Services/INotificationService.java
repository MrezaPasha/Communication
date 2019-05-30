package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

public interface INotificationService
{
//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event NotificationMessageEventHandler NotificationRecieved;
	boolean SendMessage(TMessageBase... dataItems);

	void Start(String ipAddressOrHostName, int portNumber);

	void Stop();

//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event EventHandler ConnectedStateChanged;
}