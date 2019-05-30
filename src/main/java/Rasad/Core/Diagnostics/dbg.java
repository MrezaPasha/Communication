package Rasad.Core.Diagnostics;

import Rasad.Core.*;
import java.io.*;

public class dbg
{

	//--------------------------------------------------
	// public section
	//--------------------------------------------------

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Warning(string errMsg)
	public static void Warning(String errMsg)
	{
		_WarningInternal(2, errMsg, null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Warning(string errMsg, string errSrc)
	public static void Warning(String errMsg, String errSrc)
	{
		_WarningInternal(2, errMsg, errSrc);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Error(string errMsg)
	public static void Error(String errMsg)
	{
		_ErrorInternal(2, errMsg, null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Error(string errMsg, string errSrc)
	public static void Error(String errMsg, String errSrc)
	{
		_ErrorInternal(2, errMsg, errSrc);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Error(Exception exception)
	public static void Error(RuntimeException exception)
	{
		_ErrorInternal(2, exception.getMessage(), exception.Source);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Error(Exception exception, string errSrc)
	public static void Error(RuntimeException exception, String errSrc)
	{
		_ErrorInternal(2, exception.getMessage(), errSrc);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Assert(bool expr, string errMsg, string category)
	public static void Assert(boolean expr, String errMsg, String category)
	{
		_AssertInternal(2, expr, errMsg, category);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Assert(bool expr, string errMsg)
	public static void Assert(boolean expr, String errMsg)
	{
		_AssertInternal(2, expr, errMsg, null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Assert(bool expr)
	public static void Assert(boolean expr)
	{
		_AssertInternal(2, expr, null, null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Info(string errMsg, string errSrc)
	public static void Info(String errMsg, String errSrc)
	{
		_InfoInternal(2, errMsg, errSrc);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Conditional("DEBUG")][DebuggerHidden] public static void Info(string errMsg)
	public static void Info(String errMsg)
	{
		_InfoInternal(2, errMsg, null);
	}

	public static class AssertionFailureException extends RuntimeException
	{
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerHidden][Conditional("DEBUG")] public static void Break()
	public static void Break()
	{
		if (Debugger.IsAttached)
		{
			Debugger.Break();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerHidden][Conditional("DEBUG")] public static void BreakIf(bool Condition)
	public static void BreakIf(boolean Condition)
	{
		if (Debugger.IsAttached && Condition)
		{
			Debugger.Break();
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

	protected static String _ResolveSourceIfNone(StackFrame sf, String src)
	{
		if (src == null)
		{
			//return sf.GetMethod().DeclaringType.Namespace;
			//return sf.GetMethod().DeclaringType.FullName;
			//return Process.GetCurrentProcess().ProcessName;
			return gets_processFileName();
		}
		return src;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerHidden] protected static void _AssertInternal(int framesToSkip, bool expr, string errMsg, string src)
	protected static void _AssertInternal(int framesToSkip, boolean expr, String errMsg, String src)
	{
		if (expr)
		{
			return;
		}

		StackTrace stack = new StackTrace(framesToSkip, true);
		StringBuilder evtSb = new StringBuilder();
		evtSb.append("ASSERT FAILED:");
		if (!tangible.StringHelper.isNullOrEmpty(errMsg))
		{
			evtSb.append(" \"");
			evtSb.append(errMsg);
			evtSb.append("\"");
		}
		evtSb.append(" at ");
		System.Diagnostics.StackFrame sf = stack.GetFrame(0);
		AppendStackFrame(evtSb, sf);
		src = _ResolveSourceIfNone(sf, src);
		log.WriteEvent(evtSb.toString(), src, TraceEventType.Critical);
		log.Flush();
		Break();
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerHidden] protected static void _ErrorInternal(int framesToSkip, string errMsg, string src)
	protected static void _ErrorInternal(int framesToSkip, String errMsg, String src)
	{

		StackTrace stack = new StackTrace(framesToSkip, true);
		StringBuilder evtSb = new StringBuilder();
		if (!tangible.StringHelper.isNullOrEmpty(errMsg))
		{
			evtSb.append(" \"");
			evtSb.append(errMsg);
			evtSb.append("\"");
		}
		evtSb.append(" at ");
		System.Diagnostics.StackFrame sf = stack.GetFrame(0);
		AppendStackFrame(evtSb, sf);
		src = _ResolveSourceIfNone(sf, src);
		log.WriteEvent(evtSb.toString(), src, TraceEventType.Error);
		log.Flush();
		Break();
	}

	protected static void _WarningInternal(int framesToSkip, String errMsg, String src)
	{

		StackTrace stack = new StackTrace(framesToSkip, true);
		StringBuilder evtSb = new StringBuilder();
		//if (!String.IsNullOrEmpty(errMsg)) {
		//    evtSb.Append(" \"");
		evtSb.append(errMsg);
		//    evtSb.Append("\"");
		//}
		//evtSb.Append(" at ");
		System.Diagnostics.StackFrame sf = stack.GetFrame(0);
		//AppendStackFrame(evtSb, sf);
		src = _ResolveSourceIfNone(sf, src);
		log.WriteEvent(evtSb.toString(), src, TraceEventType.Warning);
		log.Flush();
		//Break();
	}

	protected static void _InfoInternal(int framesToSkip, String errMsg, String src)
	{

		StackTrace stack = new StackTrace(framesToSkip, true);
		StringBuilder evtSb = new StringBuilder();
		//if (!String.IsNullOrEmpty(errMsg)) {
		//    evtSb.Append(" \"");
		evtSb.append(errMsg);
		//    evtSb.Append("\"");
		//}
		//evtSb.Append(" at ");
		System.Diagnostics.StackFrame sf = stack.GetFrame(0);
		//AppendStackFrame(evtSb, sf);
		src = _ResolveSourceIfNone(sf, src);
		log.WriteEvent(evtSb.toString(), src, TraceEventType.Information);
		log.Flush();
		//Break();
	}

	private static void AppendStackFrame(StringBuilder sb, StackFrame sf)
	{
		sb.append(sf.GetMethod());
		String fileName = sf.GetFileName();
		if (!tangible.StringHelper.isNullOrEmpty(fileName))
		{
			sb.append(" in ");
			sb.append(sf.GetFileName());
			int lineNumber = sf.GetFileLineNumber();
			if (lineNumber > 0)
			{
				sb.append(": line ");
				sb.append(lineNumber);
			}
		}
	}

}