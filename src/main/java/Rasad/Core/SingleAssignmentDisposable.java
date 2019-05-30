package Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 A SingleAssignmentDisposable only allows a single assignment of its disposable object. If it has already been
	 assigned, attempts to set the underlying object will throw an InvalidOperationException.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public class SingleAssignmentDisposable : IDisposable
public class SingleAssignmentDisposable implements Closeable
{
	private final Object gate = new Object();
	private Closeable current;
	private boolean disposed;

	/** 
		 Gets or sets the underlying disposable.
	 
	 If the FutureDisposable has already been assigned then it will throw an InvalidOperationException.
	*/
	public final Closeable getDisposable()
	{
		return current;
	}
	public final void setDisposable(Closeable value)
	{
		boolean disposed = false;
		synchronized (gate)
		{
			if (current != null)
			{
				throw new IllegalStateException("Disposable has already been assigned.");
			}
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
	public final boolean getIsDisposed()
	{
		synchronized (gate)
		{
			return disposed;
		}
	}

	/** 
		 Disposes the underlying disposable.
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
///// <summary>
///// Represents an object that schedules units of work on a provided scheduler.
///// </summary>
//public sealed class ScheduledDisposable : IDisposable
//{
//    private int disposed;

//    /// <summary>
//    /// Initializes a new instance of the <see cref="T:System.Reactive.Disposables.ScheduledDisposable" /> class that uses a scheduler on which to dispose the disposable.
//    /// </summary>
//    public ScheduledDisposable(IScheduler scheduler, IDisposable disposable)
//    {
//        this.Scheduler = scheduler;
//        this.Disposable = disposable;
//    }

//    /// <summary>
//    /// Disposes the wrapped disposable on the provided scheduler.
//    /// </summary>
//    public void Dispose()
//    {
//        this.Scheduler.Schedule(new Action(this.DisposeInner));
//    }

//    private void DisposeInner()
//    {
//        if (Interlocked.Exchange(ref this.disposed, 1) == 0)
//        {
//            this.Disposable.Dispose();
//        }
//    }

//    /// <summary>
//    /// Gets a value that indicates the underlying disposable.
//    /// </summary>
//    public IDisposable Disposable { get; private set; }

//    /// <summary>
//    /// Gets a value that indicates whether the object is disposed.
//    /// </summary>
//    public bool IsDisposed
//    {
//        get
//        {
//            return (this.disposed == 1);
//        }
//    }

//    /// <summary>
//    /// Gets a value that indicates the scheduler.
//    /// </summary>
//    public IScheduler Scheduler { get; private set; }
//}

///// <summary>
///// Represents an object that schedules units of work.
///// </summary>
//public interface IScheduler
//{
//    /// <summary>
//    /// Schedules an action to be executed.
//    /// </summary>
//    /// <param name="state">State passed to the action to be executed.</param>
//    /// <param name="action">Action to be executed.</param>
//    /// <returns>The disposable object used to cancel the scheduled action (best effort).</returns>
//    IDisposable Schedule<TState>(TState state, Func<IScheduler, TState, IDisposable> action);
//    /// <summary>
//    /// Schedules an action to be executed at dueTime.
//    /// </summary>
//    /// <param name="state">State passed to the action to be executed.</param>
//    /// <param name="action">Action to be executed.</param>
//    /// <param name="dueTime">Absolute time at which to execute the action.</param>
//    /// <returns>The disposable object used to cancel the scheduled action (best effort).</returns>
//    IDisposable Schedule<TState>(TState state, DateTimeOffset dueTime, Func<IScheduler, TState, IDisposable> action);
//    /// <summary>
//    /// Schedules an action to be executed after dueTime.
//    /// </summary>
//    /// <param name="state">State passed to the action to be executed.</param>
//    /// <param name="action">Action to be executed.</param>
//    /// <param name="dueTime">Relative time after which to execute the action.</param>
//    /// <returns>The disposable object used to cancel the scheduled action (best effort).</returns>
//    IDisposable Schedule<TState>(TState state, TimeSpan dueTime, Func<IScheduler, TState, IDisposable> action);

//    /// <summary>
//    /// Gets the scheduler's notion of current time.
//    /// </summary>
//    DateTimeOffset Now { get; }
//}