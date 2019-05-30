package Rasad.Core.KeyboardTools;

import Rasad.Core.*;
import java.io.*;

public class KeyboardListener implements Closeable
{
	private static IntPtr hookId = IntPtr.Zero;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.NoInlining)] private IntPtr HookCallback(int nCode, IntPtr wParam, IntPtr lParam)
	private IntPtr HookCallback(int nCode, IntPtr wParam, IntPtr lParam)
	{
		try
		{
			return HookCallbackInner(nCode, wParam, lParam);
		}
		catch (java.lang.Exception e)
		{
			System.out.println("There was some error somewhere...");
		}
		return InterceptKeys.CallNextHookEx(hookId, nCode, wParam, lParam);
	}

	private IntPtr HookCallbackInner(int nCode, IntPtr wParam, IntPtr lParam)
	{
		if (nCode >= 0)
		{
			if (IntPtr.OpEquality(wParam, (IntPtr)InterceptKeys.WM_KEYDOWN))
			{
				int vkCode = Marshal.ReadInt32(lParam);

				if (KeyDown != null)
				{
					for (RawKeyEventHandler listener : KeyDown.listeners())
					{
						listener.invoke(this, new RawKeyEventArgs(vkCode, false));
					}
				}
			}
			else if (IntPtr.OpEquality(wParam, (IntPtr)InterceptKeys.WM_KEYUP))
			{
				int vkCode = Marshal.ReadInt32(lParam);

				if (KeyUp != null)
				{
					for (RawKeyEventHandler listener : KeyUp.listeners())
					{
						listener.invoke(this, new RawKeyEventArgs(vkCode, false));
					}
				}
			}
		}
		return InterceptKeys.CallNextHookEx(hookId, nCode, wParam, lParam);
	}

	public tangible.Event<RawKeyEventHandler> KeyDown = new tangible.Event<RawKeyEventHandler>();
	public tangible.Event<RawKeyEventHandler> KeyUp = new tangible.Event<RawKeyEventHandler>();

	private InterceptKeys.LowLevelKeyboardProc hookCallbackVar = null;
	public KeyboardListener()
	{
		hookCallbackVar = (InterceptKeys.LowLevelKeyboardProc)HookCallback;
		hookId = InterceptKeys.SetHook(hookCallbackVar);
	}

	protected void finalize() throws Throwable
	{
		Dispose();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IDisposable Members

	public final void close() throws IOException
	{
		InterceptKeys.UnhookWindowsHookEx(hookId);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}