package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents a disposable whose underlying disposable can be swapped for another disposable which causes the previous
	 underlying disposable to be disposed.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public class SerialDisposable : IDisposable
public class SerialDisposable implements Closeable
{
	private final Object gate = new Object();
	private Closeable current;
	private boolean disposed;

	/** 
		 Gets or sets the underlying disposable.
	 
	 
		 If the ReplaceDisposable has already been disposed, assignment to this property causes immediate disposal of
		 the given disposable object.  Assigning this property disposes the previous disposable object.
	 
	*/
	public final Closeable getDisposable()
	{
		return current;
	}
	public final void setDisposable(Closeable value)
	{
		boolean disposed = false;
		Closeable current = null;
		synchronized (gate)
		{
			disposed = this.disposed;
			if (!disposed)
			{
				current = this.current;
				this.current = value;
			}
		}
		if (current != null)
		{
			current.Dispose();
		}
		if (disposed && (value != null))
		{
			value.Dispose();
		}
	}

	/** 
		 Gets a value that indicates whether the object is disposed.
	*/
	public final boolean getIsDisposed()
	{
		synchronized (gate)
		{
			return disposed;
		}
	}

	/** 
		 Disposes the underlying disposable as well as all future replacements.
	*/
	public final void close() throws IOException
	{
		Closeable current = null;
		synchronized (gate)
		{
			if (!disposed)
			{
				disposed = true;
				current = this.current;
				this.current = null;
			}
		}
		if (current != null)
		{
			current.Dispose();
		}
	}
}