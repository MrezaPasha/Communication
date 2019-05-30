package Rasad.Core.Diagnostics;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class LogUtilsBase implements ILogUtils
{

	//--------------------------------------------------
	// public section
	//--------------------------------------------------

	public UUID getactivityId()
	{
		return UUID.Empty;
	}

	//--------------------------------------------------
	// ILogUtils implementation
	//--------------------------------------------------

	public final TraceListenerCollection getlisteners()
	{
		return Trace.Listeners;
	}

	public final void WriteEvent(String message, String source, TraceEventType type)
	{
		Object ctx = BeforeWriteEvent();
		try
		{
			if (source == null)
			{
				source = gets_processFileName();
			}
			tangible.RefObject<Integer> tempRef__evtId = new tangible.RefObject<Integer>(_evtId);
			int evtId = Interlocked.Increment(tempRef__evtId);
		_evtId = tempRef__evtId.argValue;
			TraceEventCache evtCache = new TraceEventCache();
			for (TraceListener l : getlisteners())
			{
				synchronized (l)
				{
					try
					{
						l.TraceEvent(evtCache, source, type, evtId, message);
						if (Trace.AutoFlush)
						{
							l.Flush();
						}
					}
					catch (java.lang.Exception e)
					{
						//swallow error
						Debugger.Break();
					}
				}
			}
		}
		finally
		{
			AfterWriteEvent(ctx);
		}
	}

	public final void Flush()
	{
		for (TraceListener l : getlisteners())
		{
			synchronized (l)
			{
				l.Flush();
			}
		}
	}
	public final void Close()
	{
		for (TraceListener l : getlisteners())
		{
			synchronized (l)
			{
				l.Close();
			}
		}
	}

	//--------------------------------------------------
	// protected section
	//--------------------------------------------------

	protected static String s_processFileNameField = null;
	protected static String gets_processFileName()
	{
		if (s_processFileNameField == null)
		{
			s_processFileNameField = (new File(Environment.GetCommandLineArgs()[0])).getName();
		}
		return s_processFileNameField;
	}

	protected Object BeforeWriteEvent()
	{
		UUID oldActivityId = Trace.CorrelationManager.ActivityId;
		if (!getactivityId().equals(oldActivityId))
		{
			Trace.CorrelationManager.ActivityId = getactivityId();
		}
		return oldActivityId;
	}

	protected void AfterWriteEvent(Object context)
	{
		UUID oldActivityId = (UUID)context;
		if (!getactivityId().equals(oldActivityId))
		{
			Trace.CorrelationManager.ActivityId = getactivityId();
		}
	}

	//--------------------------------------------------
	// private section
	//--------------------------------------------------

	private int _evtId = 0;
}