package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents a disposable whose underlying disposable can be swapped for another disposable.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public sealed class MultipleAssignmentDisposable : IDisposable
public final class MultipleAssignmentDisposable implements Closeable
{
	private final Object gate = new Object();
	private Closeable current;
	private boolean disposed;

	/** 
		 Gets or sets the underlying disposable.
	 
	 
		 If the MutableDisposable has already been disposed, assignment to this property causes immediate disposal of
		 the given disposable object.
	 
	*/
	public Closeable getDisposable()
	{
		return current;
	}
	public void setDisposable(Closeable value)
	{
		boolean disposed = false;
		synchronized (gate)
		{
			disposed = this.disposed;
			if (!disposed)
			{
				current = value;
			}
		}
		if (disposed && (value != null))
		{
			value.Dispose();
		}
	}

	/** 
		 Gets a value that indicates whether the object is disposed.
	*/
	public boolean getIsDisposed()
	{
		synchronized (gate)
		{
			return disposed;
		}
	}

	/** 
		 Disposes the underlying disposable as well as all future replacements.
	*/
	public void close() throws IOException
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