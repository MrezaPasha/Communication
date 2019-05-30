package Rasad.Core.KeyboardTools;

import Rasad.Core.*;
import java.io.*;

public final class InterceptKeys
{
	@FunctionalInterface
	public interface LowLevelKeyboardProc
	{
		IntPtr invoke(int nCode, IntPtr wParam, IntPtr lParam);
	}

	public static int WH_KEYBOARD_LL = 13;
	public static int WM_KEYDOWN = 0x0100;
	public static int WM_KEYUP = 0x0101;

	public static IntPtr SetHook(LowLevelKeyboardProc proc)
	{
		try (Process curProcess = Process.GetCurrentProcess())
		{
		try (ProcessModule curModule = curProcess.MainModule)
		{
			return SetWindowsHookEx(WH_KEYBOARD_LL, proc, GetModuleHandle(curModule.ModuleName), 0);
		}
		}
	}

	public static native IntPtr SetWindowsHookEx(int idHook, LowLevelKeyboardProc lpfn, IntPtr hMod, int dwThreadId);
	static
	{
		System.loadLibrary("user32.dll");
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)][return: MarshalAs(UnmanagedType.Bool)] public static extern bool UnhookWindowsHookEx(IntPtr hhk);
	public static native boolean UnhookWindowsHookEx(IntPtr hhk);

	public static native IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

	public static native IntPtr GetModuleHandle(String lpModuleName);
	static
	{
		System.loadLibrary("kernel32.dll");
	}
}