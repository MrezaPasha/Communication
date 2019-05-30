package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.NotificationService.Services.TEventBehaviorActionAttribute;
import main.java.Rasad.Core.Services.NotificationService.Services.TEventBehaviorTriggerAttribute;

public interface IEventBehaviorService
{

	java.lang.Iterable<TEventBehaviorActionAttribute> getActionTypes();
	java.lang.Iterable<TEventBehaviorTriggerAttribute> getTriggerTypes();
	void RegisterAction(TEventBehaviorActionAttribute actionDefinition);
	void RegisterTrigger(TEventBehaviorTriggerAttribute triggerDefination);

}