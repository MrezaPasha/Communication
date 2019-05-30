package Rasad.Core.Interprocess.Utilities;

import Rasad.Core.*;

public class ParentProcessMonitor
{
	private static final boolean DisabledForDebugging = false;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private int processId;
	private Thread monitorThread = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<tangible.EventHandler<tangible.EventArgs>> ParentProcessTerminated = new tangible.Event<tangible.EventHandler<tangible.EventArgs>>();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private void DoMonitorProcess()
	{
		Process p = null;
		try
		{
			p = Process.GetProcessById(processId);
		}
		catch (java.lang.Exception e)
		{
		}
		if (p == null)
		{
			// process terminated
			if (!DisabledForDebugging)
			{
				OnParentProcessTerminated();
			}
		}
		else
		{
			while (true)
			{
				Thread.sleep(100);
				if (p.HasExited)
				{
					OnParentProcessTerminated();
					break;
				}
			}
		}
	}

	protected void OnParentProcessTerminated()
	{
		if (this.ParentProcessTerminated != null)
		{
			for (EventHandler<tangible.EventArgs> listener : ParentProcessTerminated.listeners())
			{
				listener.invoke(this, EventArgs.Empty);
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods
	public ParentProcessMonitor(int processId)
	{
		this.processId = processId;
	}

	public final void StartMonitor()
	{
		if (processId > 0)
		{
			monitorThread = new Thread()
			{
			void run()
			{
					DoMonitorProcess();
			}
			};
			monitorThread.IsBackground = true;
			monitorThread.Priority = ThreadPriority.Lowest;
			monitorThread.start();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}