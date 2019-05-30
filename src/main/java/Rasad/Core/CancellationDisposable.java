package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents an IDisposable that can be checked for cancellation status.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public sealed class CancellationDisposable : IDisposable
public final class CancellationDisposable implements Closeable
{
	private CancellationTokenSource cts;

	/** 
		 Initializes a new instance of the <see cref="T:System.Reactive.Disposables.CancellationDisposable" /> class that
		 uses a new CancellationTokenSource.
	*/
	public CancellationDisposable()
	{
		this(new CancellationTokenSource());
	}

	/** 
		 Initializes a new instance of the <see cref="T:System.Reactive.Disposables.CancellationDisposable" /> class that
		 uses an existing CancellationTokenSource.
	 
	 @param cts CancellationTokenSource used for cancellation.
	*/
	public CancellationDisposable(CancellationTokenSource cts)
	{
		if (cts == null)
		{
			throw new NullPointerException("cts");
		}
		this.cts = cts;
	}

	/** 
		 Gets a value that indicates whether the object is disposed.
	*/
	public boolean getIsDisposed()
	{
		return cts.IsCancellationRequested;
	}

	/** 
		 Gets the CancellationToken used by this CancellationDisposable.
	*/
	public CancellationToken getToken()
	{
		return cts.Token;
	}

	/** 
		 Cancels the CancellationTokenSource.
	*/
	public void close() throws IOException
	{
		cts.Cancel();
	}
}