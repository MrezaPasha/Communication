package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.NotificationService.Public.TNotifyChatMessage;

public interface IChatService
{
	void ClosePrivateChat(int receiver);
	void RemovePrivateChat(int receiver);

	void ShowPrivateChat(int receiver);
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: void ShowPrivateChat(int receiver, Rasad.Core.Services.NotificationService.Public.TNotifyChatMessage messageReceived = null);
	void ShowPrivateChat(int receiver, TNotifyChatMessage messageReceived);
}