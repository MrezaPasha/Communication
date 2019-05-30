package main.java.Rasad.Core.Common;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public final class TCommonUtilities
{

	public static void MinimizeProcessMemoryImmediate()
	{
		MinimizeProcessMemoryImmediate(false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static void MinimizeProcessMemoryImmediate(bool gcOnly = false)
	public static void MinimizeProcessMemoryImmediate(boolean gcOnly)
	{
		//GC.Collect(GC.MaxGeneration);
		//GC.WaitForPendingFinalizers();
		//GC.Collect();
		//if (!gcOnly)
		//    Kernel32.SetProcessWorkingSetSize(Process.GetCurrentProcess().Handle, (UIntPtr)0xFFFFFFFF, (UIntPtr)0xFFFFFFFF);
	}


	public static void MinimizeProcessMemorySafe()
	{
		MinimizeProcessMemorySafe(false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static void MinimizeProcessMemorySafe(bool gcOnly = false)
	public static void MinimizeProcessMemorySafe(boolean gcOnly)
	{
		try
		{
			MinimizeProcessMemoryImmediate(gcOnly);
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error memory free", exp);
		}
	}

	public static native boolean EmptyWorkingSet(IntPtr hProcess);
	static
	{
		System.loadLibrary("psapi");
	}


	public static int ElapsedTickCount(int startTickCount, int endTickCount)
	{
		int retVal;
		if (endTickCount < startTickCount)
		{
			retVal = Integer.MAX_VALUE - startTickCount + 1 - endTickCount;
		}
		else
		{
			retVal = endTickCount - startTickCount;
		}
		if (retVal < 0)
		{
			retVal = Math.abs(retVal);
		}
		return retVal;
	}

	/** 
	 Mesures ellpased milliseconds from the start tick (value from Environment.TickCount).
	 This is not dependent to system date time.
	 
	 @param startTickCount
	 @return 
	*/
	public static int ElapsedTickCount(int startTickCount)
	{
		return ElapsedTickCount(startTickCount, Environment.TickCount);
	}

	/** 
	 Implements waiting mechanism which is not dependent to system current date time,
	   supporting cancellation.
	 
	 @param startTickCount
	 @return 
	*/
	public static TaskWaitResult WaitUntil(int timeoutMilliseconds, tangible.Func0Param<Boolean> predicate, CancellationTokenSource cancellationToken)
	{
		if (predicate == null)
		{
			throw new NullPointerException("predicate");
		}
		int startTick = Environment.TickCount;
		boolean conditionSatisfied = predicate.invoke();
		boolean isCancelled = false;
		while (!conditionSatisfied && !isCancelled && ElapsedTickCount(startTick) <= timeoutMilliseconds)
		{
			Thread.sleep(5);
			conditionSatisfied = predicate.invoke();
			if (cancellationToken != null)
			{
				isCancelled = cancellationToken.IsCancellationRequested;
			}
		}
		if (conditionSatisfied)
		{
			return TaskWaitResult.Successful;
		}
		else if (isCancelled)
		{
			return TaskWaitResult.Cancelled;
		}
		else
		{
			return TaskWaitResult.TimedOut;
		}
	}

	/** 
	 Implements waiting mechanism which is not dependent to system current date time.
	 
	 @param timeoutMilliseconds
	 @param predicate
	 @return 
	*/
	public static TaskWaitResult WaitUntil(int timeoutMilliseconds, tangible.Func0Param<Boolean> predicate)
	{
		return WaitUntil(timeoutMilliseconds, predicate, null);
	}

	/** 
	 
	 
	 @param typeContainedInAssembly
	 @param resourcePath
	 Samples: Resources.MyFile.bin (path to MyFile.bin in the directory Resources)
	 
	 @return 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static Byte[] ReadFromResource(Type typeContainedInAssembly, String resourcePath)
	public static byte[] ReadFromResource(java.lang.Class typeContainedInAssembly, String resourcePath)
	{
		String resourceFullPath = Path.GetFileNameWithoutExtension(typeContainedInAssembly.Assembly.Location) + "." + resourcePath;
		try (System.IO.Stream stream = typeContainedInAssembly.Assembly.GetManifestResourceStream(resourceFullPath))
		{
			if (stream == null)
			{
				throw new RuntimeException(String.format("Resource stream '%1$s' not found", resourceFullPath));
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] retVal = new byte[(int)stream.Length];
			byte[] retVal = new byte[(int)stream.Length];
			stream.Read(retVal, 0, (int)stream.Length);
			return retVal;
		}
	}

	public static void DisposeSafe(Closeable obj)
	{
		try
		{
			if (obj != null)
			{
				obj.Dispose();
			}
		}
		catch (java.lang.Exception e)
		{
		}
	}

	public static long GetFileSizeSafe(String fname)
	{
		try
		{
			return (new File(fname)).length();
		}
		catch (java.lang.Exception e)
		{
			return 0;
		}
	}


	public static void StartKillCurrentProcessInBackground()
	{
		StartKillCurrentProcessInBackground(4000);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static void StartKillCurrentProcessInBackground(int waitMilliseconds = 4000)
	public static void StartKillCurrentProcessInBackground(int waitMilliseconds)
	{
		Thread th = new Thread()
		{
		void run()
		{
					if (waitMilliseconds > 0)
					{
						Thread.sleep(waitMilliseconds);
					}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	//#if(DEBUG)
					System.out.println("Killing ungracefully");
	//#endif
					System.Diagnostics.Process.GetCurrentProcess().Kill();
		}
		};
		th.IsBackground = true;
		th.start();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static bool ByteArraysEqual(byte[] data1, byte[] data2)
	public static boolean ByteArraysEqual(byte[] data1, byte[] data2)
	{
		if ((data1 == null && data2 != null) || (data1 != null && data2 == null))
		{
			return false;
		}
		if (data1 == null)
		{
			return true; // both are null
		}
		if (data1.length != data2.length)
		{
			return false;
		}
//C# TO JAVA CONVERTER WARNING: Java Arrays.equals is not always identical to LINQ 'SequenceEqual':
//ORIGINAL LINE: return data1.SequenceEqual(data2);
		return Arrays.equals(data1, data2);
	}

	public static boolean IsApplicationActive()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var activatedHandle = USER32.GetForegroundWindow();
		if (System.IntPtr.OpEquality(activatedHandle, IntPtr.Zero))
		{
			return false; // No window is currently activated
		}

		int procId = Process.GetCurrentProcess().Id;
		int activeProcId;
		tangible.OutObject<Integer> tempOut_activeProcId = new tangible.OutObject<Integer>();
		USER32.GetWindowThreadProcessId(activatedHandle, tempOut_activeProcId);
	activeProcId = tempOut_activeProcId.argValue;

		return activeProcId == procId;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Process Utilities
	private static void KillAllExistingProcesses(boolean allModuleInstances, Integer... processIds)
	{
		ArrayList<Process> existingInstances = new ArrayList<Process>();
		if (allModuleInstances)
		{
			existingInstances.addAll(Process.GetProcessesByName(Process.GetCurrentProcess().ProcessName));
		}
		for (int pid : processIds)
		{
			try
			{
				System.Diagnostics.Process process = Process.GetProcessById(pid);
				if (process != null && !existingInstances.Any(s -> s.Id == pid))
				{
					existingInstances.add(process);
				}
			}
			catch (java.lang.Exception e)
			{
			}
		}
		for (Process item : existingInstances)
		{
			if (item.Id != Process.GetCurrentProcess().Id)
			{
				item.Kill();
				item.WaitForExit();
			}
		}
	}
	public static void KillAllOtherExistingProcessInstances()
	{
		KillAllExistingProcesses(true);
	}
	public static void KillAllExistingProcesses(Integer... processIds)
	{
		KillAllExistingProcesses(false, processIds);
	}
	public static void KillAllExistingProcesses(String processFileName)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var exisitingProcess = Process.GetProcessesByName(System.IO.Path.GetFileNameWithoutExtension(processFileName));
		if (exisitingProcess != null && exisitingProcess.Any())
		{
			TLogManager.Info("Killing process instances for '" + processFileName + "'");
			try
			{
				exisitingProcess.ForEach(p ->
				{
							if (p.Id != Process.GetCurrentProcess().Id)
							{
								p.Kill();
								p.WaitForExit();
							}
				});
			}
			catch (RuntimeException exp)
			{
				TLogManager.Error("Error in process terminate for '" + processFileName + "'", exp);
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public static void OpenWindowsPing(String ipAddress)
	{
		System.Diagnostics.ProcessStartInfo proc = new System.Diagnostics.ProcessStartInfo();
		proc.FileName = "cmd.exe";
		proc.Arguments = String.format("/c ping %1$s -t", ipAddress);
		Process.Start(proc);
	}

	public static String GetCurrentMethodName(int stackFrameIndex)
	{
		try
		{
			StackTrace stackTrace = new StackTrace();
			return stackTrace.GetFrame(stackFrameIndex + 1).GetMethod().Name;
		}
		catch (java.lang.Exception e)
		{
			return "";
		}
	}

	public static String GetWebserviceClientAddress()
	{
		System.ServiceModel.OperationContext context = System.ServiceModel.OperationContext.Current;
		if (context != null)
		{
			System.ServiceModel.Channels.MessageProperties messageProperties = context.IncomingMessageProperties;
			System.ServiceModel.Channels.RemoteEndpointMessageProperty endpointProperty = messageProperties[System.ServiceModel.Channels.RemoteEndpointMessageProperty.Name] instanceof System.ServiceModel.Channels.RemoteEndpointMessageProperty ? (System.ServiceModel.Channels.RemoteEndpointMessageProperty)messageProperties[System.ServiceModel.Channels.RemoteEndpointMessageProperty.Name] : null;
			return endpointProperty.Address + ":" + endpointProperty.Port.toString();
		}
		else
		{
			return "";
		}
	}

	public static String RemoveInvalidFileNameChars(String s)
	{
		String invalid = new String(Path.GetInvalidFileNameChars()) + new String(Path.GetInvalidPathChars());

		for (char c : invalid)
		{
			s = s.replace(String.valueOf(c), "_");
		}
		return s;
	}

	/** Returns true if the current application has focus, false otherwise
	*/
	public static boolean ApplicationIsActivated()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var activatedHandle = GetForegroundWindow();
		if (System.IntPtr.OpEquality(activatedHandle, IntPtr.Zero))
		{
			return false; // No window is currently activated
		}

		int procId = Process.GetCurrentProcess().Id;
		int activeProcId;
		tangible.OutObject<Integer> tempOut_activeProcId = new tangible.OutObject<Integer>();
		GetWindowThreadProcessId(activatedHandle, tempOut_activeProcId);
	activeProcId = tempOut_activeProcId.argValue;

		return activeProcId == procId;
	}


	private static native IntPtr GetForegroundWindow();
	static
	{
		System.loadLibrary("user32.dll");
	}

	private static native int GetWindowThreadProcessId(IntPtr handle, tangible.OutObject<Integer> processId);

	public static String DataSizeToString(long bytes)
	{
		DataSizeUnit dataSizeUnit = DataSizeUnit.GByte;
		double unit = 1024.0;
		double retVal = bytes / unit / unit / unit;
		if (retVal <= 0.5)
		{
			retVal = bytes / unit / unit;
			dataSizeUnit = DataSizeUnit.MByte;
			if (retVal <= 0.5)
			{
				retVal = bytes / unit;
				dataSizeUnit = DataSizeUnit.KByte;
				if (retVal <= 0.5)
				{
					retVal = bytes;
					dataSizeUnit = DataSizeUnit.Byte;
				}
			}
		}
		String suffix = "";
		switch (dataSizeUnit)
		{
			case Byte:
				suffix = "بایت";
				break;
			case KByte:
				suffix = "کیلوبایت";
				break;
			case MByte:
				suffix = "مگابایت";
				break;
			case GByte:
				suffix = "گیگابایت";
				break;
		}
		return (new Double(retVal)).toString("0.##") + " " + suffix;
	}

	public static String SecondsToDurationString(long totalSeconds)
	{
		long days = totalSeconds / (24 * 60 * 60);
		totalSeconds = totalSeconds - days * 24 * 60 * 60;
		long hours = totalSeconds / (60 * 60);
		totalSeconds = totalSeconds - hours * 60 * 60;
		long minutes = totalSeconds / 60;
		totalSeconds = totalSeconds - minutes * 60;
		long seconds = totalSeconds;

		if (days > 0)
		{
			return String.format("%1$s" + " روز و " + "%2$s" + " ساعت و " + "%3$s" + " دقیقه و " + "%4$s" + " ثانیه", days, hours, minutes, seconds);
		}
		else if (hours > 0)
		{
			return String.format("%1$s" + " ساعت و " + "%2$s" + " دقیقه و " + "%3$s ثانیه", hours, minutes, seconds);
		}
		else if (minutes > 0)
		{
			return String.format("%2$s" + " دقیقه و " + "%3$s ثانیه", hours, minutes, seconds);
		}
		else
		{
			return String.format("%3$s ثانیه", hours, minutes, seconds);
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ComputeFileMD5Hash(String filename)
	public static byte[] ComputeFileMD5Hash(String filename)
	{
		try (System.Security.Cryptography.MD5 md5 = MD5.Create())
		{
			try (System.IO.FileStream stream = File.OpenRead(filename))
			{
				return md5.ComputeHash(stream);
			}
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ComputeFileMD5Hash(byte[] data)
	public static byte[] ComputeFileMD5Hash(byte[] data)
	{
		try (System.Security.Cryptography.MD5 md5 = MD5.Create())
		{
			return md5.ComputeHash(data);
		}
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] CloneByteArray(byte[] data)
	public static byte[] CloneByteArray(byte[] data)
	{
		if (data == null)
		{
			return null;
		}
		else if (data.length == 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return new byte[] { };
			return new byte[] { };
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] retVal = new byte[data.Length];
			byte[] retVal = new byte[data.length];
			Buffer.BlockCopy(data, 0, retVal, 0, data.length);
			return retVal;
		}
	}

	public static boolean MACAddressEqualString(String macStr1, String macStr2)
	{
		if (macStr1 == null)
		{
			macStr1 = "";
		}
		if (macStr2 == null)
		{
			macStr2 = "";
		}
		macStr1 = macStr1.replace("-", "").replace(":", "").toLowerCase();
		macStr2 = macStr2.replace("-", "").replace(":", "").toLowerCase();
		return macStr1.equals(macStr2);
	}
}