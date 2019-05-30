package main.java.Rasad.Core;

import main.java.Rasad.Core.AnonymousDisposable;
import main.java.Rasad.Core.DefaultDisposable;

import java.util.*;
import java.io.*;

/** 
	 Provides a set of static methods for creating Disposables.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public static class Disposable
public final class Disposable
{
	/** 
		 Gets the disposable that does nothing when disposed.
	*/
	public static Closeable getEmpty()
	{
		return DefaultDisposable.Instance;
	}

	/** 
		 Creates the disposable that invokes the specified action when disposed.
	 
	 @param dispose Action to run during IDisposable.Dispose.
	 @return The disposable object that runs the given action upon disposal.
	*/
	public static Closeable Create(tangible.Action0Param dispose)
	{
		if (dispose == null)
		{
			throw new NullPointerException("dispose");
		}
		return new AnonymousDisposable(dispose);
	}
}