package Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents a disposable that only disposes its underlying disposable when all dependent disposables have been
	 disposed.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public sealed class RefCountDisposable : IDisposable
public final class RefCountDisposable implements Closeable
{
	private Object gate;
	private Closeable underlyingDisposable;
	private int count;
	private boolean isPrimaryDisposed;
	private boolean isUnderlyingDisposed;

	/** 
		 Initializes a new instance of the <see cref="T:System.Reactive.Disposables.RefCountDisposable" /> class with the
		 specified disposable.
	 
	 @param disposable Underlying disposable.
	*/
	public RefCountDisposable(Closeable disposable)
	{
		if (disposable == null)
		{
			throw new NullPointerException("disposable");
		}
		isUnderlyingDisposed = false;
		isPrimaryDisposed = false;
		gate = new Object();
		count = 0;
		underlyingDisposable = disposable;
	}

	/** 
		 Gets a value that indicates whether the object is disposed.
	*/
	public boolean getIsDisposed()
	{
		return isUnderlyingDisposed;
	}

	/** 
		 Disposes the underlying disposable only when all dependent disposables have been disposed.
	*/
	public void close() throws IOException
	{
		boolean flag = false;
		synchronized (gate)
		{
			if (!isUnderlyingDisposed && !isPrimaryDisposed)
			{
				isPrimaryDisposed = true;
				if (count == 0)
				{
					isUnderlyingDisposed = true;
					flag = true;
				}
			}
		}
		if (flag)
		{
			underlyingDisposable.Dispose();
		}
	}

	/** 
		 Returns a dependent disposable that when disposed decreases the refcount on the underlying disposable.
	 
	 @return A dependent disposable contributing to the reference count that manages the underlying disposable's lifetime.
	*/
	public Closeable GetDisposable()
	{
		synchronized (gate)
		{
			if (isUnderlyingDisposed)
			{
				return Disposable.getEmpty();
			}
			return new InnerDisposable(this);
		}
	}

	private final static class InnerDisposable implements Closeable
	{
		private RefCountDisposable disposable;
		private boolean isInnerDisposed;

		public InnerDisposable(RefCountDisposable disposable)
		{
			this.disposable = disposable;
			this.disposable.count++;
		}

		public void close() throws IOException
		{
			boolean flag = false;
			synchronized (disposable.gate)
			{
				if (!disposable.isUnderlyingDisposed && !isInnerDisposed)
				{
					isInnerDisposed = true;
					disposable.count--;
					if ((disposable.count == 0) && disposable.isPrimaryDisposed)
					{
						disposable.isUnderlyingDisposed = true;
						flag = true;
					}
				}
			}
			if (flag)
			{
				disposable.underlyingDisposable.Dispose();
			}
		}
	}
}