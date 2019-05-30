package Rasad.Core.Memory;

import Rasad.Core.*;

public class TProcessMemoryLogger
{
	private System.Timers.Timer timerMemoryMonitor;
	private boolean logAsInfo;

	public TProcessMemoryLogger(int intervalMilliseconds, boolean logAsInfo)
	{
		this.logAsInfo = logAsInfo;
		timerMemoryMonitor = new System.Timers.Timer(intervalMilliseconds);
		timerMemoryMonitor.AutoReset = false;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		timerMemoryMonitor.Elapsed += timerMemoryMonitor_Elapsed;
		timerMemoryMonitor.Start();
	}

	private void timerMemoryMonitor_Elapsed(Object sender, System.Timers.ElapsedEventArgs e)
	{
		try
		{
			System.Diagnostics.Process currentProcess = Process.GetCurrentProcess();
			long totalMemoryMB = currentProcess.PrivateMemorySize64 / (1024 * 1024);
			String msg = "* Memory: " + String.valueOf(totalMemoryMB) + " MB";
			if (logAsInfo)
			{
				TLogManager.Info(msg);
			}
			else
			{
				TLogManager.Debug(msg);
			}
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error checking process memory in logger", exp);
		}
		finally
		{
			timerMemoryMonitor.Start();
		}
	}
}