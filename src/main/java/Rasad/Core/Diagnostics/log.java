package Rasad.Core.Diagnostics;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class log
{

	//--------------------------------------------------
	// public section
	//--------------------------------------------------

	static
	{
		Init(new LogUtilsDefault());
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void Init(ILogUtils impl)
	public static void Init(ILogUtils impl)
	{
		//lock (_staticLock) {
		log._impl = impl;
		//}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void WriteEvent(string evtMsg, string evtSrc, TraceEventType evtType)
	public static void WriteEvent(String evtMsg, String evtSrc, TraceEventType evtType)
	{
		_impl.WriteEvent(evtMsg, evtSrc, evtType);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void WriteError(string errMsg, string source)
	public static void WriteError(String errMsg, String source)
	{
		WriteEvent(errMsg, source, TraceEventType.Error);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void WriteError(string errMsg)
	public static void WriteError(String errMsg)
	{
		WriteEvent(errMsg, null, TraceEventType.Error);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void WriteError(Exception err)
	public static void WriteError(RuntimeException err)
	{

		WriteEvent(GetInner(err), null, TraceEventType.Error);
	}
	private static String GetInner(RuntimeException err)
	{
		String result = err.toString();
		if (err.getCause() != null)
		{
			result += System.lineSeparator() + GetInner(err.getCause());
		}
		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void WriteWarning(string warnMsg, string source)
	public static void WriteWarning(String warnMsg, String source)
	{
		WriteEvent(warnMsg, source, TraceEventType.Warning);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void WriteWarning(string warnMsg)
	public static void WriteWarning(String warnMsg)
	{
		WriteEvent(warnMsg, null, TraceEventType.Warning);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void WriteInfo(string infoMsg, string source)
	public static void WriteInfo(String infoMsg, String source)
	{
		WriteEvent(infoMsg, source, TraceEventType.Information);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void WriteInfo(string infoMsg)
	public static void WriteInfo(String infoMsg)
	{
		WriteEvent(infoMsg, null, TraceEventType.Information);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void Flush()
	public static void Flush()
	{
		_impl.Flush();
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("TRACE")] public static void Close()
	public static void Close()
	{
		_impl.Close();
	}

	//--------------------------------------------------
	// private section
	//--------------------------------------------------

	//private static object _staticLock = new Object();
	private static ILogUtils _impl;

}