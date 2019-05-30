package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public class DisposableExt
public class DisposableExt
{
	public static <T> Closeable<T> CreateWithFinalizer(T value, tangible.Action1Param<Boolean> disposeAction)
	{
		return new AnonymousDisposableWithFinalizer<T>(value, disposeAction);
	}

	public static <T> Closeable<T> Create(T value, tangible.Action0Param disposeAction)
	{
		return new AnonymousDisposable<T>(value, disposeAction);
	}

	private static class AnonymousDisposable<T> implements Closeable<T>
	{
		private tangible.Action0Param disposeAction;

		public AnonymousDisposable(T value, tangible.Action0Param disposeAction)
		{
			this.setvalue(value);
			this.disposeAction = () -> disposeAction.invoke();
		}

		private T value;
		public final T getvalue()
		{
			return value;
		}
		private void setvalue(T value)
		{
			value = value;
		}

		public final void close() throws IOException
		{
			tangible.RefObject<tangible.Action0Param> tempRef_disposeAction = new tangible.RefObject<tangible.Action0Param>(disposeAction);
			tangible.Action0Param act = Interlocked.Exchange(tempRef_disposeAction, null);
		disposeAction = () -> tempRef_disposeAction.argValue.invoke();
			if (act != null)
			{
				setvalue(null);
				act.invoke();
			}
		}
	}

	private static class AnonymousDisposableWithFinalizer<T> implements Closeable<T>
	{
		private tangible.Action1Param<Boolean> disposeAction;

		public AnonymousDisposableWithFinalizer(T value, tangible.Action1Param<Boolean> disposeAction)
		{
			this.setvalue(value);
			this.disposeAction = (boolean obj) -> disposeAction.invoke(obj);
		}

		private T value;
		public final T getvalue()
		{
			return value;
		}
		private void setvalue(T value)
		{
			value = value;
		}

		public final void close() throws IOException
		{
			Dispose(true);
		}

		public void Dispose(boolean disposing)
		{
			tangible.RefObject<tangible.Action1Param<Boolean>> tempRef_disposeAction = new tangible.RefObject<tangible.Action1Param<Boolean>>(disposeAction);
			tangible.Action1Param<Boolean> act = Interlocked.Exchange(tempRef_disposeAction, null);
		disposeAction = (boolean obj) -> tempRef_disposeAction.argValue.invoke(obj);
			if (act != null)
			{
				setvalue(null);
				act.invoke(disposing);
			}
		}

		protected void finalize() throws Throwable
		{
			Dispose(false);
		}
	}
}