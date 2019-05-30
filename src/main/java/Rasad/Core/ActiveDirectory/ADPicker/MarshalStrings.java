package main.java.Rasad.Core.ActiveDirectory.ADPicker;

import Rasad.Core.*;
import java.io.*;

public final class MarshalStrings implements Closeable
{
	private IntPtr _taskAlloc = System.IntPtr.Zero;
	private int _length;
	private IntPtr[] _strings;
	private boolean _disposed;

	public IntPtr getArrayPtr()
	{
		return this._taskAlloc;
	}

	public MarshalStrings(String[] theArray)
	{
		int size = IntPtr.Size;
		if (theArray == null)
		{
			return;
		}
		this._length = theArray.length;
		this._strings = new IntPtr[this._length];
		this._taskAlloc = Marshal.AllocCoTaskMem(this._length * size);
		for (int index = this._length - 1; index >= 0; --index)
		{
			this._strings[index] = Marshal.StringToCoTaskMemUni(theArray[index]);
			Marshal.WriteIntPtr(this._taskAlloc, index * size, this._strings[index]);
		}
	}

	protected void finalize() throws Throwable
	{
		this.close();
	}

	public void close() throws IOException
	{
		if (!this._disposed)
		{
			if (System.IntPtr.OpInequality(this._taskAlloc, IntPtr.Zero))
			{
				Marshal.FreeCoTaskMem(this._taskAlloc);
				int length = this._length;
				while (length-- != 0)
				{
					Marshal.FreeCoTaskMem(this._strings[length]);
				}
			}
			this._disposed = true;
		}
		GC.SuppressFinalize((Object)this);
	}
}