package Rasad.Core.Api;

import Rasad.Core.*;

public final class Shell32
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("shell32.dll", SetLastError = true)] public static extern IntPtr CommandLineToArgvW([MarshalAs(UnmanagedType.LPWStr)] string lpCmdLine, out int pNumArgs);
	public static native IntPtr CommandLineToArgvW(String lpCmdLine, tangible.OutObject<Integer> pNumArgs);
	static
	{
		System.loadLibrary("shell32.dll");
	}
}