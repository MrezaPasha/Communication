package main.java.Rasad.Core.Interprocess.SharedMemory;

import Rasad.Core.*;

@FunctionalInterface
public interface SharedMemoryNewMessageEventHandler<T>
{
	void invoke(Object sender, SharedMemoryNewMessageEventArgs e);
}