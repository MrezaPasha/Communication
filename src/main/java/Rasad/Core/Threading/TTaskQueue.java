package main.java.Rasad.Core.Threading;

import Rasad.Core.*;
import java.util.*;

public class TTaskQueue<T>
{
	private final ArrayList<TaskObject<T>> tasksList = new ArrayList<TaskObject<T>>();

	private final Object lockObj = new Object();

	private TLoopingTask loopingTask;

	public TTaskQueue()
	{
		this.loopingTask = new TLoopingTask(() -> TaskFunc(), null, null, 1);
	}

	private void TaskFunc()
	{
		this.TaskFuncExt(true);
	}


	private void TaskFuncExt()
	{
		TaskFuncExt(true);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: private void TaskFuncExt(bool action = true)
	private void TaskFuncExt(boolean action)
	{
		ArrayList<TaskObject<T>> list;
		synchronized (this.lockObj)
		{
			list = new ArrayList<TaskObject<T>>(this.tasksList);
			this.tasksList.clear();
		}
		for (TaskObject<T> current : list)
		{
			try
			{
				if (action)
				{
					current.Action(current.getState());
				}
				else
				{
					current.Failed(current.getState());
				}
			}
			catch (RuntimeException exp)
			{
				TLogManager.Error("Task Queue Error", exp);
			}
		}
	}

	public final void Enqueue(TaskObject<T> taskObject)
	{
		synchronized (this.lockObj)
		{
			this.tasksList.add(taskObject);
		}
		if (!this.loopingTask.getIsLooping())
		{
			this.TaskFuncExt(false);
		}
	}

	public final void Stop()
	{
		this.loopingTask.SignalTaskToStop();
		this.TaskFuncExt(false);
	}

	public final void Start()
	{
		this.loopingTask.StartTask();
	}
}