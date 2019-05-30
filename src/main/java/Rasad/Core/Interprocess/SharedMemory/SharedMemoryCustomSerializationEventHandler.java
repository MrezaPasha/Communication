package main.java.Rasad.Core.Interprocess.SharedMemory;

import Rasad.Core.*;

@FunctionalInterface
public interface SharedMemoryCustomSerializationEventHandler<T>
{
	void invoke(Object sender, SharedMemoryCustomSerializationEventArgs e);
}