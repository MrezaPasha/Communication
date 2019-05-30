package main.java.Rasad.Core.Api;


//C# TO JAVA CONVERTER TODO TASK: C# 'unsafe' code is not converted by C# to Java Converter:
//public unsafe static class Kernel32
//	{
//		[StructLayout(LayoutKind.Sequential)] public struct SYSTEMTIME
//		{
//			public short wYear;
//			public short wMonth;
//			public short wDayOfWeek;
//			public short wDay;
//			public short wHour;
//			public short wMinute;
//			public short wSecond;
//			public short wMilliseconds;
//		}
//		[DllImport("kernel32.dll", SetLastError = true)] private static extern bool SetSystemTime(ref SYSTEMTIME st);
//		public static bool SetSystemTime(DateTime changeDateTime)
//		{
//			var dateTime = changeDateTime.ToUniversalTime();
//			SYSTEMTIME st = new SYSTEMTIME();
//			st.wYear = (short)dateTime.Year; // must be short
//			st.wMonth = (short)dateTime.Month;
//			st.wDay = (short)dateTime.Day;
//			st.wHour = (short)dateTime.Hour;
//			st.wMinute = (short)dateTime.Minute;
//			st.wSecond = (short)dateTime.Second;
//			st.wMilliseconds = (short)dateTime.Millisecond;
//			return SetSystemTime(ref st);
//		}
//		public struct MemoryStatus
//		{
//			public uint Length;
//			public uint MemoryLoad;
//			public uint TotalPhysical;
//			public uint AvailablePhysical;
//			public uint TotalPageFile;
//			public uint AvailablePageFile;
//			public uint TotalVirtual;
//			public uint AvailableVirtual;
//		}
//
//		[DllImport("kernel32.dll")] private static extern void GlobalMemoryStatus(out MemoryStatus stat);
//		public static MemoryStatus GetMemoryStatus()
//		{
//			MemoryStatus Result;
//			GlobalMemoryStatus(out Result);
//			return Result;
//		}
//
//		[DllImport("kernel32.dll")] public static extern IntPtr LoadLibrary(string dllToLoad);
//
//
//		[DllImport("kernel32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)] public static extern IntPtr GetProcAddress(IntPtr hModule, string procName);
//
//		[DllImport("kernel32")] public extern static bool FreeLibrary(IntPtr hLibModule);
//
//		[DllImport("kernel32.dll", CharSet = CharSet.Unicode, SetLastError = true)][return: MarshalAs(UnmanagedType.Bool)] public static extern bool SetDllDirectory(string lpPathName);
//
//
//		[DllImport("kernel32.dll", EntryPoint = "CopyMemory", SetLastError = false)] public static extern void CopyMemory(IntPtr dest, IntPtr src, uint count);
//
//		[DllImport("kernel32.dll", EntryPoint = "CopyMemory", SetLastError = false)] public static extern void CopyMemory(void* dest, void* src, uint count);
//
//		[DllImport("kernel32.dll", EntryPoint = "RtlZeroMemory", SetLastError = false)] public static extern void ZeroMemory(IntPtr dest, IntPtr size);
//
//
//		public const uint FILE_MAP_ALL_ACCESS = 0xF001F;
//		public const uint PAGE_READWRITE = 0x04;
//
//		[DllImport("kernel32.dll", SetLastError = true)] public static extern IntPtr CreateFileMapping(IntPtr hFile, IntPtr lpFileMappingAttributes, uint flProtect, uint dwMaximumSizeHigh, uint dwMaximumSizeLow, string lpName);
//
//		[DllImport("kernel32.dll", SetLastError = true)] public static extern IntPtr MapViewOfFile(IntPtr hFileMappingObject, uint dwDesiredAccess, uint dwFileOffsetHigh, uint dwFileOffsetLow, uint dwNumberOfBytesToMap);
//
//		[DllImport("kernel32.dll")][return: MarshalAs(UnmanagedType.Bool)] public static extern bool SetProcessWorkingSetSize(IntPtr process, UIntPtr minimumWorkingSetSize, UIntPtr maximumWorkingSetSize);
//
//		[DllImport("kernel32.dll", SetLastError = true)][return: MarshalAs(UnmanagedType.Bool)] public static extern bool AllocConsole();
//
//		[DllImport("kernel32.dll", SetLastError = true, CharSet = CharSet.Auto)][return: MarshalAs(UnmanagedType.Bool)] public static extern bool GetDiskFreeSpaceEx(string lpDirectoryName, out ulong lpFreeBytesAvailable, out ulong lpTotalNumberOfBytes, out ulong lpTotalNumberOfFreeBytes);
//
//		[DllImport("kernel32.dll")] public static extern ErrorModes SetErrorMode(ErrorModes uMode);
//	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [Flags] public enum ErrorModes : uint
public class ErrorModes 
{
	public static final ErrorModes SYSTEM_DEFAULT = new ErrorModes(0x0);
	public static final ErrorModes SEM_FAILCRITICALERRORS = new ErrorModes(0x0001);
	public static final ErrorModes SEM_NOALIGNMENTFAULTEXCEPT = new ErrorModes(0x0004);
	public static final ErrorModes SEM_NOGPFAULTERRORBOX = new ErrorModes(0x0002);
	public static final ErrorModes SEM_NOOPENFILEERRORBOX = new ErrorModes(0x8000);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ErrorModes> mappings;
	private static java.util.HashMap<Integer, ErrorModes> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ErrorModes.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ErrorModes>();
				}
			}
		}
		return mappings;
	}

	private ErrorModes(int value)
	{
		intValue = value;
		synchronized (ErrorModes.class)
		{
			getMappings().put(value, this);
		}
	}

	public int getValue()
	{
		return intValue;
	}

	public static ErrorModes forValue(int value)
	{
		synchronized (ErrorModes.class)
		{
			ErrorModes enumObj = getMappings().get(value);
			if (enumObj == null)
			{
				return new ErrorModes(value);
			}
			else
			{
				return enumObj;
			}
		}
	}
}