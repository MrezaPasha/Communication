package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents a thread-affine IDisposable.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public sealed class ContextDisposable : IDisposable
public final class ContextDisposable implements Closeable
{
	private Closeable disposable;
	private int disposed;

	/** 
		 Initializes a new instance of the <see cref="T:System.Reactive.Disposables.ContextDisposable" /> class that uses a
		 SynchronizationContext on which to dispose the disposable.
	 
	 @param context Context to perform disposal on.
	 @param disposable Disposable whose Dispose operation to run on the given synchronization context.
	*/
	public ContextDisposable(SynchronizationContext context, Closeable disposable)
	{
		if (context == null)
		{
			throw new NullPointerException("context");
		}
		if (disposable == null)
		{
			throw new NullPointerException("disposable");
		}
		setContext(context);
		this.disposable = disposable;
	}

	/** 
		 Gets the provided SynchronizationContext.
	*/
	private SynchronizationContext Context;
	public SynchronizationContext getContext()
	{
		return Context;
	}
	private void setContext(SynchronizationContext value)
	{
		Context = value;
	}

	/** 
		 Gets a value that indicates whether the object is disposed.
	*/
	public boolean getIsDisposed()
	{
		return (disposed == 1);
	}

	/** 
		 Disposes the wrapped disposable on the provided SynchronizationContext.
	*/
	public void close() throws IOException
	{
		tangible.RefObject<Integer> tempRef_disposed = new tangible.RefObject<Integer>(disposed);
		if (Interlocked.Exchange(tempRef_disposed, 1) == 0)
		{
		disposed = tempRef_disposed.argValue;
			getContext().Post(() ->
			{
					disposable.Dispose();
			}, null);
		}
	else
	{
		disposed = tempRef_disposed.argValue;
	}
	}
}