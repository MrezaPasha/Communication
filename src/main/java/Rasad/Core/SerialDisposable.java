package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public sealed class SerialDisposable<T> : IDisposable where T : IDisposable
public final class SerialDisposable<T extends Closeable> implements Closeable
{
	private final Object sync = new Object();
	private T m_instance;
	private boolean isDisposed;
	public boolean getisDisposed()
	{
		return isDisposed;
	}
	private void setisDisposed(boolean value)
	{
		isDisposed = value;
	}

	public T getinstance()
	{
		return m_instance;
	}
	public void setinstance(T value)
	{
		Closeable toDispose = null;
		synchronized (sync)
		{
			if (getisDisposed())
			{
				toDispose = value;
			}
			else
			{
				toDispose = m_instance;
				m_instance = value;
			}
		}
		if (toDispose != null)
		{
			toDispose.Dispose();
		}
	}

	public void close() throws IOException
	{
		Closeable toDispose = null;
		synchronized (sync)
		{
			setisDisposed(true);
			toDispose = m_instance;
			m_instance = null;
		}
		if (toDispose != null)
		{
			toDispose.Dispose();
		}
	}
}