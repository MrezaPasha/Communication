package Rasad.Core.Diagnostics;

import Rasad.Core.*;

public class ColorizedConsoleTraceListener extends ConsoleTraceListener
{
	private ConsoleColor SwitchConsoleColor(TraceEventType eventType)
	{
		System.ConsoleColor originColor = Console.ForegroundColor;
		if ((eventType.getValue() & (TraceEventType.Critical.getValue() | TraceEventType.Error.getValue()).getValue()) != 0)
		{
			Console.ForegroundColor = ConsoleColor.Red;
			//Console.Beep();
		}
		else if ((eventType.getValue() & (TraceEventType.Warning).getValue()) != 0)
		{
			Console.ForegroundColor = ConsoleColor.Yellow;
		}
		else if ((eventType.getValue() & (TraceEventType.Information).getValue()) != 0)
		{
			Console.ForegroundColor = ConsoleColor.Gray;
		}
		return originColor;
	}
	@Override
	public void TraceData(TraceEventCache eventCache, String source, TraceEventType eventType, int id, Object data)
	{
		ConsoleColor originColor = SwitchConsoleColor(eventType);
		super.TraceData(eventCache, source, eventType, id, data);
		Flush();
		Console.ForegroundColor = originColor;
	}
	@Override
	public void TraceData(TraceEventCache eventCache, String source, TraceEventType eventType, int id, Object... data)
	{
		ConsoleColor originColor = SwitchConsoleColor(eventType);
		super.TraceData(eventCache, source, eventType, id, data);
		Flush();
		Console.ForegroundColor = originColor;
	}
	@Override
	public void TraceEvent(TraceEventCache eventCache, String source, TraceEventType eventType, int id)
	{
		ConsoleColor originColor = SwitchConsoleColor(eventType);
		super.TraceEvent(eventCache, source, eventType, id);
		Flush();
		Console.ForegroundColor = originColor;
	}
	@Override
	public void TraceEvent(TraceEventCache eventCache, String source, TraceEventType eventType, int id, String format, Object... args)
	{
		ConsoleColor originColor = SwitchConsoleColor(eventType);
		super.TraceEvent(eventCache, source, eventType, id, format, args);
		Flush();
		Console.ForegroundColor = originColor;
	}
	@Override
	public void TraceEvent(TraceEventCache eventCache, String source, TraceEventType eventType, int id, String message)
	{
		ConsoleColor originColor = SwitchConsoleColor(eventType);
		super.TraceEvent(eventCache, source, eventType, id, message);
		Flush();
		Console.ForegroundColor = originColor;
	}
}