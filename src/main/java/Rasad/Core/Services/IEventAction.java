package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;

public interface IEventAction
{
	UUID getActionInstanceKey();
	void Execute();
	void Start();
	TTriggerDataContext  getContext();
	void setContext(TTriggerDataContext value);
	void Stop();
}