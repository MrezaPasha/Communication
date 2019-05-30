package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents a disposable that does nothing on disposal.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] internal sealed class DefaultDisposable : IDisposable
public final class DefaultDisposable implements Closeable
{
	/** 
		 Singleton default disposable.
	*/
	public static final DefaultDisposable Instance = new DefaultDisposable();

	private DefaultDisposable()
	{
	}

	/** 
		 Does nothing.
	*/
	public void close() throws IOException
	{
	}
}