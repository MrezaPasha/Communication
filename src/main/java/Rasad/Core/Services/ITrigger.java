package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public interface ITrigger extends Closeable
{
	UUID getKey();
	void Start();
	void Stop();
	boolean IsTriggerStateOn(); // only for triggers of type StateTrigger

	TTriggerDataContext getContext();
	void setContext(TTriggerDataContext value);
	TriggerType getTriggerType();

//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event TTrigerEventHandler Fire;

}