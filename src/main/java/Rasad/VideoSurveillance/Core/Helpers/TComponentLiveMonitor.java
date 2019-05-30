package Rasad.VideoSurveillance.Core.Helpers;

import Rasad.VideoSurveillance.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

public class TComponentLiveMonitor implements Closeable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor
	public TComponentLiveMonitor(UUID componentGuid, TimeSpan updateInterval)
	{
		this.setComponentGuid(componentGuid);
		this.setUpdateInterval(updateInterval);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<tangible.EventHandler<TComponentNoLivenessPeriodDetectedEventArgs>> ComponentNoLivenessPeriodDetected = new tangible.Event<tangible.EventHandler<TComponentNoLivenessPeriodDetectedEventArgs>>();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Variables
	private boolean disposed = false;

	private Thread threadStatusMonitoring = null;
	private boolean stopRequested = false;
	private Object lockObj = new Object();
	private ManualResetEvent stopResetEvent = new ManualResetEvent(false);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties
	private UUID ComponentGuid;
	public final UUID getComponentGuid()
	{
		return ComponentGuid;
	}
	private void setComponentGuid(UUID value)
	{
		ComponentGuid = value;
	}
	private TimeSpan UpdateInterval = new TimeSpan();
	public final TimeSpan getUpdateInterval()
	{
		return UpdateInterval;
	}
	private void setUpdateInterval(TimeSpan value)
	{
		UpdateInterval = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private void DoThreadMonitor()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Check before starting
		TComponentActivityMonitor activityMonitor = new TComponentActivityMonitor(this.getComponentGuid(), ActivityMonitorItem.Alive);
		try
		{
			LocalDateTime lastAliveTime = (LocalDateTime)activityMonitor.GetLastActivity();
			LocalDateTime now = LocalDateTime.now();
			class AnonymousType
			{
				public java.time.LocalDateTime Now;
				public java.time.LocalDateTime LastActiveTime;

				public AnonymousType(java.time.LocalDateTime _Now, java.time.LocalDateTime _LastActiveTime)
				{
					Now = _Now;
					LastActiveTime = _LastActiveTime;
				}
			}
			TLogManager.Info("Component Live Monitor Info", AnonymousType(now, lastAliveTime));
			if (lastAliveTime.compareTo(now) > 0)
			{
				TLogManager.Warn("Last active time greater than now for " + getComponentGuid().toString());
			}
			else
			{
				if ((now - lastAliveTime).TotalSeconds > 10) // max 10 seconds threshold
				{
					OnComponentNoLivenessPeriodDetected(new TComponentNoLivenessPeriodDetectedEventArgs(lastAliveTime, now));
				}
			}
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error checking component last alive time", exp);
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

		while (!stopRequested)
		{
			activityMonitor.LogActivity();
			Thread.sleep(10);
			stopResetEvent.WaitOne(getUpdateInterval());
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods
	public final void Start()
	{
		synchronized (lockObj)
		{
			if (threadStatusMonitoring != null)
			{
				throw new RuntimeException("Not stopped");
			}
			stopRequested = false;
			stopResetEvent.Reset();
			threadStatusMonitoring = new Thread()
			{
			void run()
			{
						DoThreadMonitor();
			}
			}{IsBackground = true, Name = "StatusMonitor", Priority = ThreadPriority.Normal};
			threadStatusMonitoring.start();
		}
	}
//C# TO JAVA CONVERTER TODO TASK: Local functions are not converted by C# to Java Converter:
//	public void Stop()
//		{
//			lock (lockObj)
//			{
//				stopRequested = true;
//				stopResetEvent.Set();
//			}
//		}

//C# TO JAVA CONVERTER TODO TASK: Local functions are not converted by C# to Java Converter:
//	public void Dispose()
//		{
//			Dispose(true);
//			GC.SuppressFinalize(this);
//		}

//C# TO JAVA CONVERTER TODO TASK: Local functions are not converted by C# to Java Converter:
//	protected virtual void Dispose(bool disposing)
//		{
//			if (disposed)
//				return;
//
//			if (disposing)
//			{
//				disposed = true;
//				// free managed resources
//			}
//			// free native resources if there are any.
//			Stop();
//		}

//C# TO JAVA CONVERTER TODO TASK: Local functions are not converted by C# to Java Converter:
//	protected virtual void OnComponentNoLivenessPeriodDetected(TComponentNoLivenessPeriodDetectedEventArgs e)
//		{
//			var del = this.ComponentNoLivenessPeriodDetected;
//			if (del != null)
//				del(this, e);
//		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}