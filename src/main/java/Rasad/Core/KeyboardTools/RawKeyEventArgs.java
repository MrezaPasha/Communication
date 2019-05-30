package Rasad.Core.KeyboardTools;

import Rasad.Core.*;
import java.io.*;

public class RawKeyEventArgs extends tangible.EventArgs
{
	public int VKCode;
	public Key Key;
	public boolean IsSysKey;

	public RawKeyEventArgs(int VKCode, boolean isSysKey)
	{
		this.VKCode = VKCode;
		this.IsSysKey = isSysKey;
		this.Key = System.Windows.Input.KeyInterop.KeyFromVirtualKey(VKCode);
	}
}