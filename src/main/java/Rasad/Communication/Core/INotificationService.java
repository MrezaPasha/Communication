package main.java.Rasad.Communication.Core;

import java.io.IOException;

public interface INotificationService<TMSG>
{
//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event NotificationMessageEventHandler<TMSG> NotificationRecieved;
	boolean SendMessage(TMSG... dataItems);

	void Start(String ipAddressOrHostName, int portNumber) throws Exception;

	void Stop() throws IOException;

//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event EventHandler ConnectedStateChanged;
}