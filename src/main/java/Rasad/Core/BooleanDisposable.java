package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents an IDisposable that can be checked for status.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public sealed class BooleanDisposable : IDisposable
public final class BooleanDisposable implements Closeable
{
	/** 
		 Gets a value that indicates whether the object is disposed.
	*/
	private boolean IsDisposed;
	public boolean getIsDisposed()
	{
		return IsDisposed;
	}
	private void setIsDisposed(boolean value)
	{
		IsDisposed = value;
	}

	/** 
		 Sets the status to Disposed.
	*/
	public void close() throws IOException
	{
		setIsDisposed(true);
	}
}