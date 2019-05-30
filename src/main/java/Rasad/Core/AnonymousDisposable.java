package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents an Action-based disposable.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] internal sealed class AnonymousDisposable : IDisposable
public final class AnonymousDisposable implements Closeable
{
	private tangible.Action0Param dispose;
	private int isDisposed;

	/** 
		 Constructs a new disposable with the given action used for disposal.
	 
	 @param dispose Disposal action.
	*/
	public AnonymousDisposable(tangible.Action0Param dispose)
	{
		this.dispose = () -> dispose.invoke();
	}

	/** 
		 Calls the disposal action.
	*/
	public void close() throws IOException
	{
		tangible.RefObject<Integer> tempRef_isDisposed = new tangible.RefObject<Integer>(isDisposed);
		if (Interlocked.Exchange(tempRef_isDisposed, 1) == 0)
		{
		isDisposed = tempRef_isDisposed.argValue;
			dispose.invoke();
		}
	else
	{
		isDisposed = tempRef_isDisposed.argValue;
	}
	}
}