package Rasad.Core.Diagnostics.LogSystem;

import Rasad.Core.*;
import Rasad.Core.Diagnostics.*;

public abstract class LoggerBase implements ILogger
{
	@FunctionalInterface
	private interface LogDelegateWithData
	{
		void invoke(RasadLogLevel logLevel, String msg, String data);
	}

	private LogDelegateWithData DebugDelegate;
	private LogDelegateWithData InfoDelegate;
	private LogDelegateWithData WarnDelegate;
	private LogDelegateWithData ErrorDelegate;
	private LogDelegateWithData FatalDelegate;

	private RasadLogLevel logLevel = RasadLogLevel.values()[0];

	public LoggerBase()
	{
		setServerID(null);
		setCameraID(null);
		UpdateDelegates();
	}

	private void UpdateDelegates()
	{
		if (logLevel.HasFlag(RasadLogLevel.Debug))
		{
			DebugDelegate = (RasadLogLevel logLevel, String msg, String data) -> FunctionalDelegateHandler(logLevel, msg, data);
		}
		else
		{
			DebugDelegate = (RasadLogLevel logLevel, String msg, String data) -> EmptyDelegateHandler(logLevel, msg, data);
		}

		if (logLevel.HasFlag(RasadLogLevel.Info))
		{
			InfoDelegate = (RasadLogLevel logLevel, String msg, String data) -> FunctionalDelegateHandler(logLevel, msg, data);
		}
		else
		{
			InfoDelegate = (RasadLogLevel logLevel, String msg, String data) -> EmptyDelegateHandler(logLevel, msg, data);
		}

		if (logLevel.HasFlag(RasadLogLevel.Warn))
		{
			WarnDelegate = (RasadLogLevel logLevel, String msg, String data) -> FunctionalDelegateHandler(logLevel, msg, data);
		}
		else
		{
			WarnDelegate = (RasadLogLevel logLevel, String msg, String data) -> EmptyDelegateHandler(logLevel, msg, data);
		}

		if (logLevel.HasFlag(RasadLogLevel.Error))
		{
			ErrorDelegate = (RasadLogLevel logLevel, String msg, String data) -> FunctionalDelegateHandler(logLevel, msg, data);
		}
		else
		{
			ErrorDelegate = (RasadLogLevel logLevel, String msg, String data) -> EmptyDelegateHandler(logLevel, msg, data);
		}

		if (logLevel.HasFlag(RasadLogLevel.Fatal))
		{
			FatalDelegate = (RasadLogLevel logLevel, String msg, String data) -> FunctionalDelegateHandler(logLevel, msg, data);
		}
		else
		{
			FatalDelegate = (RasadLogLevel logLevel, String msg, String data) -> EmptyDelegateHandler(logLevel, msg, data);
		}
	}

	protected abstract void LogAnyway(RasadLogLevel logLevel, String msg, String data);

	private void EmptyDelegateHandler(RasadLogLevel logLevel, String msg, String data)
	{
	}
	private void FunctionalDelegateHandler(RasadLogLevel logLevel, String msg, String data)
	{
		LogAnyway(logLevel, msg, data);
	}

	private int SystemEntity;
	public final int getSystemEntity()
	{
		return SystemEntity;
	}
	public final void setSystemEntity(int value)
	{
		SystemEntity = value;
	}

	private String SystemEntityString;
	public final String getSystemEntityString()
	{
		return SystemEntityString;
	}
	public final void setSystemEntityString(String value)
	{
		SystemEntityString = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private Nullable<byte> ServerID;
	private Byte ServerID = null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<byte> getServerID()
	public final Byte getServerID()
	{
		return ServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setServerID(Nullable<byte> value)
	public final void setServerID(Byte value)
	{
		ServerID = value;
	}

	private Short CameraID = null;
	public final Short getCameraID()
	{
		return CameraID;
	}
	public final void setCameraID(Short value)
	{
		CameraID = value;
	}

	public final RasadLogLevel getLogLevel()
	{
		return logLevel;
	}
	public final void setLogLevel(RasadLogLevel value)
	{
		logLevel = value;
		UpdateDelegates();
	}

	public final void Debug(String msg)
	{
		Debug(msg, null);
	}
	public final void Debug(String msg, String data)
	{
		DebugDelegate.invoke(RasadLogLevel.Debug, msg, data);
	}

	public final void Info(String msg)
	{
		Info(msg, null);
	}
	public final void Info(String msg, String data)
	{
		InfoDelegate.invoke(RasadLogLevel.Info, msg, data);
	}

	public final void Warn(String msg)
	{
		WarnDelegate.invoke(RasadLogLevel.Warn, msg, null);
	}
	public final void Warn(String msg, String data)
	{
		WarnDelegate.invoke(RasadLogLevel.Warn, msg, data);
	}

	public final void Error(String msg)
	{
		ErrorDelegate.invoke(RasadLogLevel.Error, msg, null);
	}

	public final void Fatal(String msg)
	{
		FatalDelegate.invoke(RasadLogLevel.Fatal, msg, null);
	}
}