package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

public final class Bootstrapper
{
	private static SafeFileHandle hConOut = new SafeFileHandle(IntPtr.Zero, true);

	private static final Lazy<SpecialFolders> _specialFolders = new Lazy<SpecialFolders>(() -> GetSpecialFolders(new File("~/".MapPath())), LazyThreadSafetyMode.ExecutionAndPublication);

	public static SpecialFolders getspecialFolders()
	{
		return _specialFolders.Value;
	}

	private static native IntPtr GetStdHandle(int nStdHandle);
	static
	{
		System.loadLibrary("kernel32.dll");
	}

	private static native boolean SetStdHandle(int nStdHandle, IntPtr hHandle);

	private static native boolean AllocConsole();

	//[StructLayout(LayoutKind.Sequential)]
	//private class SECURITY_ATTRIBUTES {
	//	public int nLength;
	//	public unsafe byte* pSecurityDescriptor = null;
	//	public int bInheritHandle;
	//}

	private static native SafeFileHandle CreateFile(String lpFileName, int dwDesiredAccess, FileShare dwShareMode, IntPtr securityAttrs, FileMode dwCreationDisposition, int dwFlagsAndAttributes, IntPtr hTemplateFile);

	public static boolean CreateConsole()
	{
		if (AllocConsole())
		{
			//vs debugger may redirect console out into debug out, so we need to redirect it back
			if (!hConOut.IsInvalid)
			{
				hConOut.Dispose();
			}
			hConOut = CreateFile("CONOUT$", 0x40000000, FileShare.Write, IntPtr.Zero, FileMode.Open, 0, IntPtr.Zero);
			if (!hConOut.IsInvalid)
			{
				SetStdHandle(-11, hConOut.DangerousGetHandle());
			}
			OutputStreamWriter cw = new OutputStreamWriter(Console.OpenStandardOutput());
			cw.AutoFlush = true;
			Console.SetOut(cw);
			return true;
		}
		return false;
	}

	/** 
		 create console if there are any console trace listener
	*/
	public static void CreateConsoleForTracing()
	{
		if (Trace.Listeners != null && Trace.Listeners.<ConsoleTraceListener>OfType().Any())
		{
			boolean created = CreateConsole();
			for (ConsoleTraceListener cl : Trace.Listeners.<ConsoleTraceListener>OfType())
			{
				cl.Writer = Console.Out;
			}
			if (created)
			{
				Debug.WriteLine("console has been created...");
			}
		}
	}

	public static java.lang.Iterable<File> GetFiles(java.lang.Iterable<SpecialFolderDescription> folders, String pattern)
	{
		for (SpecialFolderDescription sfd : folders)
		{
			for (File fi : sfd.directory.GetFiles(pattern))
			{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
				yield return fi;
			}
		}
	}

	public static SpecialFolders GetSpecialFolders(File di)
	{
		SpecialFolders sf = new SpecialFolders();
		for (SpecialFolderDescription sfd : EnumSpecialFolders(di))
		{
			String sfType = sfd.info.type;
			sfType = sfType != null ? sfType.toLowerCase() : null;
			switch (sfType)
			{
				case "dlls":
					sf.dlls.add(sfd);
					break;
				case "locales":
					sf.locales.add(sfd);
					break;
				case "plugins":
					sf.plugins.add(sfd);
					break;
				default:
					sf.others.add(sfd);
					break;
			}
		}
		return sf;
	}

	public static java.lang.Iterable<SpecialFolderDescription> EnumSpecialFolders(File di)
	{
		File fi = di.GetFiles(BootstrapperDirInfo.fileName).FirstOrDefault();
		if (fi != null)
		{
			SpecialFolderDescription sfd = null;
			try
			{
				try (FileStream fs = fi.OpenRead())
				{
					try (XmlTextReader xr = new XmlTextReader(fs))
					{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
						var dirInfo = xr.<BootstrapperDirInfo>Deserialize();
						sfd = new SpecialFolderDescription();
						sfd.info = dirInfo;
						sfd.directory = di;
					}
				}
			}
			catch (RuntimeException err)
			{
				Debug.WriteLine(String.format("failed to deserialize dir.info file with error:%1$s", err.getMessage()));
				Debugger.Break();
			}
			if (sfd != null)
			{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
				yield return sfd;
			}
		}
		for (File sdi : di.GetDirectories())
		{
			for (SpecialFolderDescription sfd : EnumSpecialFolders(sdi))
			{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
				yield return sfd;
			}
		}
	}

	/** 
		 recursively looks for all folders containing '-dir.info' file, which describe kind of special folder.
		 "dlls" folders - added to dll search path.
	 
	 @param di directory where scan will be done
	*/
	public static void ScanSpecialFolders(File di)
	{
		File fi = di.GetFiles(BootstrapperDirInfo.fileName).FirstOrDefault();
		if (fi != null)
		{
			try
			{
				try (FileStream fs = fi.OpenRead())
				{
					try (XmlTextReader xr = new XmlTextReader(fs))
					{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
						var dirInfo = xr.<BootstrapperDirInfo>Deserialize();
						if (dirInfo.type.equals("dlls"))
						{
							if (!Kernel32.SetDllDirectory(di.getPath()))
							{
								Debug.WriteLine(String.format("failed to set dll search path '%1$s'", di.getPath()));
							}
						}
					}
				}
			}
			catch (RuntimeException err)
			{
				Debug.WriteLine(String.format("failed to deserialize dir.info file with error:%1$s", err.getMessage()));
				Debugger.Break();
			}
		}
		for (File sdi : di.GetDirectories())
		{
			ScanSpecialFolders(sdi);
		}
	}

	/** 
		 recursively looks for all folders containing '-dir.info' file, which describe kind of special folder.
		 "dlls" folders - added to dll search path.
	 
	 @param path directory where scan will be done
	*/
	public static void ScanSpecialFolders(String path)
	{
		File di = new File(path);
		if (di.exists())
		{
			ScanSpecialFolders(new File(path));
		}
	}

	/** 
		 recursively looks for all folders containing '-dir.info' file, which describe kind of special folder.
		 "dlls" folders - added to dll search path.
		 the scan performed from application root folder.
	*/
	public static void ScanSpecialFolders()
	{
		ScanSpecialFolders(System.TStringHelper.MapPath("~/"));
	}

	public static class SpecialFolderDescription
	{
		public File directory;
		public BootstrapperDirInfo info;
	}

	public static class SpecialFolders
	{
		public ArrayList<SpecialFolderDescription> dlls = new ArrayList<SpecialFolderDescription>();
		public ArrayList<SpecialFolderDescription> locales = new ArrayList<SpecialFolderDescription>();
		public ArrayList<SpecialFolderDescription> others = new ArrayList<SpecialFolderDescription>();
		public ArrayList<SpecialFolderDescription> plugins = new ArrayList<SpecialFolderDescription>();
	}
}