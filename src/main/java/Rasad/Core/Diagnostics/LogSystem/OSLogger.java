package Rasad.Core.Diagnostics.LogSystem;

import Rasad.Core.*;
import Rasad.Core.Diagnostics.*;
import java.time.*;

public class OSLogger extends LocalLoggerBase
{
	public OSLogger()
	{
		super();
	}

	@Override
	protected void LogAnyway(RasadLogLevel logLevel, String msg, String data)
	{
		EventLogEntryType entryType;
		switch (logLevel)
		{
			case RasadLogLevel.Debug:
			case RasadLogLevel.Info:
				entryType = EventLogEntryType.Information;
				break;
			case RasadLogLevel.Warn:
				entryType = EventLogEntryType.Warning;
				break;
			case RasadLogLevel.Error:
			case RasadLogLevel.Fatal:
				entryType = EventLogEntryType.Error;
				entryType = EventLogEntryType.Error;
				break;
			default:
				throw new RuntimeException("Invalid logLevel for oslogger");
		}

		try
		{
			EventLog.WriteEntry(getPrefix(), "(" + LocalDateTime.now().toString() + ") \r\n" + msg, entryType, 0, (short)0, data.getBytes(java.nio.charset.StandardCharsets.UTF_16LE));
		}
		catch (java.lang.Exception e)
		{
		}
	}
}