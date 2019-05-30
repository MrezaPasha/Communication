package Rasad.Core.Memory;

import Rasad.Core.*;

public class TProcessMemoryMonitor
{
	private System.Timers.Timer timerMemoryMonitor;
	private long _MaxMemoryMB;
	private boolean _HaltOnMemoryExceed = false;


	public TProcessMemoryMonitor(boolean haltOnMemoryExceed)
	{
		this(haltOnMemoryExceed, 800);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TProcessMemoryMonitor(bool haltOnMemoryExceed, int maxMemoryMB = 800)
	public TProcessMemoryMonitor(boolean haltOnMemoryExceed, int maxMemoryMB)
	{
		this._HaltOnMemoryExceed = haltOnMemoryExceed;
		this._MaxMemoryMB = maxMemoryMB;

		timerMemoryMonitor = new System.Timers.Timer(TimeSpan.FromMinutes(1).TotalMilliseconds);
		timerMemoryMonitor.AutoReset = false;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		timerMemoryMonitor.Elapsed += timerMemoryMonitor_Elapsed;
		timerMemoryMonitor.Start();
	}

	public tangible.Event<tangible.EventHandler<tangible.EventArgs>> ProcessMemoryExceeded = new tangible.Event<tangible.EventHandler<tangible.EventArgs>>();

	private void timerMemoryMonitor_Elapsed(Object sender, System.Timers.ElapsedEventArgs e)
	{
		try
		{
			System.Diagnostics.Process currentProcess = Process.GetCurrentProcess();
			long totalMemoryMB = currentProcess.PrivateMemorySize64 / (1024 * 1024);
			if (totalMemoryMB > _MaxMemoryMB)
			{
				class AnonymousType
				{
					public String MemoryLimit;
					public String CurrentMemory;

					public AnonymousType(String _MemoryLimit, String _CurrentMemory)
					{
						MemoryLimit = _MemoryLimit;
						CurrentMemory = _CurrentMemory;
					}
				}
				TLogManager.Warn("Process memory limit exceeded - so halt process", AnonymousType(String.valueOf(_MaxMemoryMB) + " MB", String.valueOf(totalMemoryMB) + " MB"));
				if (this._HaltOnMemoryExceed)
				{
					System.Diagnostics.Process.GetCurrentProcess().Kill();
				}
				OnProcessMemoryExceeded();
			}
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error checking process memory", exp);
		}
		finally
		{
			timerMemoryMonitor.Start();
		}
	}

	protected void OnProcessMemoryExceeded()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = ProcessMemoryExceeded;
		if (del != null)
		{
			del(this, tangible.EventArgs.Empty);
		}
	}
}