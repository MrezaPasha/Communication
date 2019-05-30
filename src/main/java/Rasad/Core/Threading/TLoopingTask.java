package main.java.Rasad.Core.Threading;

import Rasad.Core.*;
import java.io.*;

public class TLoopingTask implements Closeable
{
	@FunctionalInterface
	public interface TTaskFunction
	{
		void invoke();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Variables
	private Task internalTask;

	private boolean isLooping;

	private final int loopTimeMilliseconds = 1;

	private final EventWaitHandle _tmpEvent = new ManualResetEvent(false);

	private boolean isRunning;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties
	private TLoopingTask.TTaskFunction OnTaskFuncD;
	public final TLoopingTask.TTaskFunction getOnTaskFuncD()
	{
		return OnTaskFuncD;
	}
	private void setOnTaskFuncD(TLoopingTask.TTaskFunction value)
	{
		OnTaskFuncD = () -> value.invoke();
	}

	private TLoopingTask.TTaskFunction OnTaskStartD;
	public final TLoopingTask.TTaskFunction getOnTaskStartD()
	{
		return OnTaskStartD;
	}
	private void setOnTaskStartD(TLoopingTask.TTaskFunction value)
	{
		OnTaskStartD = () -> value.invoke();
	}

	private TLoopingTask.TTaskFunction OnTaskStopD;
	public final TLoopingTask.TTaskFunction getOnTaskStopD()
	{
		return OnTaskStopD;
	}
	private void setOnTaskStopD(TLoopingTask.TTaskFunction value)
	{
		OnTaskStopD = () -> value.invoke();
	}

	public final boolean getIsLooping()
	{
		return this.isLooping;
	}

	private String Name;
	public final String getName()
	{
		return Name;
	}
	public final void setName(String value)
	{
		Name = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor/Dtor

	public TLoopingTask(TLoopingTask.TTaskFunction taskFunc, TLoopingTask.TTaskFunction taskStart, TLoopingTask.TTaskFunction taskStop)
	{
		this(taskFunc, taskStart, taskStop, 1);
	}

	public TLoopingTask(TLoopingTask.TTaskFunction taskFunc, TLoopingTask.TTaskFunction taskStart)
	{
		this(taskFunc, taskStart, null, 1);
	}

	public TLoopingTask(TLoopingTask.TTaskFunction taskFunc)
	{
		this(taskFunc, null, null, 1);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TLoopingTask(TLoopingTask.TTaskFunction taskFunc, TLoopingTask.TTaskFunction taskStart = null, TLoopingTask.TTaskFunction taskStop = null, int loopTimeMilliseconds = 1)
	public TLoopingTask(TLoopingTask.TTaskFunction taskFunc, TLoopingTask.TTaskFunction taskStart, TLoopingTask.TTaskFunction taskStop, int loopTimeMilliseconds)
	{
		this.setOnTaskFuncD(() -> taskFunc.invoke());
		this.setOnTaskStartD(() -> taskStart.invoke());
		this.setOnTaskStopD(() -> taskStop.invoke());
		this.loopTimeMilliseconds = loopTimeMilliseconds;
	}

	protected void finalize() throws Throwable
	{
		this.Dispose(true);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods
	public final void StartTask()
	{
		if (!this.isLooping)
		{
			if (this.internalTask != null)
			{
				this.WaitTaskToStop(0);
			}
			this.isLooping = true;
			this.internalTask = new Task(() -> DoTask(), CancellationToken.None);
			this.internalTask.Start();
		}
	}

	public final void SignalTaskToStop()
	{
		this.isLooping = false;
	}

	public final void WaitTaskToStop(int nTimeOutMs)
	{
		if (this.internalTask != null && !this.internalTask.Wait(nTimeOutMs))
		{
			try
			{
				this.internalTask.Wait();
			}
			catch (RuntimeException exp)
			{
				TLogManager.Error("Error WaitTaskToStop", exp);
			}
		}
	}
	public final void close() throws IOException
	{
		this.Dispose(true);
		GC.SuppressFinalize(this);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private void DoTask()
	{
		try
		{
			this.isRunning = true;
			if (this.getOnTaskStartD() != null)
			{
				this.OnTaskStartD();
			}
			while (this.isLooping)
			{
				this.OnTaskFuncD();
				this._tmpEvent.WaitOne(this.loopTimeMilliseconds);
			}
			if (this.getOnTaskStopD() != null)
			{
				this.OnTaskStopD();
			}
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error inside LoopingTask DoTask", exp);
		}
		this.isRunning = false;
	}

	protected void Dispose(boolean bDisposing)
	{
		this.SignalTaskToStop();
		this._tmpEvent.Dispose();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}