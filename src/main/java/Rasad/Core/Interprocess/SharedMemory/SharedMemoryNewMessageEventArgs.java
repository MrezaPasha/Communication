package main.java.Rasad.Core.Interprocess.SharedMemory;

import Rasad.Core.*;

public class SharedMemoryNewMessageEventArgs<T> extends tangible.EventArgs
{
	public SharedMemoryNewMessageEventArgs(T message)
	{
		this.setMessage(message);
	}

	private T Message;
	public final T getMessage()
	{
		return Message;
	}
	private void setMessage(T value)
	{
		Message = value;
	}
}