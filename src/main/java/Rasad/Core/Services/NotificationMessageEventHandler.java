package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

@FunctionalInterface
public interface NotificationMessageEventHandler
{
	void invoke(TMessageBase data);
}